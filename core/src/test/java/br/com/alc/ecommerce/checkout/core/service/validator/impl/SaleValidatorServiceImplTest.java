package br.com.alc.ecommerce.checkout.core.service.validator.impl;

import br.com.alc.ecommerce.checkout.core.domain.sale.Payment;
import br.com.alc.ecommerce.checkout.core.domain.sale.SaleRequest;
import br.com.alc.ecommerce.checkout.core.domain.sale.ShoppingCartItem;
import br.com.alc.ecommerce.checkout.core.exception.TotalItemValueMismatchException;
import br.com.alc.ecommerce.checkout.core.exception.TotalPaymentValueMismatchException;
import br.com.alc.ecommerce.checkout.core.service.validator.SaleValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(MockitoExtension.class)
public class SaleValidatorServiceImplTest {

    private SaleValidatorService saleValidatorService;

    @BeforeEach
    void setUp() {
        saleValidatorService = new SaleValidatorServiceImpl();
    }

    @Test
    void givenAnTotalPaymentValueMismatchWhenExecutingTheSaleValidatorServiceThenThrowsAnTotalPaymentValueMismatchException() {
        Payment payment = Payment.builder()
                .value(BigDecimal.valueOf(101))
                .build();

        SaleRequest saleRequest = SaleRequest.builder()
                .totalValue(BigDecimal.valueOf(100))
                .payments(asList(payment))
                .build();

        TotalPaymentValueMismatchException thrown = assertThrows(TotalPaymentValueMismatchException.class, () -> saleValidatorService.execute(saleRequest));
        assertEquals("O valor total dos pagamentos está diferente do total informado.", thrown.getMessage());
    }

    @Test
    void givenAnTotalItemValueMismatchWhenExecutingTheSaleValidatorServiceThenThrowsAnTotalItemValueMismatchException() {
        ShoppingCartItem item = ShoppingCartItem.builder()
                .quantity(1)
                .value(BigDecimal.valueOf(98))
                .build();

        Payment payment = Payment.builder()
                .value(BigDecimal.valueOf(100))
                .build();

        SaleRequest saleRequest = SaleRequest.builder()
                .totalValue(BigDecimal.valueOf(100))
                .freightValue(BigDecimal.valueOf(3))
                .items(asList(item))
                .payments(asList(payment))
                .build();

        TotalItemValueMismatchException thrown = assertThrows(TotalItemValueMismatchException.class, () -> saleValidatorService.execute(saleRequest));
        assertEquals("O valor total dos itens está diferente do total informado.", thrown.getMessage());
    }

    @Test
    void givenAnAllTotalValuesMatchWhenExecutingTheSaleValidatorServiceThenNoExceptionThrown() {
        ShoppingCartItem item = ShoppingCartItem.builder()
                .quantity(1)
                .value(BigDecimal.valueOf(97))
                .build();

        Payment payment = Payment.builder()
                .value(BigDecimal.valueOf(100))
                .build();

        SaleRequest saleRequest = SaleRequest.builder()
                .totalValue(BigDecimal.valueOf(100))
                .freightValue(BigDecimal.valueOf(3))
                .items(asList(item))
                .payments(asList(payment))
                .build();

        assertDoesNotThrow(() -> saleValidatorService.execute(saleRequest));
    }
}