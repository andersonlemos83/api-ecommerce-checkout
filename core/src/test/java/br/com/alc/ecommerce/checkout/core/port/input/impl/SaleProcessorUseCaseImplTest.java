package br.com.alc.ecommerce.checkout.core.port.input.impl;

import br.com.alc.ecommerce.checkout.core.domain.authorize.AuthorizeSaleResponse;
import br.com.alc.ecommerce.checkout.core.domain.callback.SaleCallbackRequest;
import br.com.alc.ecommerce.checkout.core.domain.order.SaleOrder;
import br.com.alc.ecommerce.checkout.core.domain.sale.SaleRequest;
import br.com.alc.ecommerce.checkout.core.port.output.MostRecentSaleOrderFinderOutPort;
import br.com.alc.ecommerce.checkout.core.port.output.SaleCallbackIntegratorOutPort;
import br.com.alc.ecommerce.checkout.core.port.output.SaleOrderInserterOutPort;
import br.com.alc.ecommerce.checkout.core.service.authorize.SaleAuthorizerService;
import br.com.alc.ecommerce.checkout.core.service.validator.SaleValidatorService;
import br.com.alc.ecommerce.checkout.core.service.watch.WatchService;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static br.com.alc.ecommerce.checkout.core.domain.sale.SaleStatus.*;
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
    private SaleCallbackIntegratorOutPort saleCallbackIntegratorOutPortMock;

    @Mock
    private WatchService watchServiceMock;

    @Test
    void givenAnProcessedSaleOrderWhenExecutingTheSaleProcessorUseCaseThenShouldCallSaleCallbackIntegrateOutPort() {
        SaleRequest saleRequest = Instancio.of(SaleRequest.class).set(Select.field("orderNumber"), "987654322").create();
        SaleOrder saleOrderReturned = Instancio.of(SaleOrder.class).set(Select.field("status"), PROCESSED).create();

        when(mostRecentSaleOrderFinderOutPortMock.execute(saleRequest.getOrderNumber())).thenReturn(Optional.of(saleOrderReturned));

        saleProcessorUseCase.execute(saleRequest);

        verify(saleCallbackIntegratorOutPortMock).execute(any(SaleCallbackRequest.class));
        verifyNoInteractions(saleValidatorServiceMock, saleOrderInserterOutPortMock, saleAuthorizerServiceMock, watchServiceMock);
    }

    @Test
    void givenAnInProcessingSaleOrderWhenExecutingTheSaleProcessorUseCaseThenShouldNotCallAnyoneDependency() {
        SaleRequest saleRequest = Instancio.of(SaleRequest.class).set(Select.field("orderNumber"), "987654322").create();
        SaleOrder saleOrderReturned = Instancio.of(SaleOrder.class).set(Select.field("status"), IN_PROCESSING).create();

        when(mostRecentSaleOrderFinderOutPortMock.execute(saleRequest.getOrderNumber())).thenReturn(Optional.of(saleOrderReturned));

        saleProcessorUseCase.execute(saleRequest);

        verifyNoInteractions(saleValidatorServiceMock, saleOrderInserterOutPortMock, saleAuthorizerServiceMock, saleCallbackIntegratorOutPortMock, watchServiceMock);
    }

    @Test
    void givenAnUnprocessedSaleOrderWhenExecutingTheSaleProcessorUseCaseThenShouldCallSaleValidatorServiceAndSaleOrderInserterOutPortAndSaleAuthorizerServiceAndSaleCallbackIntegrateOutPort() {
        SaleRequest saleRequest = Instancio.of(SaleRequest.class).set(Select.field("orderNumber"), "987654322").create();
        AuthorizeSaleResponse authorizeSaleResponseReturned = Instancio.of(AuthorizeSaleResponse.class).set(Select.field("invoiceNumber"), "000000001").create();

        when(mostRecentSaleOrderFinderOutPortMock.execute(saleRequest.getOrderNumber())).thenReturn(Optional.empty());
        when(saleAuthorizerServiceMock.execute(saleRequest)).thenReturn(authorizeSaleResponseReturned);

        saleProcessorUseCase.execute(saleRequest);

        verify(saleValidatorServiceMock).execute(saleRequest);
        verify(saleOrderInserterOutPortMock, times(2)).execute(any(SaleOrder.class));
        verify(saleAuthorizerServiceMock).execute(any(SaleRequest.class));
        verify(saleCallbackIntegratorOutPortMock).execute(any(SaleCallbackRequest.class));
    }

    @Test
    void givenAnErrorSaleOrderWhenExecutingTheSaleProcessorUseCaseThenShouldCallSaleValidatorServiceAndSaleOrderInserterOutPortAndSaleAuthorizerServiceAndSaleCallbackIntegrateOutPort() {
        SaleRequest saleRequest = Instancio.of(SaleRequest.class).set(Select.field("orderNumber"), "987654322").create();
        SaleOrder saleOrderReturned = Instancio.of(SaleOrder.class).set(Select.field("status"), ERROR).create();
        AuthorizeSaleResponse authorizeSaleResponseReturned = Instancio.of(AuthorizeSaleResponse.class).set(Select.field("invoiceNumber"), "000000001").create();

        when(mostRecentSaleOrderFinderOutPortMock.execute(saleRequest.getOrderNumber())).thenReturn(Optional.of(saleOrderReturned));
        when(saleAuthorizerServiceMock.execute(saleRequest)).thenReturn(authorizeSaleResponseReturned);

        saleProcessorUseCase.execute(saleRequest);

        verify(saleValidatorServiceMock).execute(saleRequest);
        verify(saleOrderInserterOutPortMock, times(2)).execute(any(SaleOrder.class));
        verify(saleAuthorizerServiceMock).execute(any(SaleRequest.class));
        verify(saleCallbackIntegratorOutPortMock).execute(any(SaleCallbackRequest.class));
    }

    @Test
    void givenAnInvalidSaleOrderWhenExecutingTheSaleProcessorUseCaseThenShouldCallSaleOrderInserterOutPortAndSaleCallbackIntegrateOutPort() {
        SaleRequest saleRequest = Instancio.of(SaleRequest.class).set(Select.field("orderNumber"), "987654322").create();

        when(mostRecentSaleOrderFinderOutPortMock.execute(saleRequest.getOrderNumber())).thenReturn(Optional.empty());
        doThrow(new RuntimeException("Validation Error")).when(saleValidatorServiceMock).execute(saleRequest);

        saleProcessorUseCase.execute(saleRequest);

        verify(saleOrderInserterOutPortMock).execute(any(SaleOrder.class));
        verify(saleCallbackIntegratorOutPortMock).execute(any(SaleCallbackRequest.class));
        verifyNoInteractions(saleAuthorizerServiceMock);
    }
}