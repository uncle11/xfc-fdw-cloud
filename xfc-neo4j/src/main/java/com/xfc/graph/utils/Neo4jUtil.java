package com.xfc.graph.utils;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.xfc.graph.dto.GraphDTO;
import com.xfc.graph.dto.GraphLinkDTO;
import com.xfc.graph.dto.GraphNodeDTO;
import org.neo4j.driver.*;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Path;
import org.neo4j.driver.types.Relationship;
import org.neo4j.driver.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Lazy(false)
public class Neo4jUtil implements AutoCloseable {

    private static Driver neo4jDriver;

    private static final Logger log = LoggerFactory.getLogger(Neo4jUtil.class);

    @Autowired
    @Lazy
    public void setNeo4jDriver(Driver neo4jDriver) {
        Neo4jUtil.neo4jDriver = neo4jDriver;
    }
    /**
     * 测试neo4j连接是否打开
     */
    public static boolean isNeo4jOpen() {
        try (Session session = neo4jDriver.session()) {
            log.debug("连接成功：" + session.isOpen());
            return session.isOpen();
        }
    }

    /**
     * neo4j驱动执行cypher
     *
     * @param cypherSql cypherSql
     */

    public static void runCypherSql(String cypherSql) {
        try (Session session = neo4jDriver.session()) {
            log.debug(cypherSql);
            session.run(cypherSql);
        }
    }

    public <T> List<T> readCyphers(String cypherSql, Function<Record, T> mapper) {
        try (Session session = neo4jDriver.session()) {
            log.debug(cypherSql);
            Result result = session.run(cypherSql);
            return result.list(mapper);
        }
    }

    /**
     * 返回节点集合，此方法不保留关系
     *
     * @param cypherSql cypherSql
     */

    public static List<GraphNodeDTO> getGraphNode(String cypherSql) {
        log.debug("Executing Cypher query: {}", cypherSql);

        try (Session session = neo4jDriver.session(SessionConfig.builder().withDefaultAccessMode(AccessMode.WRITE).build())) {
            return session.writeTransaction(tx -> {
                Result result = tx.run(cypherSql);
                return result.list().stream()
                        .flatMap(record -> record.fields().stream())
                        .filter(pair -> pair.value().type().name().equals("NODE"))
                        .map(pair -> {
                            Node node = pair.value().asNode();
                            // label为节点对应图片
                            String label = "";
                            HashMap<String, Object> properties = new HashMap<>(node.asMap());
                            return new GraphNodeDTO(String.valueOf(node.id()), label, properties);
                        })
                        .collect(Collectors.toList());
            });
        } catch (Exception e) {
            log.error("Error executing Cypher query: {}", cypherSql, e);
            return new ArrayList<>();
        }
    }


    /**
     * 获取数据库索引
     * @return
     */
    public static List<HashMap<String, Object>> getGraphIndex() {
        String cypherSql = "CALL db.indexes()";
        log.debug("Executing Cypher query: {}", cypherSql);

        try (Session session = neo4jDriver.session()) {
            return session.readTransaction(tx -> {
                Result result = tx.run(cypherSql);
                return result.list().stream()
                        .map(record -> {
                            HashMap<String, Object> indexInfo = new HashMap<>();
                            record.fields().forEach(pair -> {
                                String key = pair.key();
                                Value value = pair.value();
                                if (key.equalsIgnoreCase("labelsOrTypes")) {
                                    String objects = value.asList().stream()
                                            .map(Object::toString)
                                            .collect(Collectors.joining(","));
                                    indexInfo.put(key, objects);
                                } else {
                                    indexInfo.put(key, value.asObject());
                                }
                            });
                            return indexInfo;
                        })
                        .collect(Collectors.toList());
            });
        } catch (Exception e) {
            log.error("Error executing Cypher query for index information", e);
            return new ArrayList<>();
        }
    }

public static List<HashMap<String, Object>> getGraphLabels() {
    String cypherSql = "CALL db.labels()";
    log.debug("Executing Cypher query: {}", cypherSql);

    try (Session session = neo4jDriver.session()) {
        return session.readTransaction(tx -> {
            Result result = tx.run(cypherSql);
            return result.list().stream()
                    .map(record -> {
                        HashMap<String, Object> labelInfo = new HashMap<>();
                        record.fields().forEach(pair -> {
                            String key = pair.key();
                            Value value = pair.value();
                            if (key.equalsIgnoreCase("label")) {
                                String label = value.asString().replace("\"", "");
                                labelInfo.put(key, label);
                            } else {
                                labelInfo.put(key, value.asObject());
                            }
                        });
                        return labelInfo;
                    })
                    .collect(Collectors.toList());
        });
    } catch (Exception e) {
        log.error("Error executing Cypher query for graph labels", e);
        return new ArrayList<>();
    }
}
    /**
     * 删除索引
     * @param label
     */
    public static void deleteIndex(String label) {
        try (Session session = neo4jDriver.session()) {
            String cypherSql=String.format("DROP INDEX ON :`%s`(name)",label);
            session.run(cypherSql);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 创建索引
     * @param label
     * @param prop
     */
    public static void createIndex(String label,String prop) {
        try (Session session = neo4jDriver.session()) {
            String cypherSql=String.format("CREATE INDEX ON :`%s`(%s)",label,prop);
            session.run(cypherSql);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static GraphNodeDTO getSingleGraphNode(String cypherSql) {
        List<GraphNodeDTO> nodes = getGraphNode(cypherSql);
        if(CollectionUtils.isNotEmpty(nodes)){
            return nodes.get(0);
        }
        return null;
    }

    /**
     * 获取一个标准的表格，一般用于语句里使用as
     *
     * @param cypherSql
     * @return
     */
    public static List<HashMap<String, Object>> getGraphTable(String cypherSql) {
        List<HashMap<String, Object>> resultData = new ArrayList<HashMap<String, Object>>();
        try (Session session = neo4jDriver.session()) {
            log.debug(cypherSql);
            Result result = session.run(cypherSql);
            if (result.hasNext()) {
                List<Record> records = result.list();
                for (Record recordItem : records) {
                    List<Pair<String, Value>> f = recordItem.fields();
                    HashMap<String, Object> rss = new HashMap<String, Object>();
                    for (Pair<String, Value> pair : f) {
                        String key = pair.key();
                        Value value = pair.value();
                        rss.put(key, value);
                    }
                    resultData.add(rss);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return resultData;
    }

    /**
     * 返回关系，不保留节点内容
     *
     * @param cypherSql
     * @return
     */
    public static List<GraphLinkDTO> getGraphRelationship(String cypherSql) {
        log.debug("Executing Cypher query: {}", cypherSql);

        try (Session session = neo4jDriver.session(SessionConfig.builder().withDefaultAccessMode(AccessMode.WRITE).build())) {
            return session.writeTransaction(tx -> {
                Result result = tx.run(cypherSql);
                return result.list().stream()
                        .flatMap(record -> record.fields().stream())
                        .filter(pair -> pair.value().type().name().equals("RELATIONSHIP"))
                        .map(pair -> {
                            Relationship relationship = pair.value().asRelationship();
                            String id = String.valueOf(relationship.id());
                            String source = String.valueOf(relationship.startNodeId());
                            String target = String.valueOf(relationship.endNodeId());
                            HashMap<String, Object> properties = new HashMap<>(relationship.asMap());
                            return new GraphLinkDTO(id, relationship.type(), source, target, properties);
                        })
                        .collect(Collectors.toList());
            });
        } catch (Exception e) {
            log.error("Error executing Cypher query: {}", cypherSql, e);
            return new ArrayList<>();
        }
    }


    /**
     * 获取值类型的结果,如count,uuid
     *
     * @return 1 2 3 等数字类型
     */
    public static long getGraphValue(String cypherSql) {
        long val = 0;
        try (Session session = neo4jDriver.session()) {
            log.debug(cypherSql);
            Result cypherResult = session.run(cypherSql);
            if (cypherResult.hasNext()) {
                Record record = cypherResult.next();
                for (Value value : record.values()) {
                    val = value.asLong();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return val;
    }

    /**
     * 返回节点和关系，节点node,关系relationship,路径path,集合list,map
     *
     * @param cypherSql
     * @return
     */

    public static GraphDTO getGraphNodeAndShip(String cypherSql) {
        log.debug("Executing Cypher query: {}", cypherSql);
        GraphDTO graphDTO = new GraphDTO();
        try (Session session = neo4jDriver.session(SessionConfig.builder().withDefaultAccessMode(AccessMode.WRITE).build())) {
            return session.writeTransaction(tx -> {
                Result result = tx.run(cypherSql);
                List<GraphNodeDTO> nodes = new ArrayList<>();
                List<GraphLinkDTO> relationships = new ArrayList<>();
                HashSet<String> uuids = new HashSet<>();

                result.list().stream().flatMap(record -> record.fields().stream()).forEach(pair -> {
                    String type = pair.value().type().name();
                    if ("NODE".equals(type)) {
                        Node node = pair.value().asNode();
                        String uuid = String.valueOf(node.id());
                        if (uuids.add(uuid)) {
                            HashMap<String, Object> properties = new HashMap<>(node.asMap());
                            nodes.add(new GraphNodeDTO(uuid, node.labels().iterator().next(), properties));
                        }
                    } else if ("RELATIONSHIP".equals(type)) {
                        Relationship relationship = pair.value().asRelationship();
                        HashMap<String, Object> properties = new HashMap<>(relationship.asMap());
                        relationships.add(new GraphLinkDTO(String.valueOf(relationship.id()), relationship.type(),
                                String.valueOf(relationship.startNodeId()),
                                String.valueOf(relationship.endNodeId()),
                                properties));
                    }
                });

                graphDTO.setNodes(nodes);
                graphDTO.setEdges(toDistinctList(relationships));
                return graphDTO;
            });
        } catch (Exception e) {
            log.error("Error executing Cypher query: {}", cypherSql, e);
            return new GraphDTO();
        }
    }

    public static List<GraphLinkDTO> toDistinctList(List<GraphLinkDTO> list) {
        Set<String> ids = new HashSet<>();
        list.removeIf(link -> !ids.add(link.getId()));
        return list;
    }



    /**
     * 去掉json键的引号，否则neo4j会报错
     *
     * @param jsonStr
     * @return
     */
    public static String getFilterPropertiesJson(String jsonStr) {
        return jsonStr.replaceAll("\"(\\w+)\"(\\s*:\\s*)", "$1$2"); // 去掉key的引号
    }

    /**
     * 对象转json，key=value,用于 cypher set语句
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String getKeyValCyphersql(T obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> sqlList = new ArrayList<String>();
        // 得到类对象
        Class userCla = obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            Class type = f.getType();

            f.setAccessible(true); // 设置些属性是可以访问的
            Object val = new Object();
            try {
                val = f.get(obj);
                if (val == null) {
                    val = "";
                }
                String sql = "";
                String key = f.getName();
                if (val instanceof String[]) {
                    //如果为true则强转成String数组
                    String[] arr = (String[]) val;
                    String v = "";
                    for (int j = 0; j < arr.length; j++) {
                        arr[j] = "'" + arr[j] + "'";
                    }
                    v = String.join(",", arr);
//                    sql = "n." + key + "=[" + val + "]";
                    sql = "n." + key + "=[" + v + "]";
                } else if (val instanceof List) {
                    //如果为true则强转成String数组
                    List<String> arr = (ArrayList<String>) val;
                    List<String> aa = new ArrayList<String>();
                    String v = "";
                    for (String s : arr) {
                        s = "'" + s + "'";
                        aa.add(s);
                    }
                    v = String.join(",", aa);
                    sql = "n." + key + "=[" + v + "]";
                } else {
                    // 得到此属性的值
                    map.put(key, val);// 设置键值
                    if (type.getName().equals("int")) {
                        sql = "n." + key + "=" + val + "";
                    } else {
                        sql = "n." + key + "='" + val + "'";
                    }
                }

                sqlList.add(sql);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                log.error(e.getMessage());
            }
        }
        return String.join(",", sqlList);
    }

    /**
     * 将haspmap集合反序列化成对象集合
     *
     * @param maps
     * @param type
     * @param <T>
     * @return
     */
    public static <T> List<T> hashMapToObject(List<HashMap<String, Object>> maps, Class<T> type) {
        try {
            List<T> list = new ArrayList<T>();
            for (HashMap<String, Object> r : maps) {
                T t = type.newInstance();
                Iterator iter = r.entrySet().iterator();// 该方法获取列名.获取一系列字段名称.例如name,age...
                while (iter.hasNext()) {
                    Entry entry = (Entry) iter.next();// 把hashmap转成Iterator再迭代到entry
                    String key = entry.getKey().toString(); // 从iterator遍历获取key
                    Object value = entry.getValue(); // 从hashmap遍历获取value
                    if ("serialVersionUID".toLowerCase().equals(key.toLowerCase())) {
                        continue;
                    }
                    Field field = type.getDeclaredField(key);// 获取field对象
                    if (field != null) {
                        //System.out.print(field.getType());
                        field.setAccessible(true);
                        //System.out.print(field.getType().getName());
                        if (field.getType() == int.class || field.getType() == Integer.class) {
                            if (value == null || StringUtil.isBlank(value.toString())) {
                                field.set(t, 0);// 设置值
                            } else {
                                field.set(t, Integer.parseInt(value.toString()));// 设置值
                            }
                        } else if (field.getType() == long.class || field.getType() == Long.class) {
                            if (value == null || StringUtil.isBlank(value.toString())) {
                                field.set(t, 0);// 设置值
                            } else {
                                field.set(t, Long.parseLong(value.toString()));// 设置值
                            }

                        } else if (field.getType() == Double.class) {
                            if (value == null || StringUtil.isBlank(value.toString())) {
                                field.set(t, 0.0);// 设置值
                            } else {
                                field.set(t, Double.parseDouble(value.toString()));// 设置值
                            }

                        } else {
                            if (field.getType().equals(List.class)) {
                                if (value == null || StringUtil.isBlank(value.toString())) {
                                    field.set(t, null);
                                } else {
                                    field.set(t, value);// 设置值
                                }
                            } else {
                                field.set(t, value);// 设置值
                            }
                        }
                    }

                }
                list.add(t);
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将haspmap反序列化成对象
     *
     * @param map
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T hashMapToObjectItem(HashMap<String, Object> map, Class<T> type) {
        try {
            T t = type.newInstance();
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Entry entry = (Entry) iter.next();// 把hashmap转成Iterator再迭代到entry
                String key = entry.getKey().toString(); // 从iterator遍历获取key
                Object value = entry.getValue(); // 从hashmap遍历获取value
                if ("serialVersionUID".toLowerCase().equals(key.toLowerCase())) {
                    continue;
                }
                Field field = type.getDeclaredField(key);// 获取field对象
                if (field != null) {
                    field.setAccessible(true);
                    if (field.getType() == int.class || field.getType() == Integer.class) {
                        if (value == null || StringUtil.isBlank(value.toString())) {
                            field.set(t, 0);// 设置值
                        } else {
                            field.set(t, Integer.parseInt(value.toString()));// 设置值
                        }
                    } else if (field.getType() == long.class || field.getType() == Long.class) {
                        if (value == null || StringUtil.isBlank(value.toString())) {
                            field.set(t, 0);// 设置值
                        } else {
                            field.set(t, Long.parseLong(value.toString()));// 设置值
                        }

                    } else if (field.getType() == Double.class) {
                        if (value == null || StringUtil.isBlank(value.toString())) {
                            field.set(t, 0.0);// 设置值
                        } else {
                            field.set(t, Double.parseDouble(value.toString()));// 设置值
                        }

                    } else {
                        if (field.getType().equals(List.class)) {
                            if (value == null || StringUtil.isBlank(value.toString())) {
                                field.set(t, null);
                            } else {
                                field.set(t, value);// 设置值
                            }
                        } else {
                            field.set(t, value);// 设置值
                        }

                    }
                }

            }

            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回单个节点信息
     */
    public static HashMap<String, Object> getOneNode(String cypherSql) {
        HashMap<String, Object> ret = new HashMap<String, Object>();
        try (Session session = neo4jDriver.session()) {
            log.debug(cypherSql);
            Result result = session.run(cypherSql);
            if (result.hasNext()) {
                Record record = result.list().get(0);
                Pair<String, Value> f = record.fields().get(0);
                String typeName = f.value().type().name();
                if ("NODE".equals(typeName)) {
                    Node noe4jNode = f.value().asNode();
                    String uuid = String.valueOf(noe4jNode.id());
                    Map<String, Object> map = noe4jNode.asMap();
                    for (Entry<String, Object> entry : map.entrySet()) {
                        String key = entry.getKey();
                        ret.put(key, entry.getValue());
                    }
//                    ret.put("uuid", uuid);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ret;
    }

    public static boolean batchRunCypherWithTx(List<String> cyphers) {
        Session session = neo4jDriver.session();
        try (Transaction tx = session.beginTransaction()) {
            for (String cypher : cyphers) {
                tx.run(cypher);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
        return true;
    }


    @Override
    public void close() throws Exception {
        neo4jDriver.close();
    }
}
