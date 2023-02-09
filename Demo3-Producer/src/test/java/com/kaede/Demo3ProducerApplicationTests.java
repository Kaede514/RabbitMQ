package com.kaede;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

@SpringBootTest
class Demo3ProducerApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    // 消息设置过期时间
    @Test
    public void test() {
        // 创建消息
        Message msg = MessageBuilder.withBody("ack...".getBytes(StandardCharsets.UTF_8))
            .setExpiration("5000").build();
        // 设置当前数据的id，默认由UUID生成
        CorrelationData data = new CorrelationData();
        // 发送消息，并传入当前数据的id
        rabbitTemplate.convertAndSend("test-exchange", "ack", msg, data);
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
