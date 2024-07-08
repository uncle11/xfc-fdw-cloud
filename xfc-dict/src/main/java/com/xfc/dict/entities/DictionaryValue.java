package com.xfc.dict.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.xfc.common.entities.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 字典值表
 * </p>
 *
 * @author xfc
 * @since 2024-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DictionaryValue对象", description="字典值表")
public class DictionaryValue extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "字典值名称")
    private String valueName;

    @ApiModelProperty(value = "字典类型id")
    private String typeId;


    @ApiModelProperty(value = "字典值标签")
    private String valueLabel;

    @ApiModelProperty(value = "字典值排序")
    private Integer valueSort;




}
