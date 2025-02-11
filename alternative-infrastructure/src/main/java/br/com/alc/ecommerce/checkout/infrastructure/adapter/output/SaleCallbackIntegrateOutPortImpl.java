package br.com.alc.ecommerce.checkout.infrastructure.adapter.output;

import br.com.alc.ecommerce.checkout.core.domain.callback.SaleCallbackRequest;
import br.com.alc.ecommerce.checkout.core.port.output.SaleCallbackIntegrateOutPort;
import br.com.alc.ecommerce.checkout.infrastructure.dto.callback.SaleCallbackRequestDto;
import br.com.alc.ecommerce.checkout.infrastructure.messaging.producer.MessagingProducer;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static br.com.alc.ecommerce.checkout.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@Component
public class SaleCallbackIntegrateOutPortImpl implements SaleCallbackIntegrateOutPort {

    private final MessagingProducer messagingProducer;
    private final ModelMapper modelMapper;

    private final String saleCallbackTopic;

    public SaleCallbackIntegrateOutPortImpl(MessagingProducer messagingProducer,
                                            ModelMapper modelMapper,
                                            @Value("${spring.kafka.sale-callback-topic}") String saleCallbackTopic) {
        this.messagingProducer = messagingProducer;
        this.modelMapper = modelMapper;
        this.saleCallbackTopic = saleCallbackTopic;
    }

    @Override
    public void execute(SaleCallbackRequest saleCallbackRequest) {
        log.debug("Incoming into SaleCallbackIntegrateOutPortImpl: {}", generateJson(saleCallbackRequest));
        SaleCallbackRequestDto saleCallbackRequestDto = modelMapper.map(saleCallbackRequest, SaleCallbackRequestDto.class);
        messagingProducer.publish(saleCallbackTopic, saleCallbackRequestDto);
    }
}