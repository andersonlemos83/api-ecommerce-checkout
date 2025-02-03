package br.com.alc.ecommerce.checkout.infrastructure.adapter.output;

import br.com.alc.ecommerce.checkout.core.application.port.output.SaleIntegratorOutPort;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.Customer;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.Payment;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleRequest;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.ShoppingCartItem;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.CustomerDto;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.PaymentDto;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.SaleRequestDto;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.ShoppingCartItemDto;
import br.com.alc.ecommerce.checkout.infrastructure.messaging.producer.MessagingProducer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaleIntegratorOutPortImpl implements SaleIntegratorOutPort {

    private final MessagingProducer messagingProducer;

    private final String saleExchange;
    private final String authorizeSaleQueue;

    public SaleIntegratorOutPortImpl(MessagingProducer messagingProducer,
                                     @Value("${spring.rabbitmq.sale-exchange}") String saleExchange,
                                     @Value("${spring.rabbitmq.authorize-sale-queue}") String authorizeSaleQueue) {
        this.messagingProducer = messagingProducer;
        this.saleExchange = saleExchange;
        this.authorizeSaleQueue = authorizeSaleQueue;
    }

    @Override
    public void execute(SaleRequest saleRequest) {
        SaleRequestDto saleRequestDto = buildSaleRequestDto(saleRequest);
        messagingProducer.publish(saleExchange, authorizeSaleQueue, saleRequestDto);
    }

    private SaleRequestDto buildSaleRequestDto(SaleRequest saleRequest) {
        SaleRequestDto saleRequestDto = SaleRequestDto.builder().build();
        BeanUtils.copyProperties(saleRequest, saleRequestDto);
        saleRequestDto.setCustomer(buildCustomer(saleRequest.getCustomer()));
        saleRequestDto.setItems(buildShoppingCartItemDtoList(saleRequest.getItems()));
        saleRequestDto.setPayments(buildPaymentDtoList(saleRequest.getPayments()));
        return saleRequestDto;
    }

    private CustomerDto buildCustomer(Customer customer) {
        CustomerDto customerDto = CustomerDto.builder().build();
        BeanUtils.copyProperties(customer, customerDto);
        return customerDto;
    }

    private List<ShoppingCartItemDto> buildShoppingCartItemDtoList(List<ShoppingCartItem> shoppingCartItemList) {
        return shoppingCartItemList.stream().map(this::buildShoppingCartItemDto).toList();
    }

    private ShoppingCartItemDto buildShoppingCartItemDto(ShoppingCartItem shoppingCartItem) {
        ShoppingCartItemDto shoppingCartItemDto = ShoppingCartItemDto.builder().build();
        BeanUtils.copyProperties(shoppingCartItem, shoppingCartItemDto);
        return shoppingCartItemDto;
    }

    private List<PaymentDto> buildPaymentDtoList(List<Payment> paymentList) {
        return paymentList.stream().map(this::buildPaymentDto).toList();
    }

    private PaymentDto buildPaymentDto(Payment payment) {
        PaymentDto paymentDto = PaymentDto.builder().build();
        BeanUtils.copyProperties(payment, paymentDto);
        return paymentDto;
    }
}