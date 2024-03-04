package com.xfc.graph.repository;

import com.xfc.graph.entity.Knows;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-03-03 15:10
 */
public interface KnowsRepository extends Neo4jRepository<Knows, Long> {
    // 如果需要，添加自定义查询
}