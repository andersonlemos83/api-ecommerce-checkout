package br.com.alc.ecommerce.checkout.core.port.output;

import br.com.alc.ecommerce.checkout.core.domain.callback.SaleCallbackRequest;

public interface SaleCallbackIntegrateOutPort {

    void execute(SaleCallbackRequest saleCallbackRequest);

}