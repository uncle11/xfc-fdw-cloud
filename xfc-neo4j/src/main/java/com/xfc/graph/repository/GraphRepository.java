package com.xfc.graph.repository;

import com.baomidou.mybatisplus.extension.api.R;
import com.xfc.graph.dto.GraphDTO;
import com.xfc.graph.dto.GraphLinkDTO;
import com.xfc.graph.dto.GraphNodeDTO;
import com.xfc.graph.entity.GraphNode;
import com.xfc.graph.entity.PersonNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.HashMap;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-08-18 10:43
 */
public interface GraphRepository extends Neo4jRepository<GraphNode,Long> {

}
