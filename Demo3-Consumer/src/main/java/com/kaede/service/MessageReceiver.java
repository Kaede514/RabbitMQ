package com.kaede.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author kaede
 * @create 2023-01-19
 */

@Service
public class MessageReceiver {

    // 指定消费队列
    @RabbitListener(queues = {"test-queue"})
    public void receive(String body, Channel channel, Message message) {
        try {
            // 开启业务处理
            if (Integer.parseInt(body) % 2 == 0) {
                int i = 1 / 0;
            }
            // 业务处理结束
            System.out.println("msg: " + body);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 判断消息是否重复处理
            if (message.getMessageProperties().getRedelivered()) {
                // 拒绝签收
                try {
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                } catch (IOException ex) {

                }
            } else {
                // 返回队列，重新发送
                try {
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                } catch (IOException ex) {

                }
            }
        }
    }

}
