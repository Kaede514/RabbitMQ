package com.kaede;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo3ProducerApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void test() {
        // 设置当前数据的id，默认由UUID生成
        CorrelationData data = new CorrelationData();
        // 消息后置处理器
        MessagePostProcessor postProcessor = (msg) -> {
            MessageProperties properties = msg.getMessageProperties();
            // 设置过期时间，单位为ms
            properties.setExpiration("5000");
            return msg;
        };
        // 发送消息，并传入当前数据的id
        rabbitTemplate.convertAndSend("test-exchange", "ack", "ack...", data);
    }

    @Test
    public void testMaxLength() {
        for (int i = 0; i < 11; i++) {
            rabbitTemplate.convertAndSend("test-exchange", "ack", "ack..." + i);
        }
    }

    @Test
    public void testTTL() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("test-exchange", "ack", "ack..." + i);
        }
    }

    @Test
    public void testReject() {
        for (int i = 0; i < 5; i++) {
            rabbitTemplate.convertAndSend("test-exchange", "ack", i);
        }
    }

}
