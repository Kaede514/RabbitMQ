package com.kaede.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kaede
 * @create 2023-01-19
 */

@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Getter
@Setter
public class RabbitMQConfig {

    private String host;
    private Integer port;
    private String username;
    private String password;

    public final static String CONFIRM_EXCHANGE_NAME = "confirm-exchange";
    public final static String BACKUP_EXCHANGE_NAME = "backup-exchange";
    public final static String CONFIRM_QUEUE_NAME = "confirm-queue";
    public final static String BACKUP_QUEUE_NAME = "backup-queue";

    @Bean("myRabbitTemplate")
    public RabbitTemplate rabbitTemplate(AckCallBack ackCallBack, ReturnCallBack returnCallBack) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory());
        // 开启确认的回调
        rabbitTemplate.setConfirmCallback(ackCallBack);
        // 设置消息投递，默认为false
        // true表示交换机无法将消息路由时会发给生产者
        // false表示交换机无法将消息路由时会丢弃
        rabbitTemplate.setMandatory(true);
        // 开启回退的回调
        rabbitTemplate.setReturnsCallback(returnCallBack);
        return rabbitTemplate;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        // 创建缓存连接工厂
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        // 设置确认类型，默认是NONE，不开启
        // SIMPLE与CORRELATED都会在发布消息后触发回调方法，区别：
        // SIMPLE：若返回的确认为false，则会关闭通道
        // CORRELATED：若返回的确认为false，则不会关闭通道
        factory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        return factory;
    }

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
