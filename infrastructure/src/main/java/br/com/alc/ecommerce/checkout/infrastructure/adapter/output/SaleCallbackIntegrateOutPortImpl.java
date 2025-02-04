package br.com.alc.ecommerce.checkout.infrastructure.adapter.output;

import br.com.alc.ecommerce.checkout.core.application.port.output.SaleCallbackIntegrateOutPort;
import br.com.alc.ecommerce.checkout.core.domain.model.callback.SaleCallbackRequest;
import br.com.alc.ecommerce.checkout.infrastructure.dto.callback.SaleCallbackRequestDto;
import br.com.alc.ecommerce.checkout.infrastructure.messaging.producer.MessagingProducer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SaleCallbackIntegrateOutPortImpl implements SaleCallbackIntegrateOutPort {

    private final MessagingProducer messagingProducer;

    private final String saleCallbackQueue;

    public SaleCallbackIntegrateOutPortImpl(MessagingProducer messagingProducer,
                                            @Value("${spring.rabbitmq.sale-callback-queue}") String saleCallbackQueue) {
        this.messagingProducer = messagingProducer;
        this.saleCallbackQueue = saleCallbackQueue;
    }

    @Override
    public void execute(SaleCallbackRequest saleCallbackRequest) {
        SaleCallbackRequestDto saleCallbackRequestDto = buildSaleCallbackRequestDto(saleCallbackRequest);
        messagingProducer.publish(saleCallbackQueue, saleCallbackRequestDto);
    }

    private SaleCallbackRequestDto buildSaleCallbackRequestDto(SaleCallbackRequest saleCallbackRequest) {
        return new ModelMapper().map(saleCallbackRequest, SaleCallbackRequestDto.class);
    }
}