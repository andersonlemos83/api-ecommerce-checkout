package br.com.alc.ecommerce.checkout.infrastructure.adapter.output.stub;

import br.com.alc.ecommerce.checkout.core.domain.order.SaleOrder;
import br.com.alc.ecommerce.checkout.core.port.output.MostRecentSaleOrderFinderOutPort;
import br.com.alc.ecommerce.checkout.infrastructure.adapter.output.MostRecentSaleOrderFinderOutPortImpl;
import br.com.alc.ecommerce.checkout.infrastructure.persistence.repository.SaleOrderRepository;
import org.modelmapper.ModelMapper;

import java.util.Optional;

public class MostRecentSaleOrderFinderOutPortStub extends MostRecentSaleOrderFinderOutPortImpl implements MostRecentSaleOrderFinderOutPort {

    public static final String INVALID_ORDER_NUMBER = "987654331";

    public MostRecentSaleOrderFinderOutPortStub(SaleOrderRepository saleOrderRepository, ModelMapper modelMapper) {
        super(saleOrderRepository, modelMapper);
    }

    @Override
    public Optional<SaleOrder> execute(String orderNumber) {
        if (INVALID_ORDER_NUMBER.equals(orderNumber)) {
            throw new RuntimeException("Erro inesperado ao consultar Sale Order.");
        }
        return super.execute(orderNumber);
    }
}