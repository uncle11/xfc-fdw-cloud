package com.xfc.workflow.service.strategy;

import com.xfc.workflow.dto.ApprovalDTO;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-06-30 17:46
 */
@Service
public class ApprovalLeaveRequestService {
    public Boolean approvalApplication(ApprovalDTO approvalDTO) {
        /**
         * 一样的逻辑，把对request表的操作改为leave_request
         */
        return true;
    }
}
