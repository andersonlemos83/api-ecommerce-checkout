package br.com.alc.ecommerce.checkout.infrastructure.adapter.output;

import br.com.alc.ecommerce.checkout.core.application.port.output.SaleOrderInserterOutPort;
import br.com.alc.ecommerce.checkout.core.domain.model.order.SaleOrder;
import br.com.alc.ecommerce.checkout.infrastructure.persistence.entity.SaleOrderEntity;
import br.com.alc.ecommerce.checkout.infrastructure.persistence.repository.SaleOrderRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class SaleOrderInserterOutPortImpl implements SaleOrderInserterOutPort {

    private final SaleOrderRepository saleOrderRepository;

    @Override
    public void execute(SaleOrder saleOrder) {
        SaleOrderEntity saleOrderEntity = buildSaleOrderEntity(saleOrder);
        Optional<SaleOrderEntity> optional = saleOrderRepository.findFirstByNumberOrderAndStatusInProcessing(saleOrder.getNumberOrder());
        optional.ifPresent(entity -> saleOrderEntity.setId(entity.getId()));
        saleOrderRepository.saveAndFlush(saleOrderEntity);
    }

    private SaleOrderEntity buildSaleOrderEntity(SaleOrder saleOrder) {
        return new ModelMapper().map(saleOrder, SaleOrderEntity.class);
    }
}