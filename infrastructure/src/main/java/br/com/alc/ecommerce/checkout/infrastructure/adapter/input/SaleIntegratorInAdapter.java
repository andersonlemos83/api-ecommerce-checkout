package br.com.alc.ecommerce.checkout.infrastructure.adapter.input;

import br.com.alc.ecommerce.checkout.infrastructure.dto.SaleRequestDto;
import br.com.alc.ecommerce.checkout.infrastructure.dto.SaleResponseDto;

public interface SaleIntegratorInAdapter {

    SaleResponseDto execute(SaleRequestDto saleRequestDto);

}