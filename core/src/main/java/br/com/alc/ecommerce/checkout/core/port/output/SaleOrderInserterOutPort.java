package br.com.alc.ecommerce.checkout.core.port.output;

import br.com.alc.ecommerce.checkout.core.domain.order.SaleOrder;

public interface SaleOrderInserterOutPort {

    void execute(SaleOrder saleOrder);

}