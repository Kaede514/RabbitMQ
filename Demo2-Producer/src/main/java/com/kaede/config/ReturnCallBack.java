package com.kaede.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author kaede
 * @create 2023-01-19
 */

@Component
@Slf4j
public class ReturnCallBack implements RabbitTemplate.ReturnsCallback {

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.info("消息：{}被服务器退后，退回原因：{}，退回码：{}",
            returned.getMessage(), returned.getReplyText(), returned.getReplyCode());
    }

}
