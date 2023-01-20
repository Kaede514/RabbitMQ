package com.kaede.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author kaede
 * @create 2023-01-19
 */

public class ConsumerDemo1 {

    private final static String QUEUE_NAME = "direct_queue_1";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.138.128");
        factory.setUsername("root");
        factory.setPassword("123456");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        // 通道和队列的连接
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("[x1] Waiting for messages. To exit press CTRL+C");
        // 设置回调
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String msg = new String(message.getBody(), "UTF-8");
            System.out.println("[x] Received '" + msg + "'");
        };
        // 异步回调，消费者会一直等待消息的的到来
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
    }

}
