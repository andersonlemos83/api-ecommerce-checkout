package br.com.alc.ecommerce.checkout.core.domain.model.sale;

import br.com.alc.ecommerce.checkout.core.application.util.EnumUtil;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {

    private String name;
    private String document;
    private DocumentType documentType;
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

    public String getNameDocumentType() {
        return EnumUtil.toName(documentType);
    }
}