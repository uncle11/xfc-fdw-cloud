package com.xfc.workflow.controller;


import com.xfc.workflow.dto.LeaveRequestDTO;
import com.xfc.workflow.service.ILeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xfc
 * @since 2024-06-30
 */
@RestController
@RequestMapping("/leave-request")
public class LeaveRequestController {

    @Autowired
    ILeaveRequestService leaveRequestService;

    @GetMapping("")
    public Boolean addRequest(@Validated @RequestBody LeaveRequestDTO leaveRequestDTO){
      return leaveRequestService.addRequest(leaveRequestDTO);
    }

}
