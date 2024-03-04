package com.xfc.graph.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;


/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-03-03 15:04
 */
@RelationshipProperties
@Data
public class Knows {
    @RelationshipId
    private Long id;

    @TargetNode
    private PersonNode personNode;

}
