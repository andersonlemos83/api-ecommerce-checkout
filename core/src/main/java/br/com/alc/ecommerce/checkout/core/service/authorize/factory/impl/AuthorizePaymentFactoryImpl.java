package br.com.alc.ecommerce.checkout.core.service.authorize.factory.impl;

import br.com.alc.ecommerce.checkout.core.domain.authorize.AuthorizePayment;
import br.com.alc.ecommerce.checkout.core.domain.sale.Payment;
import br.com.alc.ecommerce.checkout.core.service.authorize.factory.AuthorizePaymentFactory;
import br.com.alc.ecommerce.checkout.core.util.DateUtil;

public class AuthorizePaymentFactoryImpl implements AuthorizePaymentFactory {

    @Override
    public AuthorizePayment createAuthorizePayment(Payment payment, int sequence) {
        if (payment.isCredit() || payment.isDebit()) {
            AuthorizePayment.AuthorizePaymentBuilder authorizePaymentBuilder = buildAuthorizePayment(payment, sequence);
            return authorizePaymentBuilder.cardNumber(payment.getCardNumber()).build();
        }

        if (payment.isCash()) {
            AuthorizePayment.AuthorizePaymentBuilder authorizePaymentBuilder = buildAuthorizePayment(payment, sequence);
            return authorizePaymentBuilder.build();
        }

        if (payment.isPix()) {
            AuthorizePayment.AuthorizePaymentBuilder authorizePaymentBuilder = buildAuthorizePayment(payment, sequence);
            return authorizePaymentBuilder.pixKey(payment.getPixKey()).build();
        }

        throw new IllegalArgumentException("Método de pagamento não suportado: " + payment.getPaymentMethod());
    }

    private AuthorizePayment.AuthorizePaymentBuilder buildAuthorizePayment(Payment payment, int sequence) {
        return AuthorizePayment.builder()
                .sequence(sequence)
                .type(payment.getNamePaymentMethod())
                .date(DateUtil.format(payment.getPaymentDate()))
                .authorizationCode(payment.getAuthorizationCode())
                .value(payment.getValue());
    }
}