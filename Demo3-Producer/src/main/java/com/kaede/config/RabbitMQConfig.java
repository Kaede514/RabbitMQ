package com.kaede.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kaede
 * @create 2023-01-19
 */

@Configuration
public class RabbitMQConfig {

    public final static String TEST_EXCHANGE_NAME = "test-exchange";
    public final static String DEAD_EXCHANGE_NAME = "dead-exchange";
    public final static String TEST_QUEUE_NAME = "test-queue";
    public final static String DEAD_QUEUE_NAME = "dead-queue";

    @Bean
    public Queue testQueue() {
        // 普通队列
        // return new Queue(TEST_QUEUE_NAME);
        // 设置过期队列
        Queue queue = new Queue(TEST_QUEUE_NAME);
        // 给队列绑定死信交换机
        queue.addArgument("x-dead-letter-exchange", DEAD_EXCHANGE_NAME);
        // 限制队列最大长度
        // queue.addArgument("x-max-length", 10);
        // 设置队列的过期时间
        // queue.addArgument("x-message-ttl", 5000);
        return queue;
    }
    @Bean
    public DirectExchange testExchange() {
        return ExchangeBuilder.directExchange(TEST_EXCHANGE_NAME).build();
    }
    @Bean
    public Binding testBinding() {
        return BindingBuilder.bind(testQueue())
            .to(testExchange())
            .with("ack");
    }

    // 死信交换机和死信队列
    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange(DEAD_EXCHANGE_NAME);
    }
    @Bean
    public Queue deadQueue() {
        return new Queue(DEAD_QUEUE_NAME);
    }
    @Bean
    public Binding deadBinding() {
        return BindingBuilder.bind(deadQueue())
            .to(deadExchange())
            .with("ack");
    }

}
