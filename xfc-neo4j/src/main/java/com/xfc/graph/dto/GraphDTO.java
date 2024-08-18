package com.xfc.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-08-18 11:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphDTO {
    private List<GraphNodeDTO>nodes;
    private List<GraphLinkDTO>edges;
}
