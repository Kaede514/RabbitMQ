package com.kaede.demoorder;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoOrderApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void test() {
        // 发送消息
        rabbitTemplate.convertAndSend("orderExchange", "order.save", "new order need to handle...");
    }

}
