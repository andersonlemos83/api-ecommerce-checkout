package br.com.alc.ecommerce.checkout.infrastructure.dto.sale;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Channel code", example = "APP")
    private String channelCode;

    @NotBlank(message = "não foi informado")
    @Size(min = 3, max = 3)
    @Schema(description = "Company code (composed of 3 digits, possibly with leading zeros)", example = "001")
    private String companyCode;

    @NotBlank(message = "não foi informado")
    @Size(min = 3, max = 3)
    @Schema(description = "Store code (composed of 3 digits, possibly with leading zeros)", example = "100")
    private String storeCode;

    @Min(1)
    @NotNull(message = "não foi informado")
    @Schema(description = "Point of sale (POS)", example = "105")
    private Integer pos;

    @NotBlank(message = "não foi informado")
    @Schema(description = "Order Number", example = "987654321")
    private String orderNumber;

    @Min(0)
    @NotNull(message = "não foi informado")
    @Schema(description = "Total value", example = "105.04")
    private BigDecimal totalValue;

    @Min(0)
    @NotNull(message = "não foi informado")
    @Schema(description = "Freight value", example = "5.05")
    private BigDecimal freightValue;

    @Valid
    @NotNull(message = "não foi informado")
    @Schema(description = "Customer details")
    private CustomerDto customer;

    @NotNull(message = "não foi informado")
    @NotEmpty(message = "não foi informado")
    @Schema(description = "Shopping cart items")
    private List<@Valid ShoppingCartItemDto> items;

    @NotNull(message = "não foi informado")
    @NotEmpty(message = "não foi informado")
    @Schema(description = "Payments")
    private List<@Valid PaymentDto> payments;

}