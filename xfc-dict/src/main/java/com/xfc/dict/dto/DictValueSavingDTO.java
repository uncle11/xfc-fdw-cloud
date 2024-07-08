package com.xfc.dict.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: 李帅兵
 * @date: 2023/5/19 10:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictValueSavingDTO {
    private String id;

    @ApiModelProperty(value = "字典类型ID", required = true)
    @NotBlank(message = "字典类型ID不可为空")
    private String typeId;

    @ApiModelProperty(value = "字典值", required = true)
    @NotBlank(message = "字典值不可为空")
    private String valueName;

    @ApiModelProperty(value = "字典标签", required = true)
    @NotBlank(message = "字典标签不可为空")
    private String valueLabel;

    @ApiModelProperty(value = "字典值顺序", required = true)
    @NotNull(message = "字典值顺序不可为空")
    private int valueSort;
}
