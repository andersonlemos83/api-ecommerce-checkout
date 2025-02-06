package br.com.alc.ecommerce.checkout.infrastructure.dto.authorize;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizeCustomerDto implements Serializable {

    private String name;
    private String document;
    private String documentType;
    private String phone;
    private String email;
    private AuthorizeAddressDto address;

}