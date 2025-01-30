package br.com.alc.ecommerce.checkout.infrastructure.adapter.input.impl;

import br.com.alc.ecommerce.checkout.core.application.port.input.SaleIntegratorUseCase;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.*;
import br.com.alc.ecommerce.checkout.infrastructure.adapter.input.SaleIntegratorInAdapter;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

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
        saleRequest.setCustomer(buildCustomer(saleRequestDto.getCustomer()));
        saleRequest.setItems(buildShoppingCartItemList(saleRequestDto.getItems()));
        saleRequest.setPayments(buildPaymentList(saleRequestDto.getPayments()));
        return saleRequest;
    }

    private Customer buildCustomer(CustomerDto customerDto) {
        Customer customer = Customer.builder().build();
        BeanUtils.copyProperties(customerDto, customer);
        return customer;
    }

    private List<ShoppingCartItem> buildShoppingCartItemList(List<ShoppingCartItemDto> shoppingCartItemDtoList) {
        return shoppingCartItemDtoList.stream().map(this::buildShoppingCartItem).toList();
    }

    private ShoppingCartItem buildShoppingCartItem(ShoppingCartItemDto shoppingCartItemDto) {
        ShoppingCartItem shoppingCartItem = ShoppingCartItem.builder().build();
        BeanUtils.copyProperties(shoppingCartItemDto, shoppingCartItem);
        return shoppingCartItem;
    }

    private List<Payment> buildPaymentList(List<PaymentDto> paymentDtoList) {
        return paymentDtoList.stream().map(this::buildPayment).toList();
    }

    private Payment buildPayment(PaymentDto paymentDto) {
        Payment payment = Payment.builder().build();
        BeanUtils.copyProperties(paymentDto, payment);
        return payment;
    }

    private SaleResponseDto buildSaleResponseDto(SaleResponse saleResponse) {
        SaleResponseDto saleResponseDto = SaleResponseDto.builder().build();
        BeanUtils.copyProperties(saleResponse, saleResponseDto);
        return saleResponseDto;
    }
}