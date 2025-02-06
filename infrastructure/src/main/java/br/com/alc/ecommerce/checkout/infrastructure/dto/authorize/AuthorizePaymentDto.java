package br.com.alc.ecommerce.checkout.infrastructure.dto.authorize;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizePaymentDto implements Serializable {

    private Integer sequence;
    private String type;
    private String date;
    private String authorizationCode;
    private String cardNumber;
    private String pixKey;
    private BigDecimal value;

}