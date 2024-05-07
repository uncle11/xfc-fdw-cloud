package com.xfc.producer.controller;

import com.xfc.common.entities.MessageConfirmation;
import com.xfc.producer.service.IMessageConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xfc
 * @since 2024-05-04
 */
@RestController
@RequestMapping("/message-confirmation")
public class MessageConfirmationController {
    @Autowired
    IMessageConfirmationService messageConfirmationService;
    @PostMapping("/message")
    public String sendMessage(@RequestBody MessageConfirmation messageConfirmation){
       messageConfirmationService.sendMessage(messageConfirmation);
       return messageConfirmation.getMessage();
    }
}
