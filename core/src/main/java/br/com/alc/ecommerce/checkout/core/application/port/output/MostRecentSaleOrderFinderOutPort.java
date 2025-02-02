package br.com.alc.ecommerce.checkout.core.application.port.output;

import br.com.alc.ecommerce.checkout.core.domain.model.order.SaleOrder;

import java.util.Optional;

public interface MostRecentSaleOrderFinderOutPort {

    Optional<SaleOrder> execute(String numberOrder);

}