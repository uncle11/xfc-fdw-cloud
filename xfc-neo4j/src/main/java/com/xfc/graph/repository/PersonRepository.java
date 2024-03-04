package com.xfc.graph.repository;

import com.xfc.graph.entity.PersonNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-03-03 14:43
 */
@Repository
public interface PersonRepository extends Neo4jRepository<PersonNode,Long> {
}
