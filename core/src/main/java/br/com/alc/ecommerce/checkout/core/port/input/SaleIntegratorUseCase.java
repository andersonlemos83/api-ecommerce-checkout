package br.com.alc.ecommerce.checkout.core.port.input;

import br.com.alc.ecommerce.checkout.core.domain.sale.SaleRequest;
import br.com.alc.ecommerce.checkout.core.domain.sale.SaleResponse;

public interface SaleIntegratorUseCase {

    SaleResponse execute(SaleRequest saleRequest);

}