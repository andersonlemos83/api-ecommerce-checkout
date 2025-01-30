package br.com.alc.ecommerce.checkout.infrastructure.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_NULL)
public final class ErrorResponseDto implements Serializable {

    private HttpStatus httpStatus;
    private String message;

}