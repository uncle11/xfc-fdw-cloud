package com.xfc.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-06-16 19:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalDTO {
    //申请id
    @NotBlank(message = "id不可为空")
    private String id;
    @NotBlank(message = "审批意见不可为空")
    private String remark;
    //;1.待我审批;2.通过;3.驳回;
    @NotBlank(message = "审批状态不可为空")
    private String status;
    @NotBlank(message = "业务流程id不可为空")
    private String workflowId;
}
