package br.com.alc.ecommerce.checkout.core.port.output;

import br.com.alc.ecommerce.checkout.core.domain.authorize.AuthorizeSaleRequest;
import br.com.alc.ecommerce.checkout.core.domain.authorize.AuthorizeSaleResponse;

public interface SaleAuthorizerOutPort {

    AuthorizeSaleResponse execute(AuthorizeSaleRequest authorizeSaleRequest);

}