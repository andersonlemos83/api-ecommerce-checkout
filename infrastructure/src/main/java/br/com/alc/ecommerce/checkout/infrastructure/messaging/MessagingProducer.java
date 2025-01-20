package br.com.alc.ecommerce.checkout.infrastructure.messaging;

public interface MessagingProducer {

    void publish(String exchange, String queue, Object request);

}