package br.com.alc.ecommerce.checkout.infrastructure.dto.callback;

import br.com.alc.ecommerce.checkout.core.domain.sale.SaleStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SaleCallbackRequestDto implements Serializable {

    private String orderNumber;

    private String invoiceKey;
    private String invoiceNumber;
    private LocalDateTime issuanceDate;
    private String invoiceBase64;

    private SaleStatus status;
    private String errorReason;

}