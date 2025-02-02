package br.com.alc.ecommerce.checkout.core.application.port.input;

import br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleRequest;

public interface SaleProcessorUseCase {

    void execute(SaleRequest saleRequest);

}