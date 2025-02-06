package br.com.alc.ecommerce.checkout.infrastructure.adapter.input;

import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.SaleRequestDto;

public interface SaleProcessorInAdapter {

    void execute(SaleRequestDto saleRequestDto);

}