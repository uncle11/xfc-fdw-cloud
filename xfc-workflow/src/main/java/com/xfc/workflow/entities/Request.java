package com.xfc.workflow.entities;

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
 * 请求表
 * </p>
 *
 * @author xfc
 * @since 2024-06-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Request对象", description="请求表")
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty(value = "业务类型id")
    private String workflowId;

    @ApiModelProperty(value = "申请时间;数据库自动填充")
    private Date createTime;

    @ApiModelProperty(value = "使用目的")
    private String purpose;

    @ApiModelProperty(value = "审批状态;1.正在审核;2.通过;3.驳回")
    private String status;

    private Integer version;

    @ApiModelProperty(value = "申请人姓名")
    private String applicantName;

    @ApiModelProperty(value = "申请人电话")
    private String applicantPhone;

    private Integer isDeleted;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "申请用户名")
    private String applicatUsername;

    @ApiModelProperty(value = "申请单位")
    private String applicatUnit;

    @ApiModelProperty(value = "申请资源id")
    private String resourceId;


}
