package com.kaede.demoorder.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kaede
 * @create 2023-01-19
 */

@Configuration
public class RabbitMQConfig {

    // 库存中心队列
    @Bean
    public Queue inventoryQueue() {
        return new Queue("inventory_queue");
    }
    // 支付中心队列
    @Bean
    public Queue payQueue() {
        return new Queue("pay_queue");
    }
    // 创建交换机
    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange("orderExchange");
    }
    // 绑定交换机与消息队列
    @Bean
    public Binding inventoryBinding() {
        return BindingBuilder.bind(inventoryQueue())
            .to(orderExchange())
            .with("order.*");
    }
    @Bean
    public Binding payBinding() {
        return BindingBuilder.bind(payQueue())
            .to(orderExchange())
            .with("order.*");
    }

}
