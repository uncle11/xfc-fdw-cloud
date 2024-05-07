package com.xfc.consumer.mq;

import com.rabbitmq.client.Channel;
import com.xfc.common.entities.MessageConfirmation;
import com.xfc.common.utils.JsonUtil;
import com.xfc.consumer.entities.MessageError;
import com.xfc.consumer.mapper.MessageErrorMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-05-04 15:42
 */
@Service
@Slf4j
public class DeadMqConsumer {
    @Autowired
    MessageErrorMapper messageErrorMapper;
    @RabbitListener(queues = {"dead.xfc.queue"})
    public void messageconsumer(String message, Channel channel,
                                CorrelationData correlationData,
                                @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        MessageConfirmation messageConfirmation=null;
        try {
            log.info("收到MQ的消息是: " + message );
            messageConfirmation= JsonUtil.string2Obj(message, MessageConfirmation.class);
            /**
             * 编写业务逻辑
             */
            int xfc=1;
            if(xfc==1){
                throw new RuntimeException("消费失败了");
            }
        } catch (Exception e) {
            e.printStackTrace();
            /**
             * 写入message_error
             */
            messageErrorMapper.insert(new MessageError(messageConfirmation.getId(),e.getMessage(),new Date()));
            channel.basicNack(tag,false,false);// 死信队列
        }
    }
}
