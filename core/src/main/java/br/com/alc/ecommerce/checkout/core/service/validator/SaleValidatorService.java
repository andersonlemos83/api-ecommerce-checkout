package br.com.alc.ecommerce.checkout.core.service.validator;

import br.com.alc.ecommerce.checkout.core.domain.sale.SaleRequest;

public interface SaleValidatorService {

    void execute(SaleRequest saleRequest);

}