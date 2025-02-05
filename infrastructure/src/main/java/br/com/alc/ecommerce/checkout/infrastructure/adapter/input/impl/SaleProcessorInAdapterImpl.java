package br.com.alc.ecommerce.checkout.infrastructure.adapter.input.impl;

import br.com.alc.ecommerce.checkout.core.domain.sale.SaleRequest;
import br.com.alc.ecommerce.checkout.core.port.input.SaleProcessorUseCase;
import br.com.alc.ecommerce.checkout.infrastructure.adapter.input.SaleProcessorInAdapter;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.SaleRequestDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SaleProcessorInAdapterImpl implements SaleProcessorInAdapter {

    private final SaleProcessorUseCase saleProcessorUseCase;
    private final ModelMapper modelMapper;

    @Override
    public void execute(SaleRequestDto saleRequestDto) {
        SaleRequest saleRequest = modelMapper.map(saleRequestDto, SaleRequest.class);
        saleProcessorUseCase.execute(saleRequest);
    }
}