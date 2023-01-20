package com.kaede.demoinventory.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author kaede
 * @create 2023-01-19
 */

@Service
public class MessageReceiver {

    // 指定消费队列
    @RabbitListener(queues = {"inventory_queue"})
    public void receive(String message) {
        System.out.println("Inventory Center listen: " + message);
        System.out.println("Start deducting inventory...");
    }

}
