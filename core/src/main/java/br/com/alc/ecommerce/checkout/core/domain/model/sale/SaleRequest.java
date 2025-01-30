package br.com.alc.ecommerce.checkout.core.domain.model.sale;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SaleRequest implements Serializable {

    private String channelCode;
    private String companyCode;
    private String storeCode;
    private Integer pos;
    private String numberOrder;
    private BigDecimal totalAmount;
    private BigDecimal freightAmount;
    private Customer customer;
    private List<ShoppingCartItem> items;
    private List<Payment> payments;

}