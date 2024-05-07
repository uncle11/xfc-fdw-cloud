package com.xfc.producer.service.impl;

import com.xfc.common.entities.MessageConfirmation;
import com.xfc.common.utils.JsonUtil;
import com.xfc.producer.mapper.MessageConfirmationMapper;
import com.xfc.producer.service.IMessageConfirmationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xfc
 * @since 2024-05-04
 */
@Service
@Slf4j
public class MessageConfirmationServiceImpl extends ServiceImpl<MessageConfirmationMapper, MessageConfirmation> implements IMessageConfirmationService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    MessageConfirmationMapper messageConfirmationMapper;
    @PostConstruct
    public void regCallback() {
        // 消息发送成功以后，给予生产者的消息回执,来确保生产者的可靠性
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("cause:"+cause);
                // 如果ack为true代表消息已经收到
                String messageId = correlationData.getId();

                if (!ack) {
                    // 这里可能要进行其他的方式进行存储
                    log.error("MQ队列应答失败，messageId是:" + messageId);
                    return;
                }

                try {
                    MessageConfirmation messageConfirmation = messageConfirmationMapper.selectById(messageId);
                    messageConfirmation.setStatus(1);
                    int count=messageConfirmationMapper.updateById(messageConfirmation);
                    if (count == 1) {
                        log.info("本地消息状态修改成功，消息成功投递到消息队列中...");
                    }
                } catch (Exception ex) {
                    log.error("本地消息状态修改失败，出现异常：" + ex.getMessage());
                }
            }
        });
    }
    @Override
    public void sendMessage(MessageConfirmation messageConfirmation) {
        messageConfirmationMapper.insert(messageConfirmation);
        rabbitTemplate.convertAndSend("xfc_fanout_exchange","", JsonUtil.obj2String(messageConfirmation),
                new CorrelationData(messageConfirmation.getId()));
    }
}
