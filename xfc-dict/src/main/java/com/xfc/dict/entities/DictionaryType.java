package com.xfc.dict.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 字典类型表
 * </p>
 *
 * @author xfc
 * @since 2024-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DictionaryType对象", description="字典类型表")
public class DictionaryType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "字典类型名称")
    private String typeName;

    @ApiModelProperty(value = "字典类型描述")
    private String typeDescription;

    @ApiModelProperty(value = "父节点id")
    private String parentId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    @ApiModelProperty(value = "字典类型标签")
    private String typeLabel;

    @ApiModelProperty(value = "创建人名字")
    private String creatorId;

    private Boolean isVisible;

    private Boolean isDeleted;

    private String updaterId;

    private String updaterName;

    private String creatorName;


}
