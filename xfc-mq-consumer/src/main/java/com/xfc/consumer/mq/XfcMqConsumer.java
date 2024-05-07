package com.xfc.consumer.mq;

import com.rabbitmq.client.Channel;
import com.xfc.common.entities.MessageConfirmation;
import com.xfc.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-05-04 15:41
 */
@Service
@Slf4j
public class XfcMqConsumer {
    @RabbitListener(queues = {"xfc.queue"})
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
            log.error("消息投放到死信队列"+e.getMessage(),e);
            channel.basicNack(tag,false,false);// 死信队列
        }
    }
}
