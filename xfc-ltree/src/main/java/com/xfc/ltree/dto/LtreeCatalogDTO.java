package com.xfc.ltree.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-01-31 12:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LtreeCatalogDTO {

    private String id;

    private String parentId;

    private String name;

    List<LtreeCatalogDTO>children;

}
