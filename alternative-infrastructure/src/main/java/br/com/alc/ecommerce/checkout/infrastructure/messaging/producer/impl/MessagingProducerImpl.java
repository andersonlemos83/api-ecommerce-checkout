package br.com.alc.ecommerce.checkout.infrastructure.messaging.producer.impl;

import br.com.alc.ecommerce.checkout.infrastructure.messaging.producer.MessagingProducer;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static br.com.alc.ecommerce.checkout.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@Component
@AllArgsConstructor
public class MessagingProducerImpl implements MessagingProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Async
    @Override
    public <T> void publish(String topic, T request) {
        try {
            log.info("---> Sending to topic {} - {}", topic, generateJson(request));
            kafkaTemplate.send(topic, request);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
        }
    }
}