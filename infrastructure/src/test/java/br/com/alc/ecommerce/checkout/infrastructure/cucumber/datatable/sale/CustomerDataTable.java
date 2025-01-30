package br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.sale;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDataTable implements Serializable {

    private String name;
    private String document;
    private String documentType;
    private String address;
    private String addressNumber;
    private String addressComplement;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String phone;
    private String email;

}