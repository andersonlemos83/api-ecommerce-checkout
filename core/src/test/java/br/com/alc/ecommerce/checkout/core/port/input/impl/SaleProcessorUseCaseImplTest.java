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
    private MostRecentSaleOrderFinderOutPort mostRecentSaleOrderFinderOutPort;

    @Mock
    private SaleValidatorService saleValidatorService;

    @Mock
    private SaleOrderInserterOutPort saleOrderInserterOutPort;

    @Mock
    private SaleAuthorizerService saleAuthorizerService;

    @Mock
    private SaleCallbackIntegrateOutPort saleCallbackIntegrateOutPort;

    @Mock
    private WatchService watchService;

    @Test
    void givenAnProcessedSaleOrderWhenExecutingTheSaleProcessorUseCaseThenShouldCallSaleCallbackIntegrateOutPort() {
        SaleRequest saleRequest = SaleRequest.builder().numberOrder("987654322").build();
        SaleOrder saleOrderReturned = SaleOrder.builder().status(PROCESSED).build();

        when(mostRecentSaleOrderFinderOutPort.execute(saleRequest.getNumberOrder())).thenReturn(Optional.of(saleOrderReturned));

        saleProcessorUseCase.execute(saleRequest);

        verify(saleCallbackIntegrateOutPort).execute(any(SaleCallbackRequest.class));
        verifyNoInteractions(saleValidatorService, saleOrderInserterOutPort, saleAuthorizerService);
    }

    @Test
    void givenAnUnprocessedSaleOrderWhenExecutingTheSaleProcessorUseCaseThenShouldCallSaleValidatorServiceAndSaleOrderInserterOutPortAndSaleAuthorizerServiceAndSaleCallbackIntegrateOutPort() {
        SaleRequest saleRequest = SaleRequest.builder().numberOrder("987654322").build();
        AuthorizeSaleResponse authorizeSaleResponseReturned = AuthorizeSaleResponse.builder().invoiceNumber("000000001").build();

        when(mostRecentSaleOrderFinderOutPort.execute(saleRequest.getNumberOrder())).thenReturn(Optional.empty());
        when(saleAuthorizerService.execute(saleRequest)).thenReturn(authorizeSaleResponseReturned);

        saleProcessorUseCase.execute(saleRequest);

        verify(saleValidatorService).execute(saleRequest);
        verify(saleOrderInserterOutPort, times(2)).execute(any(SaleOrder.class));
        verify(saleAuthorizerService).execute(any(SaleRequest.class));
        verify(saleCallbackIntegrateOutPort).execute(any(SaleCallbackRequest.class));
    }

    @Test
    void givenAnErrorSaleOrderWhenExecutingTheSaleProcessorUseCaseThenShouldCallSaleValidatorServiceAndSaleOrderInserterOutPortAndSaleAuthorizerServiceAndSaleCallbackIntegrateOutPort() {
        SaleRequest saleRequest = SaleRequest.builder().numberOrder("987654322").build();
        SaleOrder saleOrderReturned = SaleOrder.builder().status(ERROR).build();
        AuthorizeSaleResponse authorizeSaleResponseReturned = AuthorizeSaleResponse.builder().invoiceNumber("000000001").build();

        when(mostRecentSaleOrderFinderOutPort.execute(saleRequest.getNumberOrder())).thenReturn(Optional.of(saleOrderReturned));
        when(saleAuthorizerService.execute(saleRequest)).thenReturn(authorizeSaleResponseReturned);

        saleProcessorUseCase.execute(saleRequest);

        verify(saleValidatorService).execute(saleRequest);
        verify(saleOrderInserterOutPort, times(2)).execute(any(SaleOrder.class));
        verify(saleAuthorizerService).execute(any(SaleRequest.class));
        verify(saleCallbackIntegrateOutPort).execute(any(SaleCallbackRequest.class));
    }

    @Test
    void givenAnInvalidSaleOrderWhenExecutingTheSaleProcessorUseCaseThenShouldCallSaleOrderInserterOutPortAndSaleCallbackIntegrateOutPort() {
        SaleRequest saleRequest = SaleRequest.builder().numberOrder("987654322").build();

        when(mostRecentSaleOrderFinderOutPort.execute(saleRequest.getNumberOrder())).thenReturn(Optional.empty());
        doThrow(new RuntimeException("Validation Error")).when(saleValidatorService).execute(saleRequest);

        saleProcessorUseCase.execute(saleRequest);

        verify(saleOrderInserterOutPort).execute(any(SaleOrder.class));
        verify(saleCallbackIntegrateOutPort).execute(any(SaleCallbackRequest.class));
        verifyNoInteractions(saleAuthorizerService);
    }
}