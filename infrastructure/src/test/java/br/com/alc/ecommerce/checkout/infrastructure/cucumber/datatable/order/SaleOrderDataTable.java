package br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.order;

import br.com.alc.ecommerce.checkout.core.domain.sale.SaleStatus;
import br.com.alc.ecommerce.checkout.infrastructure.helper.fixture.ResourceFixture;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static lombok.AccessLevel.NONE;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SaleOrderDataTable implements Serializable {

    private Long id;
    private String channelCode;
    private String companyCode;
    private String storeCode;
    private Integer pos;
    private String numberOrder;
    private BigDecimal totalValue;
    private BigDecimal freightValue;

    private String invoiceKey;
    private String invoiceNumber;
    private LocalDateTime issuanceDate;

    @Getter(NONE)
    private String invoiceBase64;

    private SaleStatus status;
    private String errorReason;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public String getInvoiceBase64() {
        if (invoiceBase64 == null || "".equalsIgnoreCase(invoiceBase64)) {
            return null;
        }
        if (invoiceBase64.startsWith("/fixtures/")) {
            return ResourceFixture.getContentFromResource(invoiceBase64);
        }
        return invoiceBase64;
    }
}