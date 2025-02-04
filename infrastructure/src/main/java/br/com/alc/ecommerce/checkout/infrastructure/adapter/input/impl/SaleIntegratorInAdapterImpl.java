package br.com.alc.ecommerce.checkout.infrastructure.adapter.input.impl;

import br.com.alc.ecommerce.checkout.core.application.port.input.SaleIntegratorUseCase;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleRequest;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleResponse;
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

    @Override
    public SaleResponseDto execute(SaleRequestDto saleRequestDto) {
        SaleRequest saleRequest = buildSaleRequest(saleRequestDto);
        SaleResponse saleResponse = saleIntegratorUseCase.execute(saleRequest);
        return buildSaleResponseDto(saleResponse);
    }

    private SaleRequest buildSaleRequest(SaleRequestDto saleRequestDto) {
        return new ModelMapper().map(saleRequestDto, SaleRequest.class);
    }

    private SaleResponseDto buildSaleResponseDto(SaleResponse saleResponse) {
        return new ModelMapper().map(saleResponse, SaleResponseDto.class);
    }
}