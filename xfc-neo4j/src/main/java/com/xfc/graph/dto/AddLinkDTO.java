package com.xfc.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-08-18 11:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddLinkDTO {
    @NotBlank(message = "标签名称不可为空")
    private String domain;
    @NotBlank(message = "请指定目标节点")
    private String target;
    @NotBlank(message = "请指定源节点")
    private String source;
    @NotBlank(message = "关系名称不可为空")
    private String type;
}
