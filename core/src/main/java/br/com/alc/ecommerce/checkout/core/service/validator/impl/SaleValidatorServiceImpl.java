package br.com.alc.ecommerce.checkout.core.service.validator.impl;

import br.com.alc.ecommerce.checkout.core.domain.sale.SaleRequest;
import br.com.alc.ecommerce.checkout.core.exception.TotalItemValueMismatchException;
import br.com.alc.ecommerce.checkout.core.exception.TotalPaymentValueMismatchException;
import br.com.alc.ecommerce.checkout.core.service.validator.SaleValidatorService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SaleValidatorServiceImpl implements SaleValidatorService {

    @Override
    public void execute(SaleRequest saleRequest) {
        if (saleRequest.getTotalPaymentValue().compareTo(saleRequest.getTotalValue()) != 0) {
            throw new TotalPaymentValueMismatchException();
        }

        if (saleRequest.getTotalItemValue().compareTo(saleRequest.getTotalValueMinusFreightValue()) != 0) {
            throw new TotalItemValueMismatchException();
        }
    }
}