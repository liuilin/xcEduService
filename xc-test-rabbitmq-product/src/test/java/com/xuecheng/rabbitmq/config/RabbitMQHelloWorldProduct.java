package com.xuecheng.rabbitmq.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author liuqiang
 * @since 2021-09-02
 */
public class RabbitMQHelloWorldProduct {

    private static final String QUEUE = "Hello World";

    public static void main(String[] args) throws IOException, TimeoutException {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
////        factory.setPort();
//        factory.setUsername("guest");
//        factory.setPassword("guest");
//        try (Connection connection = factory.newConnection()) {
//            Channel channel = connection.createChannel();
//            channel.basicPublish();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            // rabbitmq 默认虚拟机名称为 “/”，虚拟机相当于一个独立的 mq 服务器
            factory.setVirtualHost("/");
            // 创建与 RabbitMQ 服务的 TCP 连接
            connection = factory.newConnection();
            // 创建与 Exchange 的通道，每个连接可以创建多个通道，每个通道代表一个会话任务
            channel = connection.createChannel();
            /*
              声明队列，如果 Rabbit 中没有此队列将自动创建
              param1: 队列名称
              param2: 是否持久化
              param3: 队列是否独占此连接
              param4: 队列不再使用时是否自动删除此队列
              param5: 队列参数
             */
            channel.queueDeclare(QUEUE, true, false, false, null);
            String message = "hello world" + System.currentTimeMillis();
            /*
              消息发布方法
              param1: Exchange 的名称，如果没有指定，则使用 Default Exchange
              param2: routingKey, 消息的路由 Key，是用于 Exchange（交换机）将消息转发到指定的消息队列
              param3: 消息包含的属性
              param4: 消息体
             */
            /*
              这里没有指定交换机，消息将发送给默认交换机，每个队列也会绑定那个默认的交换机，但是不能显示绑定或解除绑定
              默认的交换机，routingKey 等于队列名称
             */
            channel.basicPublish("", QUEUE, null, message.getBytes());
            System.out.println("Send Message is:'" + message + "'");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}