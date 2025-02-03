package br.com.alc.ecommerce.checkout.core.application.service.authorize.factory;

import br.com.alc.ecommerce.checkout.core.domain.model.authorize.AuthorizePayment;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.Payment;

public interface AuthorizePaymentFactory {

    AuthorizePayment createAuthorizePayment(Payment payment, int sequence);

}