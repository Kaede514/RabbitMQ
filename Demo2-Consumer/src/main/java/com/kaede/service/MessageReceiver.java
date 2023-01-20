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
    @RabbitListener(queues = {"backup-queue"})
    public void receive(String body, Channel channel, Message message) {
        try {
            System.out.println("queue msg: " + body);
            // 开启业务处理
            int i = 1 / 0;
            // 业务处理结束
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 判断消息是否重复处理
            if (message.getMessageProperties().getRedelivered()) {
                // 拒绝签收
                try {
                    /**
                     * deliveryTag：所收到消息的唯一标识
                     * requeue：true表示拒绝的消息应重新返回给队列，然后重试，而不是丢入死信队列
                     */
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                // 返回队列，重新发送
                try {
                    /**
                     * deliveryTag：所收到消息的唯一标识
                     * multiple：true表示拒绝所有的消息
                     * requeue：true表示拒绝的消息应重新返回给队列，然后重试，而不是丢入死信队列
                     */
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
