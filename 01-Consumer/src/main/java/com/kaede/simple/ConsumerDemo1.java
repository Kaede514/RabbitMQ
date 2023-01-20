package com.kaede.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author kaede
 * @create 2023-01-19
 */

public class ConsumerDemo1 {

    // 队列名称
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 建立连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置目标主机IP
        factory.setHost("192.168.138.128");
        // 设置账号名密码
        factory.setUsername("root");
        factory.setPassword("123456");
        // 有默认访问的端口5672，若修改了端口，可在此处设置
        // factory.setPort();
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        // 通道和队列的连接
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [x] Waiting for messages. To exit press CTRL+C");
        // 设置回调
        /**
         * String consumerTag：消费者标识
         * Delivery message：消息
         */
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String msg = new String(message.getBody(), "UTF-8");
            System.out.println("[x] Received '" + msg + "'");
        };
        // 异步回调，消费者会一直等待消息的的到来
        /**
         * String queue：队列名称
         * boolean autoAck：是否自动确认
         * DeliverCallback deliverCallback：消费的回调对象
         * CancelCallback cancelCallback：取消的回调对象
         */
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
    }

}
