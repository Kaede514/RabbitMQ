package com.kaede.controller;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kaede
 * @create 2023-01-20
 */

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostMapping("/save")
    public String saveOrder(Long id) {
        CorrelationData data = new CorrelationData(id.toString());
        rabbitTemplate.convertAndSend("delay-exchange", "ack", id, data);
        return "OK " + id;
    }

}
