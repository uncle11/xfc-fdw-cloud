package com.xfc.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-06-16 19:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {
    private String workflowId;
    private String purpose;
    private String applicantName;
    private String applicantPhone;
    private String applicatUsername;
    private String applicatUnit;
    private String resourceId;

}
