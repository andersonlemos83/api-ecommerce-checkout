package br.com.alc.ecommerce.checkout.infrastructure.dto.tax;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TaxResponseDto implements Serializable {

    private BigInteger code;
    private BigDecimal ivaCbsValue;
    private BigDecimal ivaIbsValue;

}