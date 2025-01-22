package br.com.alc.ecommerce.checkout.infrastructure.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
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

    @NotBlank(message = "was not provided")
    @ApiModelProperty(value = "Payment method", example = "CREDIT", required = true)
    private String paymentMethod;

    @NotBlank(message = "was not provided")
    @ApiModelProperty(value = "Transaction authorization code", required = true)
    private String authorizationCode;

    @NotNull(message = "was not provided")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty(value = "Payment date", example = "2025-01-22T20:08:56.194", required = true)
    private LocalDateTime paymentDate;

    @ApiModelProperty(value = "Card number", example = "3556777163651312")
    private String cardNumber;

    @ApiModelProperty(value = "Sitef sequential number", example = "123456")
    private String sitefNsu;

    @Min(0)
    @NotNull(message = "was not provided")
    @ApiModelProperty(value = "Payment value", example = "100.01", required = true)
    private BigDecimal value;

}