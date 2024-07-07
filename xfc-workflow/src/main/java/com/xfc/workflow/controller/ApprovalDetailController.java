package com.xfc.workflow.controller;


import com.xfc.workflow.dto.ApprovalDTO;
import com.xfc.workflow.entities.ApprovalDetail;
import com.xfc.workflow.service.IApprovalDetailService;
import com.xfc.workflow.service.strategy.ApprovalFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 审批细节表 前端控制器
 * </p>
 *
 * @author xfc
 * @since 2024-06-16
 */
@RestController
@RequestMapping("/approval-detail")
public class ApprovalDetailController {
    @Autowired
    IApprovalDetailService approvalDetailService;

    @Autowired
    ApprovalFactory approvalFactory;

    @GetMapping("/pending-approval")
    public List<ApprovalDetail> getPendingAppprovalList() {
        return approvalDetailService.getPendingAppprovalList();
    }

    @PostMapping("/approval")
    public Boolean approvalApplication(@Validated @RequestBody ApprovalDTO approvalDTO) {
        return approvalFactory.approvalApplication(approvalDTO);
    }



}
