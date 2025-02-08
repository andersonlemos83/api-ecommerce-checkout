package br.com.alc.ecommerce.checkout.infrastructure.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.sale-callback-queue}")
    private String saleCallbackQueueName;

    @Primary
    @Bean(value = "rabbitAdmin")
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        configureQueues(rabbitAdmin);
        rabbitAdmin.initialize();
        return rabbitAdmin;
    }

    private void configureQueues(RabbitAdmin rabbitAdmin) {
        Queue saleCallbackQueue = new Queue(saleCallbackQueueName, true);
        rabbitAdmin.declareQueue(saleCallbackQueue);
    }
}