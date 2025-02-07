package br.com.alc.ecommerce.checkout.core.domain.authorize;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class AuthorizeSaleRequest implements Serializable {

    private String companyCode;
    private String storeCode;
    private String terminalNumber;
    private String nsu;
    private BigDecimal totalValue;
    private String date;
    private AuthorizeCustomer customer;
    private List<AuthorizeItem> items;
    private List<AuthorizePayment> payments;

}