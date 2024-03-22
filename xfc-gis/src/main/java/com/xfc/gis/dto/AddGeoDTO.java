package com.xfc.gis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-03-22 10:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddGeoDTO {

    @NotBlank(message = "shp路径不能为空")
    private String shpPath;
}
