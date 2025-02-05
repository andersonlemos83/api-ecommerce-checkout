package br.com.alc.ecommerce.checkout.core.port.input.impl;

import br.com.alc.ecommerce.checkout.core.domain.authorize.AuthorizeSaleResponse;
import br.com.alc.ecommerce.checkout.core.domain.callback.SaleCallbackRequest;
import br.com.alc.ecommerce.checkout.core.domain.order.SaleOrder;
import br.com.alc.ecommerce.checkout.core.domain.sale.SaleRequest;
import br.com.alc.ecommerce.checkout.core.port.output.MostRecentSaleOrderFinderOutPort;
import br.com.alc.ecommerce.checkout.core.port.output.SaleCallbackIntegrateOutPort;
import br.com.alc.ecommerce.checkout.core.port.output.SaleOrderInserterOutPort;
import br.com.alc.ecommerce.checkout.core.service.authorize.SaleAuthorizerService;
import br.com.alc.ecommerce.checkout.core.service.validator.SaleValidatorService;
import br.com.alc.ecommerce.checkout.core.service.watch.WatchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static br.com.alc.ecommerce.checkout.core.domain.sale.SaleStatus.ERROR;
import static br.com.alc.ecommerce.checkout.core.domain.sale.SaleStatus.PROCESSED;
import static org.mockito.Mockito.*;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class SaleProcessorUseCaseImplTest {

    @InjectMocks
    private SaleProcessorUseCaseImpl saleProcessorUseCase;

    @Mock
    private MostRecentSaleOrderFinderOutPort mostRecentSaleOrderFinderOutPortMock;

    @Mock
    private SaleValidatorService saleValidatorServiceMock;

    @Mock
    private SaleOrderInserterOutPort saleOrderInserterOutPortMock;

    @Mock
    private SaleAuthorizerService saleAuthorizerServiceMock;

    @Mock
    private SaleCallbackIntegrateOutPort saleCallbackIntegrateOutPortMock;

    @Mock
    private WatchService watchServiceMock;

    @Test
    void givenAnProcessedSaleOrderWhenExecutingTheSaleProcessorUseCaseThenShouldCallSaleCallbackIntegrateOutPort() {
        SaleRequest saleRequest = SaleRequest.builder().numberOrder("987654322").build();
        SaleOrder saleOrderReturned = SaleOrder.builder().status(PROCESSED).build();

        when(mostRecentSaleOrderFinderOutPortMock.execute(saleRequest.getNumberOrder())).thenReturn(Optional.of(saleOrderReturned));

        saleProcessorUseCase.execute(saleRequest);

        verify(saleCallbackIntegrateOutPortMock).execute(any(SaleCallbackRequest.class));
        verifyNoInteractions(saleValidatorServiceMock, saleOrderInserterOutPortMock, saleAuthorizerServiceMock, watchServiceMock);
    }

    @Test
    void givenAnUnprocessedSaleOrderWhenExecutingTheSaleProcessorUseCaseThenShouldCallSaleValidatorServiceAndSaleOrderInserterOutPortAndSaleAuthorizerServiceAndSaleCallbackIntegrateOutPort() {
        SaleRequest saleRequest = SaleRequest.builder().numberOrder("987654322").build();
        AuthorizeSaleResponse authorizeSaleResponseReturned = AuthorizeSaleResponse.builder().invoiceNumber("000000001").build();

        when(mostRecentSaleOrderFinderOutPortMock.execute(saleRequest.getNumberOrder())).thenReturn(Optional.empty());
        when(saleAuthorizerServiceMock.execute(saleRequest)).thenReturn(authorizeSaleResponseReturned);

        saleProcessorUseCase.execute(saleRequest);

        verify(saleValidatorServiceMock).execute(saleRequest);
        verify(saleOrderInserterOutPortMock, times(2)).execute(any(SaleOrder.class));
        verify(saleAuthorizerServiceMock).execute(any(SaleRequest.class));
        verify(saleCallbackIntegrateOutPortMock).execute(any(SaleCallbackRequest.class));
    }

    @Test
    void givenAnErrorSaleOrderWhenExecutingTheSaleProcessorUseCaseThenShouldCallSaleValidatorServiceAndSaleOrderInserterOutPortAndSaleAuthorizerServiceAndSaleCallbackIntegrateOutPort() {
        SaleRequest saleRequest = SaleRequest.builder().numberOrder("987654322").build();
        SaleOrder saleOrderReturned = SaleOrder.builder().status(ERROR).build();
        AuthorizeSaleResponse authorizeSaleResponseReturned = AuthorizeSaleResponse.builder().invoiceNumber("000000001").build();

        when(mostRecentSaleOrderFinderOutPortMock.execute(saleRequest.getNumberOrder())).thenReturn(Optional.of(saleOrderReturned));
        when(saleAuthorizerServiceMock.execute(saleRequest)).thenReturn(authorizeSaleResponseReturned);

        saleProcessorUseCase.execute(saleRequest);

        verify(saleValidatorServiceMock).execute(saleRequest);
        verify(saleOrderInserterOutPortMock, times(2)).execute(any(SaleOrder.class));
        verify(saleAuthorizerServiceMock).execute(any(SaleRequest.class));
        verify(saleCallbackIntegrateOutPortMock).execute(any(SaleCallbackRequest.class));
    }

    @Test
    void givenAnInvalidSaleOrderWhenExecutingTheSaleProcessorUseCaseThenShouldCallSaleOrderInserterOutPortAndSaleCallbackIntegrateOutPort() {
        SaleRequest saleRequest = SaleRequest.builder().numberOrder("987654322").build();

        when(mostRecentSaleOrderFinderOutPortMock.execute(saleRequest.getNumberOrder())).thenReturn(Optional.empty());
        doThrow(new RuntimeException("Validation Error")).when(saleValidatorServiceMock).execute(saleRequest);

        saleProcessorUseCase.execute(saleRequest);

        verify(saleOrderInserterOutPortMock).execute(any(SaleOrder.class));
        verify(saleCallbackIntegrateOutPortMock).execute(any(SaleCallbackRequest.class));
        verifyNoInteractions(saleAuthorizerServiceMock);
    }
}