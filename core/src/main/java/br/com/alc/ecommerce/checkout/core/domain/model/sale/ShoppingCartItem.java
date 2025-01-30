package br.com.alc.ecommerce.checkout.core.domain.model.sale;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItem implements Serializable {

    private BigInteger code;
    private Integer quantity;
    private BigDecimal value;

}