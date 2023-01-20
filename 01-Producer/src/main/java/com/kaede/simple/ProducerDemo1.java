package com.kaede.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author kaede
 * @create 2023-01-19
 */

public class ProducerDemo1 {

    // 队列名称
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        // 建立连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置目标主机IP
        factory.setHost("192.168.138.128");
        // 设置账号名密码
        factory.setUsername("root");
        factory.setPassword("123456");
        // 有默认访问的端口5672，若修改了端口，可在此处设置
        // factory.setPort();
        // 自动关闭资源
        try (Connection conn = factory.newConnection();
             Channel channel = conn.createChannel()) {
            // 通道和队列的连接
            /**
             * 参数说明
             * String queue：队列名称
             * boolean durable：是否持久化
             * boolean exclusive：是否独占，即是否只有一个消费者监听该队列
             * boolean autoDelete：是否自动删除队列，即若没有消费者，是否自动删除该队列
             * Map<String, Object> arguments：参数
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            // 需要发送的消息
            String msg = "Hello RabbitMQ!";
            // 通过最基础的发布
            /**
             * String exchange：指定交换机，若使用默认模式，则使用""代表默认交换机
             * String routingKey：路由名称
             * BasicProperties props：配置信息
             * byte[] body：发送的消息（要求字节数组）
             */
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println("[x] Sent '" + msg + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
