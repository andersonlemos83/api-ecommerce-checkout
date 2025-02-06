package br.com.alc.ecommerce.checkout.core.domain.sale;

import br.com.alc.ecommerce.checkout.core.util.EnumUtil;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static br.com.alc.ecommerce.checkout.core.domain.sale.PaymentMethod.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Payment implements Serializable {

    private PaymentMethod paymentMethod;
    private LocalDateTime paymentDate;
    private String authorizationCode;
    private String cardNumber;
    private String pixKey;
    private BigDecimal value;

    public String getNamePaymentMethod() {
        return EnumUtil.toName(paymentMethod);
    }

    public boolean isCredit() {
        return CREDIT.equals(paymentMethod);
    }

    public boolean isDebit() {
        return DEBIT.equals(paymentMethod);
    }

    public boolean isCash() {
        return CASH.equals(paymentMethod);
    }

    public boolean isPix() {
        return PIX.equals(paymentMethod);
    }
}