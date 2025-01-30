package br.com.alc.ecommerce.checkout.core.application.port.input;

import br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleRequest;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleResponse;

public interface SaleIntegratorUseCase {

    SaleResponse execute(SaleRequest saleRequest);

}