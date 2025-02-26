package br.com.alc.ecommerce.checkout.core.domain.sale;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import static java.math.BigDecimal.ZERO;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class ShoppingCartItem implements Serializable {

    private BigInteger code;
    private Integer quantity;
    private BigDecimal value;

    public BigDecimal generateTotalItemValue() {
        Integer q = Optional.ofNullable(quantity).orElse(0);
        BigDecimal v = Optional.ofNullable(value).orElse(ZERO);
        return v.multiply(new BigDecimal(q));
    }
}