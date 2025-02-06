package br.com.alc.ecommerce.checkout.core.domain.authorize;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizeCustomer implements Serializable {

    private String name;
    private String document;
    private String documentType;
    private String phone;
    private String email;
    private AuthorizeAddress address;

}