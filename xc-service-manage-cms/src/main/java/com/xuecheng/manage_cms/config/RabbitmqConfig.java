package com.xuecheng.manage_cms.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 生成方mq配置类
 *
 * @author Daniel Liu 2019/12/13 10:01
 */
@Component
public class RabbitmqConfig {

    /**
     * 交换机
     */
    public static final String EX_ROUTING_CMS_POSTPAGE = "ex_routing_cms_postpage";

    @Bean( EX_ROUTING_CMS_POSTPAGE )
    public Exchange EX_ROUTING_CMS_POSTPAGE() {
        return ExchangeBuilder.directExchange(EX_ROUTING_CMS_POSTPAGE).durable(true).build();
    }

}