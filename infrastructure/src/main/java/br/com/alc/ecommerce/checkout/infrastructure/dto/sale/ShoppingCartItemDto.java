package br.com.alc.ecommerce.checkout.infrastructure.dto.sale;

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
    @NotNull(message = "não foi informado")
    @ApiModelProperty(value = "Item code", example = "1002319335599", required = true)
    private BigInteger code;

    @Min(1)
    @NotNull(message = "não foi informado")
    @ApiModelProperty(value = "Quantity of items", example = "1", required = true)
    private Integer quantity;

    @Min(0)
    @NotNull(message = "não foi informado")
    @ApiModelProperty(value = "Item value", example = "99.99", required = true)
    private BigDecimal value;

}