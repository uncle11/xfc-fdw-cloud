package com.xfc.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-05-04 15:01
 */
@Configuration
public class RabbitMQConfig {
    /**
     * 死信队列
     * @return
     */
    @Bean
    public FanoutExchange deadExchange() {
        return new FanoutExchange("dead_xfc_fanout_exchange", true, false);
    }

    @Bean
    public Queue deadXfcQueue() {
        return new Queue("dead.xfc.queue", true);
    }
    @Bean
    public Binding bindDeadXfc() {
        return BindingBuilder.bind(deadXfcQueue()).to(deadExchange());
    }

    /**
     * 队列
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("xfc_fanout_exchange", true, false);
    }

    @Bean
    public Queue xfcQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", "dead_xfc_fanout_exchange");
        return new Queue("xfc.queue", true, false, false, args);
    }

    @Bean
    public Binding bindXfc() {
        return BindingBuilder.bind(xfcQueue()).to(fanoutExchange());
    }
}
