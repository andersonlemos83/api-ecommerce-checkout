package br.com.alc.ecommerce.checkout.infrastructure.dto.sale;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SaleRequestDto implements Serializable {

    @NotBlank(message = "não foi informado")
    @ApiModelProperty(value = "Channel code", example = "APP", required = true)
    private String channelCode;

    @NotBlank(message = "não foi informado")
    @Size(min = 3, max = 3)
    @ApiModelProperty(value = "Company code (composed of 3 digits, possibly with leading zeros)", example = "001", required = true)
    private String companyCode;

    @NotBlank(message = "não foi informado")
    @Size(min = 3, max = 3)
    @ApiModelProperty(value = "Store code (composed of 3 digits, possibly with leading zeros)", example = "100", required = true)
    private String storeCode;

    @Min(1)
    @NotNull(message = "não foi informado")
    @ApiModelProperty(value = "Point of sale (POS)", example = "105", required = true)
    private Integer pos;

    @NotBlank(message = "não foi informado")
    @ApiModelProperty(value = "Number Order", example = "987654321", required = true)
    private String numberOrder;

    @Min(0)
    @NotNull(message = "não foi informado")
    @ApiModelProperty(value = "Total amount", example = "100.01", required = true)
    private BigDecimal totalAmount;

    @Min(0)
    @NotNull(message = "não foi informado")
    @ApiModelProperty(value = "Freight amount", example = "5.05", required = true)
    private BigDecimal freightAmount;

    @Valid
    @NotNull(message = "não foi informado")
    @ApiModelProperty(value = "Customer details", required = true)
    private CustomerDto customer;

    @NotNull(message = "não foi informado")
    @NotEmpty(message = "não foi informado")
    @ApiModelProperty(value = "Shopping cart items", required = true)
    private List<@Valid ShoppingCartItemDto> items;

    @NotNull(message = "não foi informado")
    @NotEmpty(message = "não foi informado")
    @ApiModelProperty(value = "Payments", required = true)
    private List<@Valid PaymentDto> payments;

}