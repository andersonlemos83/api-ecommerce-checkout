package br.com.alc.ecommerce.checkout.infrastructure.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@SuppressWarnings("squid:S2187")
@Configuration
public class RabbitConfigurationTest {

    @Primary
    @Bean(value = "rabbitAdmin")
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        rabbitAdmin.initialize();
        configureExchangeAndQueue(rabbitAdmin);
        return rabbitAdmin;
    }

    private void configureExchangeAndQueue(RabbitAdmin rabbitAdmin) {

    }
}