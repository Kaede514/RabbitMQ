package com.kaede.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CommonConfig implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 获取RabbitTemplate
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        // 设置ConfirmCallback
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            String id = correlationData != null ? correlationData.getId() : "";
            if (ack) {
                log.info("交换机已收到id:{}的消息", id);
            } else {
                log.error("交换机未收到id:{}的消息", id);
                log.error("cause:{}", cause);
            }
        });
        // 设置ReturnCallback
        rabbitTemplate.setReturnsCallback((returned) -> {
            log.info("消息：{}被服务器退后，退回原因：{}，退回码：{}",
                returned.getMessage(), returned.getReplyText(), returned.getReplyCode());
        });
    }

}