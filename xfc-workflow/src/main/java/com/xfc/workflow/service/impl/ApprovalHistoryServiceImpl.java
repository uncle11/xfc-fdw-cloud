package com.xfc.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xfc.workflow.entities.ApprovalDetail;
import com.xfc.workflow.entities.ApprovalHistory;
import com.xfc.workflow.mapper.ApprovalHistoryMapper;
import com.xfc.workflow.service.IApprovalHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xfc
 * @since 2024-06-16
 */
@Service
public class ApprovalHistoryServiceImpl extends ServiceImpl<ApprovalHistoryMapper, ApprovalHistory> implements IApprovalHistoryService {
    @Override
    public List<ApprovalHistory> getApprovedList() {
        //这里我写死了，实际获取应该走权限框架获取当前在线用户username
        String username="xfc";
        LambdaQueryWrapper<ApprovalHistory>queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ApprovalHistory::getApproverUsername,username);
        List<ApprovalHistory> approvalHistories = baseMapper.selectList(queryWrapper);
        return approvalHistories;
    }
}
