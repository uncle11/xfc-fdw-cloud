package com.xfc.graph.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-08-18 10:44
 */
@RelationshipProperties
@Data
public class GraphRelationShip {
    @RelationshipId
    private Long id;

    @TargetNode
    private GraphNode graphNode;
}
