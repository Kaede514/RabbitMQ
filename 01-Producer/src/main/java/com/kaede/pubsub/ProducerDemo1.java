package com.kaede.pubsub;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author kaede
 * @create 2023-01-19
 */

public class ProducerDemo1 {

    // 交换机名称
    private final static String EXCHANGE_NAME = "logs";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.138.128");
        factory.setUsername("root");
        factory.setPassword("123456");
        try (Connection conn = factory.newConnection();
             Channel channel = conn.createChannel()) {
            // 通道和交换机的绑定
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
            // 创建队列
            String queue1 = "fanout_queue_1";
            String queue2 = "fanout_queue_2";
            channel.queueDeclare(queue1, false, false, false, null);
            channel.queueDeclare(queue2, false, false, false, null);
            // 绑定队列
            /**
             * 广播模式下，routingKey指定为空字符串
             */
            channel.queueBind(queue1, EXCHANGE_NAME, "");
            channel.queueBind(queue2, EXCHANGE_NAME, "");
            for (int i = 0; i < 5; i++) {
                String msg = "Hello RabbitMQ! " + i;
                // 通过最基础的发布
                channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
                System.out.println("[x] Sent '" + msg + "'");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
