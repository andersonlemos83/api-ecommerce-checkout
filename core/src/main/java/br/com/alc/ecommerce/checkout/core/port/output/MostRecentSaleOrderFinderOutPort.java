package br.com.alc.ecommerce.checkout.core.port.output;

import br.com.alc.ecommerce.checkout.core.domain.order.SaleOrder;

import java.util.Optional;

public interface MostRecentSaleOrderFinderOutPort {

    Optional<SaleOrder> execute(String orderNumber);

}