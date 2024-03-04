package com.xfc.graph.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-03-03 14:38
 */
@Data
@Node("Person")
public class PersonNode {
    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;

    @Relationship(type = "KNOWS", direction = Relationship.Direction.OUTGOING)
    private List<Knows> knows;
}

