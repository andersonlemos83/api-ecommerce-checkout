package br.com.alc.ecommerce.checkout.infrastructure.messaging.producer;

public interface MessagingProducer {

    void publish(String exchange, String queue, Object request);

}