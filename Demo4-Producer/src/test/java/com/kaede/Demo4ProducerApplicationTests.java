package com.kaede;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo4ProducerApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void test() {
        for (int i = 0; i < 5; i++) {
            rabbitTemplate.convertAndSend("delay-exchange", "ack", "ack..." + i);
        }
    }

    @Test
    public void testPlugin() {
        // 消息后置处理器
        MessagePostProcessor postProcessor1 = (msg) -> {
            MessageProperties properties = msg.getMessageProperties();
            // 设置过期时间，单位为ms
            properties.setDelay(30000);
            return msg;
        };
        MessagePostProcessor postProcessor2 = (msg) -> {
            MessageProperties properties = msg.getMessageProperties();
            // 设置过期时间，单位为ms
            properties.setDelay(10000);
            return msg;
        };
        // 发送消息，并传入当前数据的id
        rabbitTemplate.convertAndSend("delay-exchange", "ack", "ack...1", postProcessor1);
        rabbitTemplate.convertAndSend("delay-exchange", "ack", "ack...2", postProcessor2);
    }

}
