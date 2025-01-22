package br.com.alc.ecommerce.checkout.infrastructure.dto;

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

    @NotBlank(message = "was not provided")
    @ApiModelProperty(value = "Channel code", example = "APP", required = true)
    private String channelCode;

    @NotBlank(message = "was not provided")
    @Size(min = 3, max = 3)
    @ApiModelProperty(value = "Company code (composed of 3 digits, possibly with leading zeros)", example = "001", required = true)
    private String companyCode;

    @NotBlank(message = "was not provided")
    @Size(min = 3, max = 3)
    @ApiModelProperty(value = "Store code (composed of 3 digits, possibly with leading zeros)", example = "099", required = true)
    private String storeCode;

    @Min(1)
    @ApiModelProperty(value = "Point of sale (POS)", example = "001")
    private Integer pos;

    @Valid
    @NotBlank(message = "was not provided")
    @Pattern(regexp = "^[0-9]+(-[0-9]+)?$", message = "must follow the positive numeric pattern with a separator digit (e.g., 987654321-1)")
    @ApiModelProperty(value = "Number Order", example = "987654321-1", required = true)
    private String numberOrder;

    @Min(0)
    @NotNull(message = "was not provided")
    @ApiModelProperty(value = "Total amount", example = "100.01", required = true)
    private BigDecimal totalAmount;

    @Min(0)
    @NotNull(message = "was not provided")
    @ApiModelProperty(value = "Freight amount", example = "10.01", required = true)
    private BigDecimal freightAmount;

    @Valid
    @NotNull(message = "was not provided")
    @ApiModelProperty(value = "Customer details", required = true)
    private CustomerDto customer;

    @NotNull(message = "was not provided")
    @NotEmpty(message = "was not provided")
    @ApiModelProperty(value = "Shopping cart items", required = true)
    private List<@Valid ShoppingCartItemDto> items;

    @NotNull(message = "was not provided")
    @NotEmpty(message = "was not provided")
    @ApiModelProperty(value = "Payments", required = true)
    private List<@Valid PaymentDto> payments;

}