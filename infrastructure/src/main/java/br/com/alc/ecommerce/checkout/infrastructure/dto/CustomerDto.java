package br.com.alc.ecommerce.checkout.infrastructure.dto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto implements Serializable {

    @NotBlank(message = "was not provided")
    @ApiModelProperty(value = "Customer's full name", example = "Martin Kauê Lopes", required = true)
    private String name;

    @NotBlank(message = "was not provided")
    @ApiModelProperty(value = "Customer's document number", example = "60778532402", required = true)
    private String document;

    @NotBlank(message = "was not provided")
    @ApiModelProperty(value = "Customer's document type", example = "CPF", required = true)
    private String documentType;

    @NotBlank(message = "was not provided")
    @ApiModelProperty(value = "Customer's address", example = "Rua Projetada 913", required = true)
    private String address;

    @NotBlank(message = "was not provided")
    @ApiModelProperty(value = "Customer's address number", example = "622", required = true)
    private String addressNumber;

    @ApiModelProperty(value = "Customer's address complement", example = "Apt 202")
    private String addressComplement;

    @NotBlank(message = "was not provided")
    @ApiModelProperty(value = "Customer's neighborhood", example = "Antares", required = true)
    private String neighborhood;

    @NotBlank(message = "was not provided")
    @ApiModelProperty(value = "Customer's city", example = "Maceió", required = true)
    private String city;

    @NotBlank(message = "was not provided")
    @Size(max = 2, min = 2)
    @ApiModelProperty(value = "Customer's state", example = "AL", required = true)
    private String state;

    @NotBlank(message = "was not provided")
    @ApiModelProperty(value = "Customer's country", example = "Brazil", required = true)
    private String country;

    @NotBlank(message = "was not provided")
    @ApiModelProperty(value = "Customer's ZIP code", example = "57048434", required = true)
    private String zipCode;

    @ApiModelProperty(value = "Customer's phone number", example = "82992344475")
    private String phone;

    @Email
    @ApiModelProperty(value = "Customer's email", example = "martin_lopes@rafaelmarin.net")
    private String email;

}