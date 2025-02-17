package com.store.seller.rabbitMq;

import com.store.seller.config.KeywordsAndConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class RabbitMqConfiguration {
    @Bean
    public Queue eventQueue() {
        return new Queue(KeywordsAndConstants.RABBIT_MQ_QUEUE_FOR_EVENTS);
    }

    @Bean
    public Queue requestSanitationQueue() {
        return new Queue(KeywordsAndConstants.RABBIT_MQ_QUEUE_FOR_REQUEST_SANITATION);
    }

    @Bean
    public Queue topicForexDataQueue() {
        return new Queue(KeywordsAndConstants.RABBIT_MQ_QUEUE_FOR_FOREX_DATA);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(KeywordsAndConstants.RABBIT_MQ_EXCHANGE);
    }

    @Bean
    public Queue processEmail(){
        return new Queue(KeywordsAndConstants.RABBIT_MQ_QUEUE_FOR_LOGIN_OR_SIGNUP_OTP);
    }

    @Bean
    public Declarables bindings(
            Queue eventQueue,
            Queue requestSanitationQueue,
            Queue topicForexDataQueue,
            TopicExchange topicExchange,
            Queue processEmail
    ) {
        return new Declarables(
                eventQueue,
                requestSanitationQueue,
                topicForexDataQueue,
                topicExchange,
                processEmail,
                BindingBuilder.bind(eventQueue)
                        .to(topicExchange)
                        .with(KeywordsAndConstants.RABBIT_MQ_ROUTE_KEY_FOR_EVENTS),
                BindingBuilder.bind(topicForexDataQueue)
                        .to(topicExchange)
                        .with(KeywordsAndConstants.RABBIT_MQ_ROUTE_KEY_FOR_FOREX_DATA),
                BindingBuilder.bind(requestSanitationQueue)
                        .to(topicExchange)
                        .with(KeywordsAndConstants.RABBIT_MQ_ROUTE_KEY_FOR_REQUEST_SANITATION),
                BindingBuilder.bind(processEmail)
                        .to(topicExchange)
                        .with(KeywordsAndConstants.RABBIT_MQ_ROUTE_KEY_FOR_LOGIN_OR_SIGNUP_OTP)

        );
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername(KeywordsAndConstants.RABBIT_MQ_USER_NAME);
        connectionFactory.setPassword(KeywordsAndConstants.RABBIT_MQ_PASSWORD);
        connectionFactory.setHost(KeywordsAndConstants.RABBIT_MQ_HOST);
        connectionFactory.setPort(KeywordsAndConstants.RABBIT_MQ_PORT);
        return connectionFactory;
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

}

