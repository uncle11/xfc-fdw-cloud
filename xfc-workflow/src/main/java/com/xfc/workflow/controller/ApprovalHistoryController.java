package com.xfc.workflow.controller;


import com.xfc.workflow.entities.ApprovalDetail;
import com.xfc.workflow.entities.ApprovalHistory;
import com.xfc.workflow.service.IApprovalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xfc
 * @since 2024-06-16
 */
@RestController
@RequestMapping("/approval-history")
public class ApprovalHistoryController {
    @Autowired
    IApprovalHistoryService approvalHistoryService;
    @GetMapping("/approved")
    public List<ApprovalHistory> getApprovedList() {
        return approvalHistoryService.getApprovedList();
    }
}
