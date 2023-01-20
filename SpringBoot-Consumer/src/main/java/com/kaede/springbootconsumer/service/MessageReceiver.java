package com.kaede.springbootconsumer.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author kaede
 * @create 2023-01-19
 */

@Service
public class MessageReceiver {

    // 指定消费队列
    @RabbitListener(queues = {"springboot-queue", "springboot-topic-queue"})
    public void receive(String message) {
        System.out.println("queue msg: " + message);
    }

}
