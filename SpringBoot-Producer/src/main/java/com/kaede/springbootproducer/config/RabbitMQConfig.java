package com.kaede.springbootproducer.config;

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

    // 创建队列 org.springframework.amqp.core.Queue
    @Bean
    public Queue helloQueue() {
        return new Queue("springboot-queue");
    }

    // 创建队列
    @Bean
    public Queue topicQueue() {
        return new Queue("springboot-topic-queue");
    }
    // 创建交换机
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }
    // 绑定交换机与消息队列
    @Bean
    public Binding topicBinding() {
        return BindingBuilder.bind(topicQueue())    // 绑定队列
            .to(topicExchange())    // 绑定交换机
            .with("jd.#");   // 指定routingKey
    }

}
