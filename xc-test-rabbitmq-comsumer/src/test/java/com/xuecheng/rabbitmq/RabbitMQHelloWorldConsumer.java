package com.xuecheng.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author liuqiang
 * @since 2021-09-02
 */
public class RabbitMQHelloWorldConsumer {

    private static final String QUEUE = "Hello World";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = null;
        Channel channel = null;
//        try {
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
            // 定义消费方法
            DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
                /**
                 * 消费者接收消息时调用此方法
                 * @param consumerTag 消费者的标签，在 channel.basicConsume () 去指定
                 * @param envelope 消息包的内容，可从中获取消息 id，消息 routingKey，交换机，消息和重传标志 (收到消息失败后是否需要重新发送)
                 * @param properties 消息属性
                 * @param body 消息内容
                 * @throws IOException
                 */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    // 交换机
                    String exchange = envelope.getExchange();
                    // 路由 key
                    String routingKey = envelope.getRoutingKey();
                    // 消息 id
                    long deliveryTag = envelope.getDeliveryTag();
                    // 消息内容
                    String msg = new String(body, StandardCharsets.UTF_8);
                    System.out.println("msg = " + msg);
                }
            };
            /*
              监听队列 String queue, boolean autoAck,Consumer callback
              参数明细
              1、队列名称
              2、是否自动回复，设置为 true 为表示消息接收到自动向 mq 回复接收到了，mq 接收到回复会删除消息，设置为 false 则需要手动回复
              3、消费消息的方法，消费者接收到消息后调用此方法
             */
            channel.basicConsume(QUEUE, true, defaultConsumer);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            if (channel != null) {
//                channel.close();
//            }
//            if (connection != null) {
//                connection.close();
//            }
//        }
    }
}