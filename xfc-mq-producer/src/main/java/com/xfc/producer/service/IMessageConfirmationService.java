package com.xfc.producer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xfc.common.entities.MessageConfirmation;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xfc
 * @since 2024-05-04
 */
public interface IMessageConfirmationService extends IService<MessageConfirmation> {
    public void sendMessage(MessageConfirmation messageConfirmation);
}
