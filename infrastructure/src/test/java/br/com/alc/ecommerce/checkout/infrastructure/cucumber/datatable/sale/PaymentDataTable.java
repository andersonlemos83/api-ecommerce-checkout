package br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.sale;

import br.com.alc.ecommerce.checkout.core.domain.model.sale.PaymentMethod;
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

    private PaymentMethod paymentMethod;
    private LocalDateTime paymentDate;
    private String authorizationCode;
    private String cardNumber;
    private String pixKey;
    private BigDecimal value;

}