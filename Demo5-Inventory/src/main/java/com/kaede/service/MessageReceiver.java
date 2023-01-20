package com.kaede.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kaede
 * @create 2023-01-19
 */

@Service
public class MessageReceiver {

    @Data
    @AllArgsConstructor
    class Order {
        private String key;
        // true代表已支付，false代表未支付
        private boolean state;
        private int count;  // 库存
    }

    Map<String, Order> inventoryMap = new HashMap<>();
    {
        inventoryMap.put("1", new Order("1", false, 10));
        inventoryMap.put("1", new Order("2", true, 5));
    }

    // 指定消费队列
    @RabbitListener(queues = {"dead-delay-queue"})
    public void receive(String body) {
        System.out.println("body = " + body);
        Order order = inventoryMap.get(body);
        System.out.println(order);
        if (!order.isState()) {
            order.setCount(order.getCount()+1);
        }
    }

}
