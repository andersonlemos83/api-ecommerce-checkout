package br.com.alc.ecommerce.checkout.core.application.port.input.impl;

import br.com.alc.ecommerce.checkout.core.application.port.output.SaleIntegratorOutPort;
import br.com.alc.ecommerce.checkout.core.application.service.watch.WatchService;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleRequest;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleStatus.IN_PROCESSING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class SaleIntegratorUseCaseImplTest {

    @InjectMocks
    private SaleIntegratorUseCaseImpl saleIntegratorUseCase;

    @Mock
    private SaleIntegratorOutPort saleIntegratorOutPort;

    @Mock
    private WatchService watchService;

    @Test
    void whenExecutingTheExecuteMethodShouldCallSaleIntegratorAndReturnTheExpectedObject() {
        SaleRequest saleRequest = new SaleRequest();
        LocalDateTime nowExpected = LocalDateTime.now();
        when(watchService.nowLocalDateTime()).thenReturn(nowExpected);

        SaleResponse response = saleIntegratorUseCase.execute(saleRequest);

        verify(saleIntegratorOutPort, times(1)).execute(saleRequest);

        assertNotNull(response);
        assertEquals(IN_PROCESSING, response.getStatus());
        assertEquals(nowExpected, response.getDate());
    }
}