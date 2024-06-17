package com.xfc.workflow.service;

import com.xfc.workflow.entities.ApprovalDetail;
import com.xfc.workflow.entities.ApprovalHistory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xfc
 * @since 2024-06-16
 */
public interface IApprovalHistoryService extends IService<ApprovalHistory> {

    List<ApprovalHistory> getApprovedList();
}
