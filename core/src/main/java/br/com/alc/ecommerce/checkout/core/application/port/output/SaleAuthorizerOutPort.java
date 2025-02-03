package br.com.alc.ecommerce.checkout.core.application.port.output;

import br.com.alc.ecommerce.checkout.core.domain.model.authorize.AuthorizeSaleRequest;
import br.com.alc.ecommerce.checkout.core.domain.model.authorize.AuthorizeSaleResponse;

public interface SaleAuthorizerOutPort {

    AuthorizeSaleResponse execute(AuthorizeSaleRequest authorizeSaleRequest);

}