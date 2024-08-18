package com.xfc.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-08-18 11:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphLinkDTO {
    private String id;
    private String type;
    private String source;
    private String target;
    private HashMap<String,Object> properties;
}
