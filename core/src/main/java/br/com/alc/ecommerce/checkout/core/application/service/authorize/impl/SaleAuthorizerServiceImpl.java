package br.com.alc.ecommerce.checkout.core.application.service.authorize.impl;

import br.com.alc.ecommerce.checkout.core.application.port.output.SaleAuthorizerOutPort;
import br.com.alc.ecommerce.checkout.core.application.port.output.TaxFinderOutPort;
import br.com.alc.ecommerce.checkout.core.application.service.authorize.SaleAuthorizerService;
import br.com.alc.ecommerce.checkout.core.application.service.authorize.factory.AuthorizePaymentFactory;
import br.com.alc.ecommerce.checkout.core.application.service.watch.WatchService;
import br.com.alc.ecommerce.checkout.core.application.util.DateUtil;
import br.com.alc.ecommerce.checkout.core.domain.model.authorize.*;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.Customer;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.Payment;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleRequest;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.ShoppingCartItem;
import br.com.alc.ecommerce.checkout.core.domain.model.tax.TaxResponse;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
public class SaleAuthorizerServiceImpl implements SaleAuthorizerService {

    private final SaleAuthorizerOutPort saleAuthorizerOutPort;
    private final TaxFinderOutPort taxFinderOutPort;
    private final AuthorizePaymentFactory authorizePaymentFactory;
    private final WatchService watchService;

    @Override
    public AuthorizeSaleResponse execute(SaleRequest saleRequest) {
        AuthorizeSaleRequest authorizeSaleRequest = buildAuthorizeSaleRequest(saleRequest);
        return saleAuthorizerOutPort.execute(authorizeSaleRequest);
    }

    private AuthorizeSaleRequest buildAuthorizeSaleRequest(SaleRequest saleRequest) {
        String nsu = createNsu(saleRequest.getNumberOrder());
        AuthorizeCustomer authorizeCustomer = buildAuthorizeCustomer(saleRequest.getCustomer());
        List<AuthorizeItem> items = buildAuthorizeItemList(saleRequest.getItems());
        List<AuthorizePayment> payments = buildAuthorizePaymentList(saleRequest.getPayments());
        return AuthorizeSaleRequest.builder()
                .companyCode(saleRequest.getCompanyCode())
                .storeCode(saleRequest.getStoreCode())
                .terminalNumber(saleRequest.getTerminalNumber())
                .nsu(nsu)
                .totalValue(saleRequest.getTotalValue())
                .date(createDate())
                .customer(authorizeCustomer)
                .items(items)
                .payments(payments)
                .build();
    }

    private String createNsu(String numberOrder) {
        String sequential = DateUtil.createSequential(watchService.nowLocalDateTime());
        return numberOrder + sequential;
    }

    private String createDate() {
        return DateUtil.format(watchService.nowLocalDateTime());
    }

    private AuthorizeCustomer buildAuthorizeCustomer(Customer customer) {
        AuthorizeAddress authorizeAddress = buildAuthorizeAddress(customer);
        return AuthorizeCustomer.builder()
                .name(customer.getName())
                .document(customer.getDocument())
                .documentType(customer.getNameDocumentType())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .address(authorizeAddress)
                .build();
    }

    private AuthorizeAddress buildAuthorizeAddress(Customer customer) {
        return AuthorizeAddress.builder()
                .address(customer.getAddress())
                .addressNumber(customer.getAddressNumber())
                .addressComplement(customer.getAddressComplement())
                .neighborhood(customer.getNeighborhood())
                .city(customer.getCity())
                .state(customer.getState())
                .country(customer.getCountry())
                .zipCode(customer.getZipCode())
                .build();
    }

    private List<AuthorizeItem> buildAuthorizeItemList(List<ShoppingCartItem> items) {
        final AtomicInteger sequence = new AtomicInteger(1);
        return items.stream()
                .map(i -> buildAuthorizeItem(i, sequence.getAndIncrement()))
                .toList();
    }

    private AuthorizeItem buildAuthorizeItem(ShoppingCartItem item, int sequence) {
        TaxResponse taxResponse = taxFinderOutPort.execute(item.getCode());
        return AuthorizeItem.builder()
                .sequence(sequence)
                .sku(item.getCode())
                .amount(item.getQuantity())
                .value(item.getValue())
                .ivaCbsValue(taxResponse.getIvaCbsValue())
                .ivaIbsValue(taxResponse.getIvaIbsValue())
                .build();
    }

    private List<AuthorizePayment> buildAuthorizePaymentList(List<Payment> payments) {
        final AtomicInteger sequence = new AtomicInteger(1);
        return payments.stream()
                .map(p -> authorizePaymentFactory.createAuthorizePayment(p, sequence.getAndIncrement()))
                .toList();
    }
}