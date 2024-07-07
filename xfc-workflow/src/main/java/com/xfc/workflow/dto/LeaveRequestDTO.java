package com.xfc.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-06-30 16:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestDTO {
    private String workflowId;
    private String purpose;
    private Integer leaveDays;
    private String applicantName;
    private String applicantPhone;
    private String applicatUsername;
}
