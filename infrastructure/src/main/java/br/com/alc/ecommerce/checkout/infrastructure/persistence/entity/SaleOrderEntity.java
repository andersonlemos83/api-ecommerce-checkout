package br.com.alc.ecommerce.checkout.infrastructure.persistence.entity;

import br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "ECOMMERCE_CHECKOUT_OWNER", name = "SALE_ORDER")
@SequenceGenerator(schema = "ECOMMERCE_CHECKOUT_OWNER", name = "sale_order_seq", sequenceName = "SALE_ORDER_SEQ", allocationSize = 1)
public class SaleOrderEntity implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = SEQUENCE, generator = "sale_order_seq")
    private Long id;

    @Column(name = "CHANNEL_CODE", nullable = false)
    private String channelCode;

    @Column(name = "COMPANY_CODE", nullable = false)
    private String companyCode;

    @Column(name = "STORE_CODE", nullable = false)
    private String storeCode;

    @Column(name = "POS", nullable = false)
    private Integer pos;

    @Column(name = "NUMBER_ORDER", nullable = false)
    private String numberOrder;

    @Column(name = "TOTAL_AMOUNT", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "FREIGHT_AMOUNT", nullable = false)
    private BigDecimal freightAmount;

    @Column(name = "INVOICE_KEY")
    private String invoiceKey;

    @Column(name = "INVOICE_NUMBER")
    private String invoiceNumber;

    @Column(name = "ISSUANCE_DATE")
    private LocalDateTime issuanceDate;

    @Column(name = "INVOICE_BASE64")
    private String invoiceBase64;

    @Enumerated(STRING)
    @Column(name = "STATUS", nullable = false)
    private SaleStatus status;

    @Column(name = "ERROR_REASON", length = 1000)
    private String errorReason;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "UPDATE_DATE", nullable = false)
    private LocalDateTime updatedDate;

}