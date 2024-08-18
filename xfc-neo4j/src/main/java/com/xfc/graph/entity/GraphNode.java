package com.xfc.graph.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

import java.util.HashMap;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-08-18 10:44
 */
@Data
@Node("graph")
public class GraphNode {
    @Id
    @GeneratedValue
    private Long id;

    private String labels;

    private HashMap<String,Object>properties;
}
