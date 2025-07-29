package com.cdpma.common.rabbitmq.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String RABBITMQ_USER_ACTION_TOPIC = "CDPMAUserActionTopic";

    public static final String RABBITMQ_EXCHANGE = "CDPMAExchange";

    public static final String RABBITMQ_USER_ACTION_ROUTING = "CDPMAUserActionRouting";

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
