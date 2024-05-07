package com.xfc.producer.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xfc.common.entities.MessageConfirmation;
import com.xfc.common.utils.JsonUtil;
import com.xfc.producer.mapper.MessageConfirmationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-04-17 9:32
 */
@Configuration
@EnableScheduling
@Slf4j
public class confirmMessageTaskService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    MessageConfirmationMapper messageConfirmationMapper;

    @Scheduled(cron = "0 */1 * * * ?")
    public void sendMessage(){
        // 把消息为0的状态消息重新查询出来，投递到MQ中。
        LambdaQueryWrapper<MessageConfirmation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MessageConfirmation::getStatus, 0);
        List<MessageConfirmation> noConfirmMessages = messageConfirmationMapper.selectList(queryWrapper)
                .stream()
                .collect(Collectors.toList());
        noConfirmMessages.forEach((noConfirmMessage)->{
            rabbitTemplate.convertAndSend("xz_push_exchange","", JsonUtil.obj2String(noConfirmMessage),
                    new CorrelationData(noConfirmMessage.getId()));
        });
    }
}
