package br.com.alc.ecommerce.checkout.infrastructure.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public final class ErrorResponseDto implements Serializable {

    @ApiModelProperty(value = "Response HTTP status", example = "BAD_REQUEST", required = true)
    private HttpStatus httpStatus;

    @ApiModelProperty(value = "Response error message", example = "O campo totalAmount não foi informado.", required = true)
    private String message;

}