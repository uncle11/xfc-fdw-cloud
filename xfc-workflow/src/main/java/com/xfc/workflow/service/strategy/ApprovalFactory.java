package com.xfc.workflow.service.strategy;

import com.xfc.workflow.dto.ApprovalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-06-18 13:19
 */
@Service
public class ApprovalFactory {
    @Autowired
    ApprovalDataRequestService approvalDataRequestService;
    @Autowired
    ApprovalLeaveRequestService approvalLeaveRequestService;
    private static Map<String, Function<ApprovalDTO,Boolean>> approvalMap = null;
    @PostConstruct
    public void init(){
        approvalMap=new HashMap<>();
        approvalMap.put("2",approvalDTO->approvalDataRequestService.approvalApplication(approvalDTO));
        approvalMap.put("1",approvalDTO ->approvalLeaveRequestService.approvalApplication(approvalDTO));
    }

    public Boolean approvalApplication(ApprovalDTO approvalDTO) {
       return approvalMap.get(approvalDTO.getWorkflowId()).apply(approvalDTO);
    }
}
