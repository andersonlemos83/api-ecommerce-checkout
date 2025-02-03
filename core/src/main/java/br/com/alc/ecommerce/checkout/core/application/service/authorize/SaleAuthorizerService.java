package br.com.alc.ecommerce.checkout.core.application.service.authorize;

import br.com.alc.ecommerce.checkout.core.domain.model.authorize.AuthorizeSaleResponse;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleRequest;

public interface SaleAuthorizerService {

    AuthorizeSaleResponse execute(SaleRequest saleRequest);

}