package br.com.alc.ecommerce.checkout.core.port.input.impl;

import br.com.alc.ecommerce.checkout.core.domain.sale.SaleRequest;
import br.com.alc.ecommerce.checkout.core.domain.sale.SaleResponse;
import br.com.alc.ecommerce.checkout.core.port.output.SaleIntegratorOutPort;
import br.com.alc.ecommerce.checkout.core.service.watch.WatchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static br.com.alc.ecommerce.checkout.core.domain.sale.SaleStatus.IN_PROCESSING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class SaleIntegratorUseCaseImplTest {

    @InjectMocks
    private SaleIntegratorUseCaseImpl saleIntegratorUseCase;

    @Mock
    private SaleIntegratorOutPort saleIntegratorOutPortMock;

    @Mock
    private WatchService watchServiceMock;

    @Test
    void whenExecutingTheSaleIntegratorUseCaseThenShouldCallSaleIntegratorOutPortAndReturnTheExpectedObject() {
        SaleRequest saleRequest = new SaleRequest();
        LocalDateTime nowExpected = LocalDateTime.now();
        when(watchServiceMock.nowLocalDateTime()).thenReturn(nowExpected);

        SaleResponse response = saleIntegratorUseCase.execute(saleRequest);

        verify(saleIntegratorOutPortMock, times(1)).execute(saleRequest);

        assertNotNull(response);
        assertEquals(IN_PROCESSING, response.getStatus());
        assertEquals(nowExpected, response.getDate());
    }
}