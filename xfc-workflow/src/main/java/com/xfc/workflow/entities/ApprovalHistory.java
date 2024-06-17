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
 * 
 * </p>
 *
 * @author xfc
 * @since 2024-06-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ApprovalHistory对象", description="")
public class ApprovalHistory extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "申请id")
    private String requestId;

    @ApiModelProperty(value = "审批人姓名")
    private String approverName;

    @ApiModelProperty(value = "审批时间")
    private Date approvalTime;

    @ApiModelProperty(value = "审批状态1.待我审批;2.通过;3.驳回")
    private String status;

    @ApiModelProperty(value = "审批意见")
    private String remark;

    @ApiModelProperty(value = "业务流程id")
    private String workflowId;


    @ApiModelProperty(value = "申请人电话")
    private String applicantPhone;

    @ApiModelProperty(value = "申请理由")
    private String purpose;

    @ApiModelProperty(value = "申请人姓名")
    private String applicantName;

    @ApiModelProperty(value = "审批人username")
    private String approverUsername;



}
