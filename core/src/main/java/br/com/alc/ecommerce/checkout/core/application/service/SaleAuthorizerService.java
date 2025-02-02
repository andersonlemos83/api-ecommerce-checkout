package br.com.alc.ecommerce.checkout.core.application.service;

import br.com.alc.ecommerce.checkout.core.domain.model.SaleAuthorizeResponse;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleRequest;

public interface SaleAuthorizerService {

    SaleAuthorizeResponse execute(SaleRequest saleRequest);

}