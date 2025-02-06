package br.com.alc.ecommerce.checkout.core.port.output;

import br.com.alc.ecommerce.checkout.core.domain.sale.SaleRequest;

public interface SaleIntegratorOutPort {

    void execute(SaleRequest saleRequest);

}