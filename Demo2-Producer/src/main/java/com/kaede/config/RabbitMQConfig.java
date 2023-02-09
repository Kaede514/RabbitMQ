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

    public final static String CONFIRM_EXCHANGE_NAME = "confirm-exchange";
    public final static String BACKUP_EXCHANGE_NAME = "backup-exchange";
    public final static String CONFIRM_QUEUE_NAME = "confirm-queue";
    public final static String BACKUP_QUEUE_NAME = "backup-queue";

    @Bean
    public Queue confirmQueue() {
        return new Queue(CONFIRM_QUEUE_NAME);
    }
    @Bean
    public DirectExchange confirmExchange() {
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME)
            // 指定备份交换机及其名称
            .withArgument("alternate-exchange", BACKUP_EXCHANGE_NAME).build();
    }
    @Bean
    public Binding confirmBinding() {
        return BindingBuilder.bind(confirmQueue())
            .to(confirmExchange())
            .with("ack");
    }

    // 备份队列
    @Bean
    public Queue backupQueue() {
        return new Queue(BACKUP_QUEUE_NAME);
    }
    // 备份交换机
    @Bean
    public FanoutExchange backupExchange() {
        return ExchangeBuilder.fanoutExchange(BACKUP_EXCHANGE_NAME).build();
    }
    @Bean
    public Binding backupBinding() {
        return BindingBuilder.bind(backupQueue()).to(backupExchange());
    }

}
