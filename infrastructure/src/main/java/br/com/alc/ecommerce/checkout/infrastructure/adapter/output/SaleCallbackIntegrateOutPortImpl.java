package br.com.alc.ecommerce.checkout.infrastructure.adapter.output;

import br.com.alc.ecommerce.checkout.core.domain.callback.SaleCallbackRequest;
import br.com.alc.ecommerce.checkout.core.port.output.SaleCallbackIntegrateOutPort;
import br.com.alc.ecommerce.checkout.infrastructure.dto.callback.SaleCallbackRequestDto;
import br.com.alc.ecommerce.checkout.infrastructure.messaging.producer.MessagingProducer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SaleCallbackIntegrateOutPortImpl implements SaleCallbackIntegrateOutPort {

    private final MessagingProducer messagingProducer;
    private final ModelMapper modelMapper;

    private final String saleCallbackQueue;

    public SaleCallbackIntegrateOutPortImpl(MessagingProducer messagingProducer,
                                            ModelMapper modelMapper,
                                            @Value("${spring.rabbitmq.sale-callback-queue}") String saleCallbackQueue) {
        this.messagingProducer = messagingProducer;
        this.modelMapper = modelMapper;
        this.saleCallbackQueue = saleCallbackQueue;
    }

    @Override
    public void execute(SaleCallbackRequest saleCallbackRequest) {
        SaleCallbackRequestDto saleCallbackRequestDto = modelMapper.map(saleCallbackRequest, SaleCallbackRequestDto.class);
        messagingProducer.publish(saleCallbackQueue, saleCallbackRequestDto);
    }
}