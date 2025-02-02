package br.com.alc.ecommerce.checkout.core.application.service.validator;

import br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleRequest;

public interface SaleValidatorService {

    void execute(SaleRequest saleRequest);

}