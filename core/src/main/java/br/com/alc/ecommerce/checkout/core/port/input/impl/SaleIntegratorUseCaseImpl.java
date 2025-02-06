package br.com.alc.ecommerce.checkout.core.port.input.impl;

import br.com.alc.ecommerce.checkout.core.domain.sale.SaleRequest;
import br.com.alc.ecommerce.checkout.core.domain.sale.SaleResponse;
import br.com.alc.ecommerce.checkout.core.port.input.SaleIntegratorUseCase;
import br.com.alc.ecommerce.checkout.core.port.output.SaleIntegratorOutPort;
import br.com.alc.ecommerce.checkout.core.service.watch.WatchService;
import lombok.AllArgsConstructor;

import static br.com.alc.ecommerce.checkout.core.domain.sale.SaleStatus.IN_PROCESSING;

@AllArgsConstructor
public class SaleIntegratorUseCaseImpl implements SaleIntegratorUseCase {

    private final SaleIntegratorOutPort saleIntegratorOutPort;
    private final WatchService watchService;

    @Override
    public SaleResponse execute(SaleRequest saleRequest) {
        saleIntegratorOutPort.execute(saleRequest);
        return buildSaleResponse();
    }

    private SaleResponse buildSaleResponse() {
        return SaleResponse.builder()
                .status(IN_PROCESSING)
                .date(watchService.nowLocalDateTime())
                .build();
    }
}