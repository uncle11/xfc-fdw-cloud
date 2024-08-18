package com.xfc.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-08-18 11:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNodeDTO {
    //对应节点标签
    private String domain;

    private String id;
    //对应neo4jd3.js节点属性中的labels
    private String labels;
   //name为必要key
    private HashMap<String,Object> properties;
}
