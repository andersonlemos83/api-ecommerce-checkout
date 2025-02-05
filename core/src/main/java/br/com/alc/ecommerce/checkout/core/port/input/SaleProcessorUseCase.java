package br.com.alc.ecommerce.checkout.core.port.input;

import br.com.alc.ecommerce.checkout.core.domain.sale.SaleRequest;

public interface SaleProcessorUseCase {

    void execute(SaleRequest saleRequest);

}