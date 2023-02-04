package com.kaede.springbootconsumer.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    @RabbitListener(queues = {"object.queue"})
    public void listenObjectQueue(Map<String, Object> msg) {
        System.out.println("msg = " + msg);
    }

}
