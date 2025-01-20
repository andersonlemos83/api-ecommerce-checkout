package br.com.alc.ecommerce.checkout.infrastructure.adapter.input.impl;

import br.com.alc.ecommerce.checkout.core.application.port.input.SaleIntegratorUseCase;
import br.com.alc.ecommerce.checkout.core.domain.model.SaleRequest;
import br.com.alc.ecommerce.checkout.core.domain.model.SaleResponse;
import br.com.alc.ecommerce.checkout.infrastructure.adapter.input.SaleIntegratorInAdapter;
import br.com.alc.ecommerce.checkout.infrastructure.dto.SaleRequestDto;
import br.com.alc.ecommerce.checkout.infrastructure.dto.SaleResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
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
        SaleRequest saleRequest = SaleRequest.builder().build();
        BeanUtils.copyProperties(saleRequestDto, saleRequest);
        return saleRequest;
    }

    private SaleResponseDto buildSaleResponseDto(SaleResponse saleResponse) {
        SaleResponseDto saleResponseDto = SaleResponseDto.builder().build();
        BeanUtils.copyProperties(saleResponse, saleResponseDto);
        return saleResponseDto;
    }
}