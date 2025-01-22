package br.com.alc.ecommerce.checkout.infrastructure.dto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItemDto implements Serializable {

    @Min(1)
    @NotNull(message = "was not provided")
    @ApiModelProperty(value = "Item code", example = "1002319335599", required = true)
    private BigInteger sku;

    @Min(1)
    @NotNull(message = "was not provided")
    @ApiModelProperty(value = "Quantity of items", example = "2", required = true)
    private Integer quantity;

    @Min(0)
    @NotNull(message = "was not provided")
    @ApiModelProperty(value = "Item value", example = "100.01", required = true)
    private BigDecimal value;

}