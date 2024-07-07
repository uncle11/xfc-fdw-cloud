package com.xfc.workflow.service.strategy;


import com.xfc.common.entities.MessageConfirmation;
import com.xfc.common.utils.JsonUtil;
import com.xfc.workflow.dto.ApprovalDTO;
import com.xfc.workflow.entities.*;
import com.xfc.workflow.mapper.ApprovalDetailMapper;
import com.xfc.workflow.mapper.ApprovalHistoryMapper;
import com.xfc.workflow.mapper.RequestMapper;
import com.xfc.workflow.service.IBusinessApprovalWorkflowDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-06-18 13:26
 */
@Service
@Slf4j
public class ApprovalDataRequestService {
    @Autowired
    IBusinessApprovalWorkflowDetailService businessApprovalWorkflowDetailService;
    @Autowired
    RequestMapper requestMapper;
    @Autowired
    ApprovalHistoryMapper approvalHistoryMapper;
    @Autowired
    ApprovalDetailMapper approvalDetailMapper;

    @Transactional
    public Boolean approvalApplication(ApprovalDTO approvalDTO) {
        // 这里我写死了，实际获取应该走权限框架获取当前在线用户 username
        String username = "xfc";
//        审批人姓名，从用户表中获取
        String name="小肥肠";
        //查询出当前任务节点
        ApprovalDetail approvalDetail = approvalDetailMapper.selectById(approvalDTO.getId());
        //获取当前审批的申请信息
        Request request = requestMapper.selectById(approvalDetail.getRequestId());
        if(request==null){
            throw new RuntimeException("申请id有误");
        }

        // 审批通过
        if (approvalDTO.getStatus().equals("2")) {
            // 根据 workflow_id 和 node_name 联查 business_approval_workflow_detail 表，获取当前流程是否为最后节点即 is_final=1
            BusinessApprovalWorkflowDetail currentWorkflowDetail = businessApprovalWorkflowDetailService.findByWorkflowIdAndNodeName(approvalDTO.getWorkflowId(), approvalDetail.getNodeName());
            if (currentWorkflowDetail != null && currentWorkflowDetail.getIsFinal().equals("1")) {
                // 如果是最后节点，则删除该条数据，填充 approval_history 表，根据 request 表修改 request 数据的 status 为 2
                approvalDetailMapper.deleteById(approvalDetail.getId()); // 删除当前审批记录
                // 更新 request 表中的状态为 2（通过）
                request.setStatus("2");
                requestMapper.updateById(request);

            } else {
                // 如果不是最后节点，则更新 business_approval_workflow_detail 为下一个节点审批信息
                BusinessApprovalWorkflowDetail nextNode = businessApprovalWorkflowDetailService.getNextNodeByPreNode(currentWorkflowDetail);
//                获取下一级节点的更下一级
                BusinessApprovalWorkflowDetail nextNextNode= businessApprovalWorkflowDetailService.getNextNodeByPreNode(nextNode);
                // 更新当前 approval_detail 表中的审批人和下一个审批人信息
                approvalDetail.setApproverUsername(nextNode.getNodeUsername());
                approvalDetail.setNodeName(nextNextNode.getNodeName());
                approvalDetail.setNextApproverUsername(nextNextNode!=null?nextNextNode.getNodeUsername():"");
                approvalDetail.setNextNodeName(nextNextNode!=null?nextNextNode.getNodeName():"");
                approvalDetail.setApprovalTime(new Date());
                approvalDetail.setStatus("1"); // 设置为待审批状态
                approvalDetailMapper.updateById(approvalDetail);

            }
            // 填充 approval_history 表
            ApprovalHistory approvalHistory = new ApprovalHistory();
            approvalHistory.setRequestId(request.getId());
            approvalHistory.setApproverName(name); // 设置审批人姓名，或者从用户表中获取
            approvalHistory.setApprovalTime(new Date());
            approvalHistory.setStatus("2"); // 通过
            approvalHistory.setRemark(approvalDTO.getRemark());
            approvalHistory.setWorkflowId(approvalDTO.getWorkflowId());
            approvalHistory.setApplicantPhone(request.getApplicantPhone());
            approvalHistory.setPurpose(request.getPurpose());
            approvalHistory.setApplicantName(request.getApplicantName());
            approvalHistory.setApproverUsername(username); // 设置审批人用户名，或者从用户表中获取
            approvalHistoryMapper.insert(approvalHistory); // 插入审批历史记录
        } else if (approvalDTO.getStatus().equals("3")) {
            // 审批驳回
            approvalDetailMapper.deleteById(approvalDetail.getId()); // 删除当前审批记录

            // 填充 approval_history 表
            ApprovalHistory approvalHistory = new ApprovalHistory();
            approvalHistory.setRequestId(request.getId());
            approvalHistory.setApproverName(name); // 设置审批人姓名，或者从用户表中获取
            approvalHistory.setApprovalTime(new Date());
            approvalHistory.setStatus("3"); // 驳回
            approvalHistory.setRemark(approvalDTO.getRemark());
            approvalHistory.setWorkflowId(approvalDTO.getWorkflowId());
            approvalHistory.setApplicantPhone(request.getApplicantPhone());
            approvalHistory.setPurpose(request.getPurpose());
            approvalHistory.setApplicantName(request.getApplicantName());
            approvalHistory.setApproverUsername(username); // 设置审批人用户名，或者从用户表中获取
            approvalHistoryMapper.insert(approvalHistory); // 插入审批历史记录

            // 更新 request 表中的状态为 3（驳回）
            request.setStatus("3");
            requestMapper.updateById(request);

        }
        return true; // 或者根据实际需求返回其他业务逻辑
    }


}