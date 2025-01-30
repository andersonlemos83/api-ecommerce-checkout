package br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.sale;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDataTable implements Serializable {

    private String paymentMethod;
    private LocalDateTime paymentDate;
    private String authorizationCode;
    private String cardNumber;
    private String nsu;
    private BigDecimal value;

}