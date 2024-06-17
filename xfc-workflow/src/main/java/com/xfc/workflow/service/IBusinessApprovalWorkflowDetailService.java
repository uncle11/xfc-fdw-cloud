package com.xfc.workflow.service;

import com.xfc.workflow.entities.BusinessApprovalWorkflowDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 业务审批工作流_详情 服务类
 * </p>
 *
 * @author xfc
 * @since 2024-06-16
 */
public interface IBusinessApprovalWorkflowDetailService extends IService<BusinessApprovalWorkflowDetail> {

    BusinessApprovalWorkflowDetail findFirstNodeByWorkflowId(String workflowId);

    BusinessApprovalWorkflowDetail getNextNodeByPreNode(BusinessApprovalWorkflowDetail firstNode);

    BusinessApprovalWorkflowDetail findByWorkflowIdAndNodeName(String workflowId, String nodeName);
}
