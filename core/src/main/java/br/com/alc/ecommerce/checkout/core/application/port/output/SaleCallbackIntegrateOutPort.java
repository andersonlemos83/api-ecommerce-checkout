package br.com.alc.ecommerce.checkout.core.application.port.output;

import br.com.alc.ecommerce.checkout.core.domain.model.callback.SaleCallbackRequest;

public interface SaleCallbackIntegrateOutPort {

    void execute(SaleCallbackRequest saleCallbackRequest);

}