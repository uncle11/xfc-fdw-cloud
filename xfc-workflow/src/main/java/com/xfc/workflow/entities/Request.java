package com.xfc.workflow.entities;

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
 * 请求表
 * </p>
 *
 * @author xfc
 * @since 2024-06-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Request对象", description = "请求表")
public class Request extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty(value = "业务类型id")
    private String workflowId;

    @ApiModelProperty(value = "使用目的")
    private String purpose;

    @ApiModelProperty(value = "审批状态;1.正在审核;2.通过;3.驳回")
    private String status;

    @ApiModelProperty(value = "申请人姓名")
    private String applicantName;

    @ApiModelProperty(value = "申请人电话")
    private String applicantPhone;

    @ApiModelProperty(value = "申请用户名")
    private String applicatUsername;

    @ApiModelProperty(value = "申请单位")
    private String applicatUnit;

    @ApiModelProperty(value = "申请资源id")
    private String resourceId;


}
