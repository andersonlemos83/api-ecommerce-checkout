package br.com.alc.ecommerce.checkout.infrastructure.adapter.input.impl;

import br.com.alc.ecommerce.checkout.core.domain.sale.SaleRequest;
import br.com.alc.ecommerce.checkout.core.domain.sale.SaleResponse;
import br.com.alc.ecommerce.checkout.core.port.input.SaleIntegratorUseCase;
import br.com.alc.ecommerce.checkout.infrastructure.adapter.input.SaleIntegratorInAdapter;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.SaleRequestDto;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.SaleResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import static br.com.alc.ecommerce.checkout.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@Component
@AllArgsConstructor
public class SaleIntegratorInAdapterImpl implements SaleIntegratorInAdapter {

    private final SaleIntegratorUseCase saleIntegratorUseCase;
    private final ModelMapper modelMapper;

    @Override
    public SaleResponseDto execute(SaleRequestDto saleRequestDto) {
        log.debug("---> SaleIntegratorInAdapterImpl: {}", generateJson(saleRequestDto));
        SaleRequest saleRequest = modelMapper.map(saleRequestDto, SaleRequest.class);
        SaleResponse saleResponse = saleIntegratorUseCase.execute(saleRequest);
        SaleResponseDto saleResponseDto = modelMapper.map(saleResponse, SaleResponseDto.class);
        log.debug("<--- SaleIntegratorInAdapterImpl: {}", generateJson(saleResponseDto));
        return saleResponseDto;
    }
}