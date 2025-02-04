package br.com.alc.ecommerce.checkout.infrastructure.adapter.output;

import br.com.alc.ecommerce.checkout.core.application.port.output.SaleIntegratorOutPort;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleRequest;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.SaleRequestDto;
import br.com.alc.ecommerce.checkout.infrastructure.messaging.producer.MessagingProducer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SaleIntegratorOutPortImpl implements SaleIntegratorOutPort {

    private final MessagingProducer messagingProducer;

    private final String saleExchange;
    private final String authorizeSaleQueue;

    public SaleIntegratorOutPortImpl(MessagingProducer messagingProducer,
                                     @Value("${spring.rabbitmq.sale-exchange}") String saleExchange,
                                     @Value("${spring.rabbitmq.authorize-sale-queue}") String authorizeSaleQueue) {
        this.messagingProducer = messagingProducer;
        this.saleExchange = saleExchange;
        this.authorizeSaleQueue = authorizeSaleQueue;
    }

    @Override
    public void execute(SaleRequest saleRequest) {
        SaleRequestDto saleRequestDto = buildSaleRequestDto(saleRequest);
        messagingProducer.publish(saleExchange, authorizeSaleQueue, saleRequestDto);
    }

    private SaleRequestDto buildSaleRequestDto(SaleRequest saleRequest) {
        return new ModelMapper().map(saleRequest, SaleRequestDto.class);
    }
}