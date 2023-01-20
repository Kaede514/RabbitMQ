package com.kaede.demopay.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author kaede
 * @create 2023-01-19
 */

@Service
public class MessageReceiver {

    // 指定消费队列
    @RabbitListener(queues = {"pay_queue"})
    public void receive(String message) {
        System.out.println("Pay Center listen: " + message);
        System.out.println("Start adding payment orders...");
    }

}
