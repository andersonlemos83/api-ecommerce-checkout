package br.com.alc.ecommerce.checkout.infrastructure.adapter.output;

import br.com.alc.ecommerce.checkout.core.domain.order.SaleOrder;
import br.com.alc.ecommerce.checkout.core.port.output.MostRecentSaleOrderFinderOutPort;
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
public class MostRecentSaleOrderFinderOutPortImpl implements MostRecentSaleOrderFinderOutPort {

    private final SaleOrderRepository saleOrderRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<SaleOrder> execute(String orderNumber) {
        log.debug("---> MostRecentSaleOrderFinderOutPortImpl: {}", generateJson(orderNumber));
        Optional<SaleOrderEntity> optional = saleOrderRepository.findFirstByOrderNumberOrderByUpdatedDateDesc(orderNumber);
        Optional<SaleOrder> saleOrderOptional = optional.map(this::buildSaleOrder);
        log.debug("<--- MostRecentSaleOrderFinderOutPortImpl: {}", generateJson(saleOrderOptional));
        return saleOrderOptional;
    }

    private SaleOrder buildSaleOrder(SaleOrderEntity saleOrderEntity) {
        return modelMapper.map(saleOrderEntity, SaleOrder.class);
    }
}