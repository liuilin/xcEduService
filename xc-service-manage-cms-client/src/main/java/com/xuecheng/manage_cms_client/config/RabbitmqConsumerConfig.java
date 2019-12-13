package com.xuecheng.manage_cms_client.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * MQ消费者配置类
 *
 * @author Daniel Liu 2019/12/13 8:02
 */
@Component//@configuration不行
public class RabbitmqConsumerConfig {

    /**
     * 队列bean's name
     */
    private static final String QUEUE_CMS_POSTPAGE = "queue_cms_postpage";
    /**
     * 定义交换机
     */
    private static final String EX_ROUTING_CMS_POSTPAGE = "ex_routing_cms_postpage";

    /**
     * 队列的名称
     */
    @Value( "${xuecheng.mq.queue}" )
    private String queue_cms_postpage_name;

    /**
     * routingKey:siteId
     */
    @Value( "${xuecheng.mq.routingKey}" )
    private String routingKey;

    /**
     * 读取队列名称创建队列
     *
     * @return
     */
    @Bean( QUEUE_CMS_POSTPAGE )
    public Queue QUEUE_CMS_POSTPAGE() {
        return new Queue(queue_cms_postpage_name);
    }

    @Bean( EX_ROUTING_CMS_POSTPAGE )
    public Exchange EX_ROUTING_CMS_POSTPAGE() {
        return ExchangeBuilder.directExchange(EX_ROUTING_CMS_POSTPAGE).durable(true).build();
    }

    @Bean
    public Binding BINDING_CMS_POSTPAGE(@Qualifier( QUEUE_CMS_POSTPAGE ) Queue queue, @Qualifier( EX_ROUTING_CMS_POSTPAGE ) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }

}