package br.com.alc.ecommerce.checkout.infrastructure.dto.sale;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Item code", example = "1002319335599")
    private BigInteger code;

    @Min(1)
    @NotNull(message = "não foi informado")
    @Schema(description = "Quantity of items", example = "1")
    private Integer quantity;

    @Min(0)
    @NotNull(message = "não foi informado")
    @Schema(description = "Item value", example = "99.99")
    private BigDecimal value;

}