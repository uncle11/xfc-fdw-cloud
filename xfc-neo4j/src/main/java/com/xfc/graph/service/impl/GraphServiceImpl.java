package com.xfc.graph.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.xfc.graph.dto.*;
import com.xfc.graph.entity.Knows;
import com.xfc.graph.entity.PersonNode;
import com.xfc.graph.repository.GraphRepository;
import com.xfc.graph.repository.KnowsRepository;
import com.xfc.graph.repository.PersonRepository;
import com.xfc.graph.service.GraphService;
import com.xfc.graph.utils.Neo4jUtil;
import com.xfc.graph.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-03-03 14:46
 */
@Service
public class GraphServiceImpl implements GraphService {
    @Autowired
    private GraphRepository graphRepository;
    @Autowired
    private PersonRepository personRepository;

    //    @Autowired
//    private KnowsRepository knowsRepository;
    @Override
    public String saveNAndR() {
        PersonNode person = new PersonNode();
        person.setName("小肥肠1");
        personRepository.save(person);

        PersonNode person1 = new PersonNode();
        person1.setName("豆豆");
        personRepository.save(person1);

        // 创建关系并设置目标节点
        Knows knowsRelationship = new Knows();
        knowsRelationship.setPersonNode(person1); // 设置关系的目标节点

        if (person.getKnows() == null) {
            person.setKnows(new ArrayList<>());
        }
        person.getKnows().add(knowsRelationship);
        personRepository.save(person);
        return "新增成功";
    }

    @Override
    public String saveNode() {
        PersonNode person = new PersonNode();
        person.setName("小肥肠");
        personRepository.save(person);
        return "新增成功";
    }


    @Override
    public GraphDTO createNode(AddNodeDTO addNodeDTO) {
        GraphDTO dataGraphDTO = new GraphDTO();
        String cypherSql = String.format("n.name='%s'", addNodeDTO.getProperties().get("name"));
        String buildNodeql = "";
        buildNodeql = String.format("create (n:`%s`) set %s return n", addNodeDTO.getDomain(), cypherSql);
        List<GraphNodeDTO> nodes = Neo4jUtil.getGraphNode(buildNodeql);
        dataGraphDTO.setNodes(nodes);
        return dataGraphDTO;
    }

    @Override
    public GraphDTO createLink(AddLinkDTO addLinkDTO) {
        String cypherSql = String.format("MATCH (n:`%s`),(m:`%s`) WHERE id(n)=%s AND id(m) = %s "
                + "CREATE (n)-[r:%s]->(m)" + "RETURN n,m,r", addLinkDTO.getDomain(), addLinkDTO.getDomain(), addLinkDTO.getSource(), addLinkDTO.getTarget(), addLinkDTO.getType());
        return   Neo4jUtil.getGraphNodeAndShip(cypherSql);


    }

    @Override
    public GraphDTO getGraph(String domain) {
        GraphDTO graphDTO = new GraphDTO();
        if (!StringUtil.isBlank(domain)) {
            String nodeSql = String.format("MATCH (n:`%s`)  RETURN distinct(n) ", domain);
            List<GraphNodeDTO> graphNode = Neo4jUtil.getGraphNode(nodeSql);
            graphDTO.setNodes(graphNode);
            String relationShipSql = String.format("MATCH (n:`%s`)<-[r]-> (m)  RETURN distinct(r) ", domain);// m是否加领域
            List<GraphLinkDTO> graphRelation = Neo4jUtil.getGraphRelationship(relationShipSql);
            graphDTO.setEdges(graphRelation);

        }

        return graphDTO;
    }
}
