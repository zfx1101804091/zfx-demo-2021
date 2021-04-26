package com.zfx.rabbitmq.listen;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

@Component
public class MyAckReceiver implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        // 消息的唯一性ID
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            String msg = new String(message.getBody());
            System.out.println("消息: " + msg);
            System.out.println("消息来自: "+message.getMessageProperties().getConsumerQueue());

            // 手动确认
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            // 拒绝策略
            channel.basicReject(deliveryTag, false);
            e.printStackTrace();
        }
    }
}