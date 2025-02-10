package br.com.alc.ecommerce.checkout.infrastructure.messaging.producer;

public interface MessagingProducer {

    <T> void publish(String topic, T request);

}