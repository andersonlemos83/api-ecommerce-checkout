package br.com.alc.ecommerce.checkout.core.application.port.output;

import br.com.alc.ecommerce.checkout.core.domain.model.SaleRequest;

public interface SaleIntegratorOutPort {

    void execute(SaleRequest saleRequest);

}