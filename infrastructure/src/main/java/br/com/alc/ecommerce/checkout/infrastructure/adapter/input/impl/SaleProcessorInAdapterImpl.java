package br.com.alc.ecommerce.checkout.infrastructure.adapter.input.impl;

import br.com.alc.ecommerce.checkout.core.application.port.input.SaleProcessorUseCase;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleRequest;
import br.com.alc.ecommerce.checkout.infrastructure.adapter.input.SaleProcessorInAdapter;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.SaleRequestDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SaleProcessorInAdapterImpl implements SaleProcessorInAdapter {

    private final SaleProcessorUseCase saleProcessorUseCase;

    @Override
    public void execute(SaleRequestDto saleRequestDto) {
        SaleRequest saleRequest = buildSaleRequest(saleRequestDto);
        saleProcessorUseCase.execute(saleRequest);
    }

    private SaleRequest buildSaleRequest(SaleRequestDto saleRequestDto) {
        return new ModelMapper().map(saleRequestDto, SaleRequest.class);
    }
}