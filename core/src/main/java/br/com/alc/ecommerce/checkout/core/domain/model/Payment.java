package br.com.alc.ecommerce.checkout.core.domain.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Payment implements Serializable {

    private String paymentMethod;
    private String authorizationCode;
    private LocalDateTime paymentDate;
    private String cardNumber;
    private String sitefNsu;
    private BigDecimal value;

}