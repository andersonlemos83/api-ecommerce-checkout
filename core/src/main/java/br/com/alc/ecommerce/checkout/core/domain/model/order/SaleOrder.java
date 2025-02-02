package br.com.alc.ecommerce.checkout.core.domain.model.order;

import br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleStatus;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleStatus.ERROR;
import static br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleStatus.PROCESSED;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SaleOrder implements Serializable {

    private Long id;
    private String channelCode;
    private String companyCode;
    private String storeCode;
    private Integer pos;
    private String numberOrder;
    private BigDecimal totalAmount;
    private BigDecimal freightAmount;

    private String invoiceKey;
    private String invoiceNumber;
    private LocalDateTime issuanceDate;
    private String invoiceBase64;

    private SaleStatus status;
    private String errorReason;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public boolean isProcessed() {
        return PROCESSED.equals(status);
    }

    public boolean isError() {
        return ERROR.equals(status);
    }
}