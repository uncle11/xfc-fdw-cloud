package com.xfc.workflow.service;

import com.xfc.workflow.dto.ApprovalDTO;
import com.xfc.workflow.entities.ApprovalDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 审批细节表 服务类
 * </p>
 *
 * @author xfc
 * @since 2024-06-16
 */
public interface IApprovalDetailService extends IService<ApprovalDetail> {

    List<ApprovalDetail> getPendingAppprovalList();

    Boolean approvalApplication(ApprovalDTO approvalDTO);

}
