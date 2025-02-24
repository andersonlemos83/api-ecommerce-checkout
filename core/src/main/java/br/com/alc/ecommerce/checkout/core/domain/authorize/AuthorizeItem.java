package br.com.alc.ecommerce.checkout.core.domain.authorize;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class AuthorizeItem implements Serializable {

    private Integer sequence;
    private BigInteger sku;
    private Integer amount;
    private BigDecimal value;

    private BigDecimal ivaCbsValue;
    private BigDecimal ivaIbsValue;

}