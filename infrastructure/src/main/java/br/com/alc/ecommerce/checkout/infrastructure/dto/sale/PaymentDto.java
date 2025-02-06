package br.com.alc.ecommerce.checkout.infrastructure.dto.sale;

import br.com.alc.ecommerce.checkout.core.domain.sale.PaymentMethod;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto implements Serializable {

    @NotNull(message = "não foi informado")
    @Schema(description = "Payment method", example = "CREDIT", required = true)
    private PaymentMethod paymentMethod;

    @NotNull(message = "não foi informado")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Schema(description = "Payment date", example = "2025-01-22T20:08:56.194", required = true)
    private LocalDateTime paymentDate;

    @NotBlank(message = "não foi informado")
    @Schema(description = "Transaction authorization code", example = "270606", required = true)
    private String authorizationCode;

    @Schema(description = "Card number", example = "3556777163651312")
    private String cardNumber;

    @Schema(description = "PIX key", example = "82992344475")
    private String pixKey;

    @Min(0)
    @NotNull(message = "não foi informado")
    @Schema(description = "Payment value", example = "105.04", required = true)
    private BigDecimal value;

}