package br.com.alc.ecommerce.checkout.core.service.service.authorize.factory.impl;

import br.com.alc.ecommerce.checkout.core.domain.authorize.AuthorizePayment;
import br.com.alc.ecommerce.checkout.core.domain.sale.Payment;
import br.com.alc.ecommerce.checkout.core.service.authorize.factory.AuthorizePaymentFactory;
import br.com.alc.ecommerce.checkout.core.service.authorize.factory.impl.AuthorizePaymentFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static br.com.alc.ecommerce.checkout.core.domain.sale.PaymentMethod.*;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(MockitoExtension.class)
public class AuthorizePaymentFactoryImplTest {

    private AuthorizePaymentFactory authorizePaymentFactory;

    @BeforeEach
    void setUp() {
        authorizePaymentFactory = new AuthorizePaymentFactoryImpl();
    }

    @Test
    void givenAnCreditPaymentMethodWhenExecutingTheCreateAuthorizePaymentMethodThenReturnTheExpectedCreditAuthorizePayment() {
        Payment payment = Payment.builder()
                .paymentMethod(CREDIT)
                .paymentDate(getLocalDateTime())
                .authorizationCode("270606")
                .cardNumber("3556777163651312")
                .pixKey(null)
                .value(BigDecimal.valueOf(105.04))
                .build();

        AuthorizePayment result = authorizePaymentFactory.createAuthorizePayment(payment, 1);

        assertNotNull(result);
        assertEquals(1, result.getSequence());
        assertEquals(CREDIT.name(), result.getType());
        assertEquals("2025-01-30T13:45:01", result.getDate());
        assertEquals("270606", result.getAuthorizationCode());
        assertEquals("3556777163651312", result.getCardNumber());
        assertNull(result.getPixKey());
        assertEquals(BigDecimal.valueOf(105.04), result.getValue());
    }

    @Test
    void givenAnDebitPaymentMethodWhenExecutingTheCreateAuthorizePaymentMethodThenReturnTheExpectedDebitAuthorizePayment() {
        Payment payment = Payment.builder()
                .paymentMethod(DEBIT)
                .paymentDate(getLocalDateTime())
                .authorizationCode("270606")
                .cardNumber("3556777163651312")
                .pixKey(null)
                .value(BigDecimal.valueOf(105.04))
                .build();

        AuthorizePayment result = authorizePaymentFactory.createAuthorizePayment(payment, 1);

        assertNotNull(result);
        assertEquals(1, result.getSequence());
        assertEquals(DEBIT.name(), result.getType());
        assertEquals("2025-01-30T13:45:01", result.getDate());
        assertEquals("270606", result.getAuthorizationCode());
        assertEquals("3556777163651312", result.getCardNumber());
        assertNull(result.getPixKey());
        assertEquals(BigDecimal.valueOf(105.04), result.getValue());
    }

    @Test
    void givenAnCashPaymentMethodWhenExecutingTheCreateAuthorizePaymentMethodThenReturnTheExpectedCashAuthorizePayment() {
        Payment payment = Payment.builder()
                .paymentMethod(CASH)
                .paymentDate(getLocalDateTime())
                .authorizationCode("270606")
                .cardNumber(null)
                .pixKey(null)
                .value(BigDecimal.valueOf(105.04))
                .build();

        AuthorizePayment result = authorizePaymentFactory.createAuthorizePayment(payment, 1);

        assertNotNull(result);
        assertEquals(1, result.getSequence());
        assertEquals(CASH.name(), result.getType());
        assertEquals("2025-01-30T13:45:01", result.getDate());
        assertEquals("270606", result.getAuthorizationCode());
        assertNull(result.getCardNumber());
        assertNull(result.getPixKey());
        assertEquals(BigDecimal.valueOf(105.04), result.getValue());
    }

    @Test
    void givenAnPixPaymentMethodWhenExecutingTheCreateAuthorizePaymentMethodThenReturnTheExpectedPixAuthorizePayment() {
        Payment payment = Payment.builder()
                .paymentMethod(PIX)
                .paymentDate(getLocalDateTime())
                .authorizationCode("270606")
                .cardNumber(null)
                .pixKey("82992344475")
                .value(BigDecimal.valueOf(105.04))
                .build();

        AuthorizePayment result = authorizePaymentFactory.createAuthorizePayment(payment, 1);

        assertNotNull(result);
        assertEquals(1, result.getSequence());
        assertEquals(PIX.name(), result.getType());
        assertEquals("2025-01-30T13:45:01", result.getDate());
        assertEquals("270606", result.getAuthorizationCode());
        assertNull(result.getCardNumber());
        assertEquals("82992344475", result.getPixKey());
        assertEquals(BigDecimal.valueOf(105.04), result.getValue());
    }

    @Test
    void givenAnUnsupportedPaymentMethodWhenExecutingTheCreateAuthorizePaymentMethodThenThrowsAnIllegalArgumentException() {
        Payment payment = Payment.builder()
                .paymentMethod(null)
                .paymentDate(getLocalDateTime())
                .authorizationCode("270606")
                .cardNumber("3556777163651312")
                .pixKey(null)
                .value(BigDecimal.valueOf(105.04))
                .build();

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                authorizePaymentFactory.createAuthorizePayment(payment, 1));

        assertEquals("Método de pagamento não suportado: null", thrown.getMessage());
    }

    private LocalDateTime getLocalDateTime() {
        return LocalDateTime.now()
                .withYear(2025)
                .withMonth(01)
                .withDayOfMonth(30)
                .withHour(13)
                .withMinute(45)
                .withSecond(01)
                .withNano(0);
    }
}