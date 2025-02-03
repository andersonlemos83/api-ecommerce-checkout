package br.com.alc.ecommerce.checkout.infrastructure.adapter.output;

import br.com.alc.ecommerce.checkout.core.application.port.output.SaleOrderInserterOutPort;
import br.com.alc.ecommerce.checkout.core.domain.model.order.SaleOrder;
import br.com.alc.ecommerce.checkout.infrastructure.persistence.entity.SaleOrderEntity;
import br.com.alc.ecommerce.checkout.infrastructure.persistence.repository.SaleOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SaleOrderInserterOutPortImpl implements SaleOrderInserterOutPort {

    private final SaleOrderRepository saleOrderRepository;

    @Override
    public void execute(SaleOrder saleOrder) {
        SaleOrderEntity saleOrderEntity = buildSaleOrderEntity(saleOrder);
        saleOrderRepository.saveAndFlush(saleOrderEntity);
    }

    private SaleOrderEntity buildSaleOrderEntity(SaleOrder saleOrder) {
        SaleOrderEntity saleOrderEntity = SaleOrderEntity.builder().build();
        BeanUtils.copyProperties(saleOrder, saleOrderEntity);
        return saleOrderEntity;
    }
}