package br.com.alc.ecommerce.checkout.core.service.service.authorize.impl;

import br.com.alc.ecommerce.checkout.core.domain.authorize.AuthorizePayment;
import br.com.alc.ecommerce.checkout.core.domain.authorize.AuthorizeSaleRequest;
import br.com.alc.ecommerce.checkout.core.domain.authorize.AuthorizeSaleResponse;
import br.com.alc.ecommerce.checkout.core.domain.sale.*;
import br.com.alc.ecommerce.checkout.core.domain.tax.TaxResponse;
import br.com.alc.ecommerce.checkout.core.port.output.SaleAuthorizerOutPort;
import br.com.alc.ecommerce.checkout.core.port.output.TaxFinderOutPort;
import br.com.alc.ecommerce.checkout.core.service.authorize.factory.AuthorizePaymentFactory;
import br.com.alc.ecommerce.checkout.core.service.authorize.impl.SaleAuthorizerServiceImpl;
import br.com.alc.ecommerce.checkout.core.service.watch.WatchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static br.com.alc.ecommerce.checkout.core.domain.sale.PaymentMethod.CREDIT;
import static br.com.alc.ecommerce.checkout.core.domain.sale.PaymentMethod.PIX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class SaleAuthorizerServiceImplTest {

    private static final String EXPECTED_REQUEST = "AuthorizeSaleRequest(companyCode=001, storeCode=100, terminalNumber=105, nsu=98765432220250130134501, totalValue=105.04, date=2025-01-30T13:45:01, customer=AuthorizeCustomer(name=Martin Kauê Lopes, document=60778532402, documentType=CPF, phone=82992344475, email=martin_lopes@rafaelmarin.net, address=AuthorizeAddress(address=Rua Projetada 913, addressNumber=622, addressComplement=Apt 202, neighborhood=Antares, city=Maceió, state=AL, country=Brasil, zipCode=57048434)), items=[AuthorizeItem(sequence=1, sku=100231933559, amount=1, value=7.09, ivaCbsValue=1.06, ivaIbsValue=0.7), AuthorizeItem(sequence=2, sku=874631202305, amount=2, value=17.68, ivaCbsValue=2.65, ivaIbsValue=1.76), AuthorizeItem(sequence=3, sku=392084657819, amount=3, value=19.18, ivaCbsValue=2.87, ivaIbsValue=1.91)], payments=[AuthorizePayment(sequence=1, type=CREDIT, date=2025-01-30'T'13:45:01, authorizationCode=270606, cardNumber=3556777163651312, pixKey=null, value=100.0), AuthorizePayment(sequence=2, type=PIX, date=2025-01-30'T'13:45:01, authorizationCode=270607, cardNumber=null, pixKey=82992344475, value=5.04)])";

    @InjectMocks
    private SaleAuthorizerServiceImpl saleAuthorizerService;

    @Mock
    private SaleAuthorizerOutPort saleAuthorizerOutPortMock;

    @Mock
    private TaxFinderOutPort taxFinderOutPortMock;

    @Mock
    private AuthorizePaymentFactory authorizePaymentFactoryMock;

    @Mock
    private WatchService watchServiceMock;

    @Test
    void givenAnValidSaleRequestWhenExecutingTheSaleAuthorizerServiceThenShouldCallSaleAuthorizerOutPortAndReturnTheExpectedAuthorizeSaleResponse() {
        SaleRequest saleRequest = buildSaleRequest();
        AuthorizeSaleResponse expectedResponse = AuthorizeSaleResponse.builder().build();

        when(saleAuthorizerOutPortMock.execute(any(AuthorizeSaleRequest.class))).thenReturn(expectedResponse);
        when(taxFinderOutPortMock.execute(eq(saleRequest.getItems().get(0).getCode()))).thenReturn(buildTaxResponse100231933559());
        when(taxFinderOutPortMock.execute(eq(saleRequest.getItems().get(1).getCode()))).thenReturn(buildTaxResponse874631202305());
        when(taxFinderOutPortMock.execute(eq(saleRequest.getItems().get(2).getCode()))).thenReturn(buildTaxResponse392084657819());
        when(authorizePaymentFactoryMock.createAuthorizePayment(eq(saleRequest.getPayments().get(0)), eq(1))).thenReturn(buildAuthorizePaymentCredit());
        when(authorizePaymentFactoryMock.createAuthorizePayment(eq(saleRequest.getPayments().get(1)), eq(2))).thenReturn(buildAuthorizePaymentPix());
        when(watchServiceMock.nowLocalDateTime()).thenReturn(getLocalDateTime());

        AuthorizeSaleResponse returnedResponse = saleAuthorizerService.execute(saleRequest);

        ArgumentCaptor<AuthorizeSaleRequest> captor = ArgumentCaptor.forClass(AuthorizeSaleRequest.class);
        verify(saleAuthorizerOutPortMock, times(1)).execute(captor.capture());

        assertEquals(EXPECTED_REQUEST, captor.getValue().toString());
        assertEquals(expectedResponse, returnedResponse);
    }

    private SaleRequest buildSaleRequest() {
        Customer customer = buildCustomer();
        List<ShoppingCartItem> buildShoppingCartItemList = buildShoppingCartItemList();
        List<Payment> buildPaymentList = buildPaymentList();
        return SaleRequest.builder()
                .channelCode("APP")
                .companyCode("001")
                .storeCode("100")
                .pos(105)
                .numberOrder("987654322")
                .totalValue(BigDecimal.valueOf(105.04))
                .freightValue(BigDecimal.valueOf(5.05))
                .customer(customer)
                .items(buildShoppingCartItemList)
                .payments(buildPaymentList)
                .build();
    }

    private Customer buildCustomer() {
        return Customer.builder()
                .name("Martin Kauê Lopes")
                .document("60778532402")
                .documentType(DocumentType.CPF)
                .address("Rua Projetada 913")
                .addressNumber("622")
                .addressComplement("Apt 202")
                .neighborhood("Antares")
                .city("Maceió")
                .state("AL")
                .country("Brasil")
                .zipCode("57048434")
                .phone("82992344475")
                .email("martin_lopes@rafaelmarin.net")
                .build();
    }

    private List<ShoppingCartItem> buildShoppingCartItemList() {
        ShoppingCartItem shoppingCartItem100231933559 = buildShoppingCartItem100231933559();
        ShoppingCartItem shoppingCartItem874631202305 = buildShoppingCartItem874631202305();
        ShoppingCartItem shoppingCartItem392084657819 = buildShoppingCartItem392084657819();
        return Arrays.asList(shoppingCartItem100231933559, shoppingCartItem874631202305, shoppingCartItem392084657819);
    }

    private ShoppingCartItem buildShoppingCartItem100231933559() {
        return ShoppingCartItem.builder()
                .code(BigInteger.valueOf(100231933559L))
                .quantity(1)
                .value(BigDecimal.valueOf(7.09))
                .build();
    }

    private ShoppingCartItem buildShoppingCartItem874631202305() {
        return ShoppingCartItem.builder()
                .code(BigInteger.valueOf(874631202305L))
                .quantity(2)
                .value(BigDecimal.valueOf(17.68))
                .build();
    }

    private ShoppingCartItem buildShoppingCartItem392084657819() {
        return ShoppingCartItem.builder()
                .code(BigInteger.valueOf(392084657819L))
                .quantity(3)
                .value(BigDecimal.valueOf(19.18))
                .build();
    }

    private List<Payment> buildPaymentList() {
        Payment paymentCredit = buildPaymentCredit();
        Payment paymentPix = buildPaymentPix();
        return Arrays.asList(paymentCredit, paymentPix);
    }

    private Payment buildPaymentCredit() {
        return Payment.builder()
                .paymentMethod(CREDIT)
                .paymentDate(getLocalDateTime())
                .authorizationCode("270606")
                .cardNumber("3556777163651312")
                .pixKey(null)
                .value(BigDecimal.valueOf(100.00))
                .build();
    }

    private Payment buildPaymentPix() {
        return Payment.builder()
                .paymentMethod(PIX)
                .paymentDate(getLocalDateTime())
                .authorizationCode("270607")
                .cardNumber(null)
                .pixKey("82992344475")
                .value(BigDecimal.valueOf(5.04))
                .build();
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

    private TaxResponse buildTaxResponse100231933559() {
        return TaxResponse.builder()
                .code(BigInteger.valueOf(100231933559L))
                .ivaCbsValue(BigDecimal.valueOf(1.06))
                .ivaIbsValue(BigDecimal.valueOf(0.70))
                .build();
    }

    private TaxResponse buildTaxResponse874631202305() {
        return TaxResponse.builder()
                .code(BigInteger.valueOf(874631202305L))
                .ivaCbsValue(BigDecimal.valueOf(2.65))
                .ivaIbsValue(BigDecimal.valueOf(1.76))
                .build();
    }

    private TaxResponse buildTaxResponse392084657819() {
        return TaxResponse.builder()
                .code(BigInteger.valueOf(392084657819L))
                .ivaCbsValue(BigDecimal.valueOf(2.87))
                .ivaIbsValue(BigDecimal.valueOf(1.91))
                .build();
    }

    private AuthorizePayment buildAuthorizePaymentCredit() {
        return AuthorizePayment.builder()
                .sequence(1)
                .type("CREDIT")
                .date("2025-01-30'T'13:45:01")
                .authorizationCode("270606")
                .cardNumber("3556777163651312")
                .pixKey(null)
                .value(BigDecimal.valueOf(100.00))
                .build();
    }

    private AuthorizePayment buildAuthorizePaymentPix() {
        return AuthorizePayment.builder()
                .sequence(2)
                .type("PIX")
                .date("2025-01-30'T'13:45:01")
                .authorizationCode("270607")
                .cardNumber(null)
                .pixKey("82992344475")
                .value(BigDecimal.valueOf(5.04))
                .build();
    }
}