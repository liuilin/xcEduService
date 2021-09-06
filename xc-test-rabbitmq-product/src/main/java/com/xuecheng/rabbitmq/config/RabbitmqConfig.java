package com.xuecheng.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Daniel Liu 2019/12/8 22:00
 */
@Configuration
public class RabbitmqConfig {
    /**
     * 短信队列.
     */
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";

    /**
     * 邮箱队列
     */
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";

    /**
     * Topic交换机类型
     */
    static final String EXCHANGE_INFORM_TOPIC = "exchange_inform_topic";

    @Bean( QUEUE_INFORM_SMS )
    public Queue QUEUE_INFORM_SMS() {
        return new org.springframework.amqp.core.Queue(QUEUE_INFORM_SMS);
    }

    @Bean( QUEUE_INFORM_EMAIL )
    public Queue QUEUE_INFORM_EMAIL() {
        return new Queue(QUEUE_INFORM_EMAIL);
    }

    /**
     * ExchangeBuilder提供了fanout、direct、topic、header交换机类型的配置
     *
     * @return exchange
     */
    @Bean( EXCHANGE_INFORM_TOPIC )
    public Exchange EXCHANGE_INFORM_TOPIC() {
        return ExchangeBuilder.topicExchange(EXCHANGE_INFORM_TOPIC).durable(true).build();
    }

    /**
     * qualifier制定用哪个bean对象，上面bean对象起了个名字
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_SMS(@Qualifier( QUEUE_INFORM_SMS ) Queue queue, @Qualifier( EXCHANGE_INFORM_TOPIC ) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.sms.#").noargs();
    }

    @Bean
    public Binding BINDING_QUEUE_INFORM_EMAIL(@Qualifier( QUEUE_INFORM_EMAIL ) Queue queue, @Qualifier( EXCHANGE_INFORM_TOPIC ) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.email.#").noargs();
    }


}