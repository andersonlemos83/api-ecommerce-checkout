package br.com.alc.ecommerce.checkout.core.application.port.input.impl;

import br.com.alc.ecommerce.checkout.core.application.port.input.SaleIntegratorUseCase;
import br.com.alc.ecommerce.checkout.core.application.port.output.SaleIntegratorOutPort;
import br.com.alc.ecommerce.checkout.core.application.service.watch.WatchService;
import br.com.alc.ecommerce.checkout.core.domain.model.SaleRequest;
import br.com.alc.ecommerce.checkout.core.domain.model.SaleResponse;

import static br.com.alc.ecommerce.checkout.core.domain.model.SaleStatus.IN_PROCESSING;

public class SaleIntegratorUseCaseImpl implements SaleIntegratorUseCase {

    private final SaleIntegratorOutPort saleIntegratorOutPort;
    private final WatchService watchService;

    public SaleIntegratorUseCaseImpl(SaleIntegratorOutPort saleIntegratorOutPort,
                                     WatchService watchService) {
        this.saleIntegratorOutPort = saleIntegratorOutPort;
        this.watchService = watchService;
    }

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