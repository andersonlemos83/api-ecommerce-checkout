package br.com.alc.ecommerce.checkout.core.domain.model;

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

    private BigInteger sku;
    private Integer quantity;
    private BigDecimal value;

}