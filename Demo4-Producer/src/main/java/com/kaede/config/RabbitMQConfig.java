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

    public final static String DELAY_EXCHANGE_NAME = "delay-exchange";
    public final static String DEAD_EXCHANGE_NAME = "dead-exchange";
    public final static String DELAY_QUEUE_NAME = "delay-queue";
    public final static String DEAD_QUEUE_NAME = "dead-delay-queue";

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
            .with("dl");
    }

    /* @Bean
    public Queue delayQueue() {
        return QueueBuilder.durable(DELAY_QUEUE_NAME).ttl(60000)
            .deadLetterExchange(DEAD_EXCHANGE_NAME)
            .deadLetterRoutingKey("dl")
            .build();
    }
    @Bean
    public DirectExchange delayExchange() {
        return ExchangeBuilder.directExchange(DELAY_EXCHANGE_NAME).build();
    }
    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(delayQueue())
            .to(delayExchange())
            .with("delay");
    } */

    @Bean
    public Queue delayQueue() {
        return new Queue(DELAY_QUEUE_NAME);
    }
    // 使用插件的延迟交换机
    @Bean
    public DirectExchange delayExchange() {
        return ExchangeBuilder.directExchange(DELAY_EXCHANGE_NAME)
            .delayed().build();
    }
    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(delayQueue())
            .to(delayExchange())
            .with("delay");
    }

}
