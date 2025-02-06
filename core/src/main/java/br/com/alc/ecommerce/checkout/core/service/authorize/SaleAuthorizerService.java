package br.com.alc.ecommerce.checkout.core.service.authorize;

import br.com.alc.ecommerce.checkout.core.domain.authorize.AuthorizeSaleResponse;
import br.com.alc.ecommerce.checkout.core.domain.sale.SaleRequest;

public interface SaleAuthorizerService {

    AuthorizeSaleResponse execute(SaleRequest saleRequest);

}