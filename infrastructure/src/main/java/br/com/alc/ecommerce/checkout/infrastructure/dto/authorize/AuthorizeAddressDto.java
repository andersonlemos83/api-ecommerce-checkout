package br.com.alc.ecommerce.checkout.infrastructure.dto.authorize;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizeAddressDto implements Serializable {

    private String address;
    private String addressNumber;
    private String addressComplement;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
    private String zipCode;

}