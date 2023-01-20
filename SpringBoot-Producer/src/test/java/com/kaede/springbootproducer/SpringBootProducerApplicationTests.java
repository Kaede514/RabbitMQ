package com.kaede.springbootproducer;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootProducerApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void test() {
        // 发送消息
        rabbitTemplate.convertAndSend("springboot-queue", "Hello RabbitMQ");
        rabbitTemplate.convertAndSend("topicExchange", "jd.order", "Hello topic");
    }

}
