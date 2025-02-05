package br.com.alc.ecommerce.checkout.infrastructure.adapter.output;

import br.com.alc.ecommerce.checkout.core.domain.order.SaleOrder;
import br.com.alc.ecommerce.checkout.core.port.output.SaleOrderInserterOutPort;
import br.com.alc.ecommerce.checkout.infrastructure.persistence.entity.SaleOrderEntity;
import br.com.alc.ecommerce.checkout.infrastructure.persistence.repository.SaleOrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static br.com.alc.ecommerce.checkout.infrastructure.util.ObjectMapperUtil.generateJson;

@Log4j2
@Component
@AllArgsConstructor
public class SaleOrderInserterOutPortImpl implements SaleOrderInserterOutPort {

    private final SaleOrderRepository saleOrderRepository;
    private final ModelMapper modelMapper;

    @Override
    public void execute(SaleOrder saleOrder) {
        log.debug("---> SaleOrderInserterOutPortImpl: {}", generateJson(saleOrder));
        SaleOrderEntity saleOrderEntity = modelMapper.map(saleOrder, SaleOrderEntity.class);
        Optional<SaleOrderEntity> optional = saleOrderRepository.findFirstByOrderNumberAndStatusInProcessing(saleOrder.getOrderNumber());
        optional.ifPresent(entity -> saleOrderEntity.setId(entity.getId()));
        saleOrderRepository.saveAndFlush(saleOrderEntity);
    }
}