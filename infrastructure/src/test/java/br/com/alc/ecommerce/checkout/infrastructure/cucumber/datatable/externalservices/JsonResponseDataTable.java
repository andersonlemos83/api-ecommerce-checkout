package br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.externalservices;

import br.com.alc.ecommerce.checkout.infrastructure.fixture.ResourceFixture;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

import static lombok.AccessLevel.NONE;
import static org.springframework.http.HttpStatus.OK;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsonResponseDataTable implements Serializable {

    private HttpStatus status;

    @Getter(NONE)
    private String response;

    public String getResponse() {
        if (response.startsWith("/fixtures/")) {
            return ResourceFixture.getContentFromResourceJson(response);
        }
        return response;
    }

    public boolean isStatusOk() {
        return OK.equals(status);
    }
}