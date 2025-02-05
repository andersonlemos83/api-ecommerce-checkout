package br.com.alc.ecommerce.checkout.infrastructure.adapter.input.impl;

import br.com.alc.ecommerce.checkout.core.domain.sale.SaleRequest;
import br.com.alc.ecommerce.checkout.core.domain.sale.SaleResponse;
import br.com.alc.ecommerce.checkout.core.port.input.SaleIntegratorUseCase;
import br.com.alc.ecommerce.checkout.infrastructure.adapter.input.SaleIntegratorInAdapter;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.SaleRequestDto;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.SaleResponseDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SaleIntegratorInAdapterImpl implements SaleIntegratorInAdapter {

    private final SaleIntegratorUseCase saleIntegratorUseCase;
    private final ModelMapper modelMapper;

    @Override
    public SaleResponseDto execute(SaleRequestDto saleRequestDto) {
        SaleRequest saleRequest = modelMapper.map(saleRequestDto, SaleRequest.class);
        SaleResponse saleResponse = saleIntegratorUseCase.execute(saleRequest);
        return modelMapper.map(saleResponse, SaleResponseDto.class);
    }
}