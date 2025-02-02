package br.com.alc.ecommerce.checkout.infrastructure.adapter.output;

import br.com.alc.ecommerce.checkout.core.application.port.output.MostRecentSaleOrderFinderOutPort;
import br.com.alc.ecommerce.checkout.core.domain.model.order.SaleOrder;
import br.com.alc.ecommerce.checkout.infrastructure.persistence.entity.SaleOrderEntity;
import br.com.alc.ecommerce.checkout.infrastructure.persistence.repository.SaleOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class MostRecentSaleOrderFinderOutPortImpl implements MostRecentSaleOrderFinderOutPort {

    private final SaleOrderRepository saleOrderRepository;

    @Override
    public Optional<SaleOrder> execute(String numberOrder) {
        Optional<SaleOrderEntity> optional = saleOrderRepository.findFirstByNumberOrderOrderByUpdatedDateDesc(numberOrder);
        return optional.map(this::buildSaleOrder);
    }

    private SaleOrder buildSaleOrder(SaleOrderEntity saleOrderEntity) {
        SaleOrder saleOrder = SaleOrder.builder().build();
        BeanUtils.copyProperties(saleOrderEntity, saleOrder);
        return saleOrder;
    }
}