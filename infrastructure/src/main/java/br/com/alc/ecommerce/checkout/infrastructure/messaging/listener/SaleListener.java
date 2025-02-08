package br.com.alc.ecommerce.checkout.infrastructure.messaging.listener;

import br.com.alc.ecommerce.checkout.infrastructure.adapter.input.SaleProcessorInAdapter;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.SaleRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.alc.ecommerce.checkout.core.util.ObjectMapperUtil.generateJson;
import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;

@Log4j2
@Service
@Transactional
@AllArgsConstructor
public class SaleListener {

    private final SaleProcessorInAdapter saleProcessorInAdapter;

    @RabbitListener(queues = {"${spring.rabbitmq.authorize-sale-queue}"})
    public void authorizeSale(SaleRequestDto saleRequestDto) {
        try {
            log.info("---> Listener of the authorize-sale-queue: {}", generateJson(saleRequestDto));
            saleProcessorInAdapter.execute(saleRequestDto);
            log.info("<--- Listener of the authorize-sale-queue processed successfully");
        } catch (Exception exception) {
            log.error("<--- Error in the listener of the authorize-sale-queue: {}", getMessage(exception), exception);
        }
    }
}