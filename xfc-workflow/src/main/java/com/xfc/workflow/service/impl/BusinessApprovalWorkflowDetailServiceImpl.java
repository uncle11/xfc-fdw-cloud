package com.xfc.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xfc.workflow.entities.BusinessApprovalWorkflowDetail;
import com.xfc.workflow.mapper.BusinessApprovalWorkflowDetailMapper;
import com.xfc.workflow.service.IBusinessApprovalWorkflowDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务审批工作流_详情 服务实现类
 * </p>
 *
 * @author xfc
 * @since 2024-06-16
 */
@Service
public class BusinessApprovalWorkflowDetailServiceImpl extends ServiceImpl<BusinessApprovalWorkflowDetailMapper, BusinessApprovalWorkflowDetail> implements IBusinessApprovalWorkflowDetailService {
    @Override
    public BusinessApprovalWorkflowDetail findFirstNodeByWorkflowId(String workflowId) {
        LambdaQueryWrapper<BusinessApprovalWorkflowDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusinessApprovalWorkflowDetail::getWorkflowId, workflowId)
                .orderByAsc(BusinessApprovalWorkflowDetail::getSerialNumber);
        List<BusinessApprovalWorkflowDetail> businessApprovalWorkflowDetails = baseMapper.selectList(queryWrapper);
        return businessApprovalWorkflowDetails.get(0);
    }

    @Override
    public BusinessApprovalWorkflowDetail getNextNodeByPreNode(BusinessApprovalWorkflowDetail firstNode) {
        LambdaQueryWrapper<BusinessApprovalWorkflowDetail> nextNodeQueryWrapper = new LambdaQueryWrapper<>();
        nextNodeQueryWrapper.eq(BusinessApprovalWorkflowDetail::getWorkflowId,firstNode.getWorkflowId())
                .eq(BusinessApprovalWorkflowDetail::getSerialNumber,firstNode.getSerialNumber()+1);
        BusinessApprovalWorkflowDetail nextNode = baseMapper.selectOne(nextNodeQueryWrapper);
        return nextNode;
    }

    @Override
    public BusinessApprovalWorkflowDetail findByWorkflowIdAndNodeName(String workflowId, String nodeName) {
        LambdaQueryWrapper<BusinessApprovalWorkflowDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusinessApprovalWorkflowDetail::getWorkflowId,workflowId)
                .eq(BusinessApprovalWorkflowDetail::getNodeName,nodeName);
        BusinessApprovalWorkflowDetail businessApprovalWorkflowDetail=baseMapper.selectOne(queryWrapper);
        return businessApprovalWorkflowDetail;
    }
}
