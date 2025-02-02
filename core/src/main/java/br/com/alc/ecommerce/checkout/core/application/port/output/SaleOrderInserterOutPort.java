package br.com.alc.ecommerce.checkout.core.application.port.output;

import br.com.alc.ecommerce.checkout.core.domain.model.order.SaleOrder;

public interface SaleOrderInserterOutPort {

    void execute(SaleOrder saleOrder);

}