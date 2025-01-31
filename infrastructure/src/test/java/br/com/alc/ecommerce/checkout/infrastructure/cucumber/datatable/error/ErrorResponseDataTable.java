package br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.error;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class ErrorResponseDataTable implements Serializable {

    private HttpStatus httpStatus;
    private String message;

}