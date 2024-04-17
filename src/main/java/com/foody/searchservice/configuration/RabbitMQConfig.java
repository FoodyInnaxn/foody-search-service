package com.foody.searchservice.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String RECIPE_QUEUE = "recipe_queue_search";
    public static final String RECIPE_ROUTING_KEY_CREATE = "recipe_routingKey_create_search";
    public static final String RECIPE_ROUTING_KEY_UPDATE = "recipe_routingKey_update_search";
    public static final String FANOUT_SEARCH_QUEUE = "fanout_search_queue";

    @Bean
    public Queue recipe_queue() {
        return new Queue(RECIPE_QUEUE);
    }

    @Bean
    public Queue fanoutSearch() {
        return new Queue(FANOUT_SEARCH_QUEUE);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
