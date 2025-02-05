package br.com.alc.ecommerce.checkout.infrastructure.adapter.output;

import br.com.alc.ecommerce.checkout.core.domain.sale.SaleRequest;
import br.com.alc.ecommerce.checkout.core.port.output.SaleIntegratorOutPort;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.SaleRequestDto;
import br.com.alc.ecommerce.checkout.infrastructure.messaging.producer.MessagingProducer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SaleIntegratorOutPortImpl implements SaleIntegratorOutPort {

    private final MessagingProducer messagingProducer;
    private final ModelMapper modelMapper;

    private final String saleExchange;
    private final String authorizeSaleQueue;

    public SaleIntegratorOutPortImpl(MessagingProducer messagingProducer,
                                     ModelMapper modelMapper,
                                     @Value("${spring.rabbitmq.sale-exchange}") String saleExchange,
                                     @Value("${spring.rabbitmq.authorize-sale-queue}") String authorizeSaleQueue) {
        this.messagingProducer = messagingProducer;
        this.modelMapper = modelMapper;
        this.saleExchange = saleExchange;
        this.authorizeSaleQueue = authorizeSaleQueue;
    }

    @Override
    public void execute(SaleRequest saleRequest) {
        SaleRequestDto saleRequestDto = modelMapper.map(saleRequest, SaleRequestDto.class);
        messagingProducer.publish(saleExchange, authorizeSaleQueue, saleRequestDto);
    }
}