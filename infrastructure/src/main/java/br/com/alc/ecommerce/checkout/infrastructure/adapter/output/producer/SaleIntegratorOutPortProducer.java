package br.com.alc.ecommerce.checkout.infrastructure.adapter.output.producer;

import br.com.alc.ecommerce.checkout.core.application.port.output.SaleIntegratorOutPort;
import br.com.alc.ecommerce.checkout.core.domain.model.SaleRequest;
import br.com.alc.ecommerce.checkout.infrastructure.messaging.MessagingProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SaleIntegratorOutPortProducer implements SaleIntegratorOutPort {

    private final MessagingProducer messagingProducer;

    private final String saleExchange;
    private final String authorizeSaleQueue;

    public SaleIntegratorOutPortProducer(MessagingProducer messagingProducer,
                                         @Value("${spring.rabbitmq.sale-exchange}") String saleExchange,
                                         @Value("${spring.rabbitmq.authorize-sale-queue}") String authorizeSaleQueue) {
        this.messagingProducer = messagingProducer;
        this.saleExchange = saleExchange;
        this.authorizeSaleQueue = authorizeSaleQueue;
    }

    @Override
    public void execute(SaleRequest saleRequest) {
        messagingProducer.publish(saleExchange, authorizeSaleQueue, saleRequest);
    }
}