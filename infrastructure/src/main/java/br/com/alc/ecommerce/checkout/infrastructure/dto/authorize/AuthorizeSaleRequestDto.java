package br.com.alc.ecommerce.checkout.infrastructure.dto.authorize;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizeSaleRequestDto implements Serializable {

    private String companyCode;
    private String storeCode;
    private String terminalNumber;
    private String nsu;
    private BigDecimal totalValue;
    private String date;
    private AuthorizeCustomerDto customer;
    private List<AuthorizeItemDto> items;
    private List<AuthorizePaymentDto> payments;

}