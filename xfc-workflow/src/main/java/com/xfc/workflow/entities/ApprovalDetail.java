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
 * 审批细节表
 * </p>
 *
 * @author xfc
 * @since 2024-06-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ApprovalDetail对象", description="审批细节表")
public class ApprovalDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "request表id")
    private String requestId;

    @ApiModelProperty(value = "审批人username")
    private String approverUsername;

    @ApiModelProperty(value = "审批时间")
    private Date approvalTime;

    @ApiModelProperty(value = "下一个审批人的username")
    private String nextApproverUsername;

    @ApiModelProperty(value = "审批状态;1.待我审批;2.通过;3.驳回; ")
    private String status;

    @ApiModelProperty(value = "审批意见")
    private String remark;

    @ApiModelProperty(value = "业务流程id")
    private String workflowId;



}
