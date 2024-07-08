package com.xfc.dict.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 字典类型DTO
 * @author: chenss
 * @date 2023-05-18 15:31
 */
@Data
public class DicTypeDTO implements Serializable {
    //字典类型标签
    private String typeLabel;
    //字典类型名称
    private String typeName;
    //字典类型描述
    private String typeDescription;
    //父节点id
    private String parentId;
}
