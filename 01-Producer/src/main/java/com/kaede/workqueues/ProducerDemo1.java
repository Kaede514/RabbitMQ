package com.kaede.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * @author kaede
 * @create 2023-01-19
 */

public class ProducerDemo1 {

    private final static String QUEUE_NAME = "work_queues";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.138.128");
        factory.setUsername("root");
        factory.setPassword("123456");
        try (Connection conn = factory.newConnection();
             Channel channel = conn.createChannel()) {
            // 通道和队列的连接
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            for (int i = 0; i < 10; i++) {
                String msg = "Hello RabbitMQ! " + i;
                // 通过最基础的发布
                channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
                System.out.println("[x] Sent '" + msg + "'");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
