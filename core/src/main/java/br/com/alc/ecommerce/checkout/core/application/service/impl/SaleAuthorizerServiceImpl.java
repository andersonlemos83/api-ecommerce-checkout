package br.com.alc.ecommerce.checkout.core.application.service.impl;

import br.com.alc.ecommerce.checkout.core.application.service.SaleAuthorizerService;
import br.com.alc.ecommerce.checkout.core.domain.model.SaleAuthorizeResponse;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SaleAuthorizerServiceImpl implements SaleAuthorizerService {

    @Override
    public SaleAuthorizeResponse execute(SaleRequest saleRequest) {
        return null;
    }
}