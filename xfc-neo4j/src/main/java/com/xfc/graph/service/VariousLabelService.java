package com.xfc.graph.service;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @description: 给特定节点添加动态标签
 * @author: chenss
 * @date 2024-01-04 9:35
 */
@Service
public class VariousLabelService {

    private final Driver driver;

    public VariousLabelService(Driver driver) {
        this.driver = driver;
    }

    public void addDynamicLabel(String uuid, String label) {
        try (Session session = driver.session()) {
            String cypherQuery = "MATCH (node:graph) WHERE ID(n) = $uuid SET node:" + label;
            session.run(cypherQuery, Values.parameters("uuid", uuid));
        }
    }
}
