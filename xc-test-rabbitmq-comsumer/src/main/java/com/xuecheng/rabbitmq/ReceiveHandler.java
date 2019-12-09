package com.xuecheng.rabbitmq;

import com.rabbitmq.client.Channel;
import com.xuecheng.rabbitmq.config.RabbitmqConsumerConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Daniel Liu 2019/12/9 8:44
 */
@Component
public class ReceiveHandler {

    @RabbitListener( queues = {RabbitmqConsumerConfig.QUEUE_INFORM_SMS} )
    public void receiveMsg(String msg, Message message, Channel channel) {
        System.out.println(msg);
    }

    @RabbitListener( queues = RabbitmqConsumerConfig.QUEUE_INFORM_EMAIL )
    public void receiveEmailMsg(String msg, Message message, Channel channel) {
        System.out.println(msg);
    }
}