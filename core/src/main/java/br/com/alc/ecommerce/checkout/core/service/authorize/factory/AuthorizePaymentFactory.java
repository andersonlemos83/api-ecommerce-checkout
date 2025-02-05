package br.com.alc.ecommerce.checkout.core.service.authorize.factory;

import br.com.alc.ecommerce.checkout.core.domain.authorize.AuthorizePayment;
import br.com.alc.ecommerce.checkout.core.domain.sale.Payment;

public interface AuthorizePaymentFactory {

    AuthorizePayment createAuthorizePayment(Payment payment, int sequence);

}