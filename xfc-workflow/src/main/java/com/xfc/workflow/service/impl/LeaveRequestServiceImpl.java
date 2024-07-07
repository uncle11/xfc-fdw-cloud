package com.xfc.workflow.service.impl;

import com.xfc.common.utils.BeanCopyUtils;
import com.xfc.workflow.dto.LeaveRequestDTO;
import com.xfc.workflow.entities.ApprovalDetail;
import com.xfc.workflow.entities.BusinessApprovalWorkflowDetail;
import com.xfc.workflow.entities.LeaveRequest;
import com.xfc.workflow.mapper.LeaveRequestMapper;
import com.xfc.workflow.service.IApprovalDetailService;
import com.xfc.workflow.service.IBusinessApprovalWorkflowDetailService;
import com.xfc.workflow.service.ILeaveRequestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xfc
 * @since 2024-06-30
 */
@Service
public class LeaveRequestServiceImpl extends ServiceImpl<LeaveRequestMapper, LeaveRequest> implements ILeaveRequestService {
    @Autowired
    IBusinessApprovalWorkflowDetailService workflowDetaiSlService;
    @Autowired
    IApprovalDetailService approvalDetailService;

    @Override
    public Boolean addRequest(LeaveRequestDTO leaveRequestDTO) {
        LeaveRequest leaveRequest= BeanCopyUtils.copyBean(leaveRequestDTO,LeaveRequest.class);
        // 1. 插入数据到 request 表
        baseMapper.insert(leaveRequest);
        // 2. 根据 workflow_id 查询业务流程的节点信息，找到 serial_number 为 1 的节点,即流程开始时的第一个节点
        BusinessApprovalWorkflowDetail firstNode = workflowDetaiSlService.findFirstNodeByWorkflowId(leaveRequest.getWorkflowId());
        //获取下一级节点 填充下级节点审批人
        BusinessApprovalWorkflowDetail nextNode=workflowDetaiSlService.getNextNodeByPreNode(firstNode);

        if (firstNode != null) {
            // 创建一个 approval_detail 记录示例，需要根据具体情况设置字段值
            ApprovalDetail approvalDetail = new ApprovalDetail();
            approvalDetail.setRequestId(leaveRequest.getId()); // 假设设置关联的 request_id
            approvalDetail.setApproverUsername(firstNode.getNodeUsername()); // 设置首次节点的审批人用户名
            approvalDetail.setApprovalTime(new Date());
            approvalDetail.setNextApproverUsername(nextNode.getNodeUsername());//设置下游节点的审批人用户名
            approvalDetail.setStatus("1"); // 设置初始状态为待审批
            approvalDetail.setWorkflowId(leaveRequest.getWorkflowId());
            approvalDetail.setNodeName(firstNode.getNodeName());
            approvalDetail.setNextNodeName(nextNode.getNodeName());

            // 插入数据到 approval_detail 表
            approvalDetailService.save(approvalDetail);
        } else {
            // 如果未找到对应的节点，根据实际需求进行错误处理或日志记录
            throw new RuntimeException("Unable to find the first node for workflow id: " + leaveRequest.getWorkflowId());
        }
        return true;
    }
}
