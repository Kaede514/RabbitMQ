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
class Demo4ProducerApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void test() {
        for (int i = 0; i < 5; i++) {
            rabbitTemplate.convertAndSend("delay-exchange", "delay", "ack..." + i);
        }
    }

    @Test
    public void testPlugin() {
        // 创建消息
        Message msg1 = MessageBuilder.withBody("ack...1".getBytes(StandardCharsets.UTF_8))
            .setHeader("x-delay", 30000).build();
        Message msg2 = MessageBuilder.withBody("ack...2".getBytes(StandardCharsets.UTF_8))
            .setHeader("x-delay", 10000).build();
        CorrelationData data1 = new CorrelationData();
        CorrelationData data2 = new CorrelationData();
        // 发送消息，并传入当前数据的id
        rabbitTemplate.convertAndSend("delay-exchange", "delay", msg1, data1);
        rabbitTemplate.convertAndSend("delay-exchange", "delay", msg2, data2);
    }

}
