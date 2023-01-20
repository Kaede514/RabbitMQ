package com.kaede;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo02ProducerApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void test() {
        // 设置当前数据的id，默认由UUID生成
        CorrelationData data = new CorrelationData();
        // 发送消息，并传入当前数据的id
        rabbitTemplate.convertAndSend("confirm-exchange", "ack1", "ack...", data);
    }

}
