package br.com.alc.ecommerce.checkout.core.application.service.validator.impl;

import br.com.alc.ecommerce.checkout.core.application.service.validator.SaleValidatorService;
import br.com.alc.ecommerce.checkout.core.domain.exception.TotalItemValueMismatchException;
import br.com.alc.ecommerce.checkout.core.domain.exception.TotalPaymentValueMismatchException;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleRequest;
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