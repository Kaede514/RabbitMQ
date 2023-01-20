package com.kaede.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author kaede
 * @create 2023-01-19
 */

@Component
@Slf4j
public class AckCallBack implements RabbitTemplate.ConfirmCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (ack) {
            log.info("交换机已收到id:{}的消息", id);
        } else {
            log.error("交换机未收到id:{}的消息", id);
            log.error("cause:{}", cause);
        }
    }

}
