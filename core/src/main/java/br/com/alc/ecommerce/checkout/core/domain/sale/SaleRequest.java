package br.com.alc.ecommerce.checkout.core.domain.sale;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.math.BigDecimal.ZERO;
import static java.util.Collections.emptyList;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class SaleRequest implements Serializable {

    private String channelCode;
    private String companyCode;
    private String storeCode;
    private Integer pos;
    private String orderNumber;
    private BigDecimal totalValue;
    private BigDecimal freightValue;
    private Customer customer;
    private List<ShoppingCartItem> items;
    private List<Payment> payments;

    public String generateTerminalNumber() {
        return Optional.ofNullable(pos)
                .map(String::valueOf)
                .orElse(null);
    }

    public BigDecimal generateTotalValueMinusFreightValue() {
        BigDecimal total = Optional.ofNullable(totalValue).orElse(ZERO);
        BigDecimal freight = Optional.ofNullable(freightValue).orElse(ZERO);
        return total.subtract(freight);
    }

    public BigDecimal generateTotalItemValue() {
        return Optional.ofNullable(items)
                .orElse(emptyList())
                .stream()
                .map(ShoppingCartItem::generateTotalItemValue)
                .reduce(ZERO, BigDecimal::add);
    }

    public BigDecimal generateTotalPaymentValue() {
        return Optional.ofNullable(payments)
                .orElse(emptyList())
                .stream()
                .map(Payment::getValue)
                .reduce(ZERO, BigDecimal::add);
    }
}