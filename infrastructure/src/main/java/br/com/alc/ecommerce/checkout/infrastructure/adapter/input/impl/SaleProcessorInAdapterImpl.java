package br.com.alc.ecommerce.checkout.infrastructure.adapter.input.impl;

import br.com.alc.ecommerce.checkout.core.application.port.input.SaleProcessorUseCase;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.Customer;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.Payment;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleRequest;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.ShoppingCartItem;
import br.com.alc.ecommerce.checkout.infrastructure.adapter.input.SaleProcessorInAdapter;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.CustomerDto;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.PaymentDto;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.SaleRequestDto;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.ShoppingCartItemDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class SaleProcessorInAdapterImpl implements SaleProcessorInAdapter {

    private final SaleProcessorUseCase saleProcessorUseCase;

    @Override
    public void execute(SaleRequestDto saleRequestDto) {
        SaleRequest saleRequest = buildSaleRequest(saleRequestDto);
        saleProcessorUseCase.execute(saleRequest);
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
}