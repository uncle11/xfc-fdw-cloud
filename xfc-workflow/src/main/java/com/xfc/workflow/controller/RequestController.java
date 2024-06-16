package com.xfc.workflow.controller;


import com.xfc.workflow.dto.RequestDTO;
import com.xfc.workflow.entities.Request;
import com.xfc.workflow.service.IRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 请求表 前端控制器
 * </p>
 *
 * @author xfc
 * @since 2024-06-16
 */
@RestController
@RequestMapping("/request")
public class RequestController {
    @Autowired
    IRequestService requestService;

    @GetMapping("")
    public Boolean addRequest(@RequestBody RequestDTO requestDTO){
      return requestService.addRequest(requestDTO);
    }

}
