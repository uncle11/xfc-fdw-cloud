package com.xfc.workflow.service.impl;

import com.xfc.common.utils.BeanCopyUtils;
import com.xfc.workflow.dto.RequestDTO;
import com.xfc.workflow.entities.ApprovalDetail;
import com.xfc.workflow.entities.BusinessApprovalWorkflowDetail;
import com.xfc.workflow.entities.Request;
import com.xfc.workflow.mapper.ApprovalDetailMapper;
import com.xfc.workflow.mapper.ApprovalHistoryMapper;
import com.xfc.workflow.mapper.BusinessApprovalWorkflowDetailMapper;
import com.xfc.workflow.mapper.RequestMapper;
import com.xfc.workflow.service.IApprovalDetailService;
import com.xfc.workflow.service.IBusinessApprovalWorkflowDetailService;
import com.xfc.workflow.service.IBusinessApprovalWorkflowService;
import com.xfc.workflow.service.IRequestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 请求表 服务实现类
 * </p>
 *
 * @author xfc
 * @since 2024-06-16
 */
@Service
public class RequestServiceImpl extends ServiceImpl<RequestMapper, Request> implements IRequestService {
    @Autowired
    IBusinessApprovalWorkflowDetailService workflowDetaiSlService;
    @Autowired
    IApprovalDetailService approvalDetailService;
    @Override
    public Boolean addRequest(RequestDTO requestDTO) {
        Request request= BeanCopyUtils.copyBean(requestDTO,Request.class);
        request.setStatus("1");//设置整个流程状态为正在审核
        // 1. 插入数据到 request 表
        baseMapper.insert(request);


        // 2. 根据 workflow_id 查询业务流程的节点信息，找到 serial_number 为 1 的节点,即流程开始时的第一个节点
        BusinessApprovalWorkflowDetail firstNode = workflowDetailService.findFirstNodeByWorkflowId(request.getWorkflowId());
        //获取下一级节点 填充下级节点审批人
        BusinessApprovalWorkflowDetail nextNode=workflowDetailService.getNextNodeByPreNode(firstNode);

        if (firstNode != null) {
            // 创建一个 approval_detail 记录示例，需要根据具体情况设置字段值
            ApprovalDetail approvalDetail = new ApprovalDetail();
            approvalDetail.setRequestId(request.getId()); // 假设设置关联的 request_id
            approvalDetail.setApproverUsername(firstNode.getNodeUsername()); // 设置首次节点的审批人用户名
            approvalDetail.setApprovalTime(new Date());
            approvalDetail.setNextApproverUsername(nextNode.getNodeUsername());//设置下游节点的审批人用户名
            approvalDetail.setStatus("1"); // 设置初始状态为待审批
            approvalDetail.setWorkflowId(request.getWorkflowId());
            approvalDetail.setNodeName(firstNode.getNodeName());
            approvalDetail.setNextNodeName(nextNode.getNodeName());

            // 插入数据到 approval_detail 表
            approvalDetailService.save(approvalDetail);
        } else {
            // 如果未找到对应的节点，根据实际需求进行错误处理或日志记录
            throw new RuntimeException("Unable to find the first node for workflow id: " + request.getWorkflowId());
        }
        return true;
    }
}
