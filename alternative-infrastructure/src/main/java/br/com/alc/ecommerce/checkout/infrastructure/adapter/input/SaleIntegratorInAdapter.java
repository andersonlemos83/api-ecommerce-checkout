package br.com.alc.ecommerce.checkout.infrastructure.adapter.input;

import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.SaleRequestDto;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.SaleResponseDto;

public interface SaleIntegratorInAdapter {

    SaleResponseDto execute(SaleRequestDto saleRequestDto);

}