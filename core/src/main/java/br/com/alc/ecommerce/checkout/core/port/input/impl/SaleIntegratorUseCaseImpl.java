package br.com.alc.ecommerce.checkout.core.port.input.impl;

import br.com.alc.ecommerce.checkout.core.domain.sale.SaleRequest;
import br.com.alc.ecommerce.checkout.core.domain.sale.SaleResponse;
import br.com.alc.ecommerce.checkout.core.port.input.SaleIntegratorUseCase;
import br.com.alc.ecommerce.checkout.core.port.output.SaleIntegratorOutPort;
import br.com.alc.ecommerce.checkout.core.service.watch.WatchService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import static br.com.alc.ecommerce.checkout.core.domain.sale.SaleStatus.IN_PROCESSING;
import static br.com.alc.ecommerce.checkout.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@AllArgsConstructor
public final class SaleIntegratorUseCaseImpl implements SaleIntegratorUseCase {

    private final SaleIntegratorOutPort saleIntegratorOutPort;
    private final WatchService watchService;

    @Override
    public SaleResponse execute(SaleRequest saleRequest) {
        log.info("Incoming into SaleIntegratorUseCaseImpl: {}", generateJson(saleRequest));
        saleIntegratorOutPort.execute(saleRequest);
        SaleResponse saleResponse = buildSaleResponse();
        log.info("Outgoing from SaleIntegratorUseCaseImpl: {}", generateJson(saleResponse));
        return saleResponse;
    }

    private SaleResponse buildSaleResponse() {
        return SaleResponse.builder()
                .status(IN_PROCESSING)
                .date(watchService.nowLocalDateTime())
                .build();
    }
}