package br.com.alc.ecommerce.checkout.infrastructure.configuration;

import br.com.alc.ecommerce.checkout.core.util.ObjectMapperUtil;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Value("${spring.rabbitmq.sale-exchange}")
    private String saleExchange;

    @Value("${spring.rabbitmq.authorize-sale-queue}")
    private String authorizeSaleQueueName;

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter(ObjectMapperUtil.getInstance().copy().findAndRegisterModules());
    }

    @Bean
    public TopicExchange saleExchange() {
        return ExchangeBuilder.topicExchange(saleExchange).build();
    }

    @Bean
    public Queue authorizeSaleQueue() {
        return QueueBuilder.durable(authorizeSaleQueueName).build();
    }

    @Bean
    public Binding autorizarVendaQueueBinding(TopicExchange saleExchange, Queue authorizeSaleQueue) {
        return BindingBuilder.bind(authorizeSaleQueue).to(saleExchange).with(authorizeSaleQueueName);
    }
}