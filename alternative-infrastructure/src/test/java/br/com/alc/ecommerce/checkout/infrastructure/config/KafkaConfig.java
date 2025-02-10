package br.com.alc.ecommerce.checkout.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Value(value = "${spring.kafka.sale-callback-topic}")
    private String saleCallbackTopic;

    @Bean
    public NewTopic saleCallbackTopic() {
        return new NewTopic(saleCallbackTopic, 2, (short) 1);
    }
}