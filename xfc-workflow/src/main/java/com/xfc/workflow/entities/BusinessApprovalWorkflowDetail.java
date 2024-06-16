package com.xfc.workflow.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 业务审批工作流_详情
 * </p>
 *
 * @author xfc
 * @since 2024-06-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="BusinessApprovalWorkflowDetail对象", description="业务审批工作流_详情")
public class BusinessApprovalWorkflowDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增ID;自增ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "业务审批工作流id")
    private String workflowId;

    @ApiModelProperty(value = "流程序号;流程序号")
    private Integer serialNumber;

    @ApiModelProperty(value = "处理节点名称;处理节点名称")
    private String nodeName;

    @ApiModelProperty(value = "处理任务节点的username")
    private String nodeUsername;

    @ApiModelProperty(value = "是否最后一道审批;是否最后一道审批，1表示是，0表示不是")
    private String isFinal;


}
