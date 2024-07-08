package com.xfc.dict.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: administered
 * @date: 2022/10/13 17:52
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryTypeCatalogVO implements Serializable {

    @ApiModelProperty("目录ID")
    private String id;

    private String parentId;

    @ApiModelProperty("完整库目录名称")
    private String name;

    @ApiModelProperty("子集目录")
    private List<DictionaryTypeCatalogVO> children;

    public DictionaryTypeCatalogVO(String id, String parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }
}
