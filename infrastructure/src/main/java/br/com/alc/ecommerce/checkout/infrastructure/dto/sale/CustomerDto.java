package br.com.alc.ecommerce.checkout.infrastructure.dto.sale;

import br.com.alc.ecommerce.checkout.core.domain.sale.DocumentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto implements Serializable {

    @NotBlank(message = "não foi informado")
    @Schema(description = "Customer's full name", example = "Martin Kauê Lopes")
    private String name;

    @NotBlank(message = "não foi informado")
    @Schema(description = "Customer's document number", example = "60778532402")
    private String document;

    @NotNull(message = "não foi informado")
    @Schema(description = "Customer's document type", example = "CPF")
    private DocumentType documentType;

    @NotBlank(message = "não foi informado")
    @Schema(description = "Customer's address", example = "Rua Projetada 913")
    private String address;

    @NotBlank(message = "não foi informado")
    @Schema(description = "Customer's address number", example = "622")
    private String addressNumber;

    @Schema(description = "Customer's address complement", example = "Apt 202")
    private String addressComplement;

    @NotBlank(message = "não foi informado")
    @Schema(description = "Customer's neighborhood", example = "Antares")
    private String neighborhood;

    @NotBlank(message = "não foi informado")
    @Schema(description = "Customer's city", example = "Maceió")
    private String city;

    @NotBlank(message = "não foi informado")
    @Size(max = 2, min = 2)
    @Schema(description = "Customer's state", example = "AL")
    private String state;

    @NotBlank(message = "não foi informado")
    @Schema(description = "Customer's country", example = "Brasil")
    private String country;

    @NotBlank(message = "não foi informado")
    @Schema(description = "Customer's ZIP code", example = "57048434")
    private String zipCode;

    @Schema(description = "Customer's phone number", example = "82992344475", nullable = true)
    private String phone;

    @Email
    @Schema(description = "Customer's email", example = "martin_lopes@rafaelmarin.net", nullable = true)
    private String email;

}