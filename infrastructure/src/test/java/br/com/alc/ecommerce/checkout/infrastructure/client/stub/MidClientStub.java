package br.com.alc.ecommerce.checkout.infrastructure.client.stub;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.externalservices.JsonResponseDataTable;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Component
@RequiredArgsConstructor
public class MidClientStub {

    private static final String URL_AUTHORIZE = "/authorize";

    private final WireMockServer wireMockServer;

    public void configureAuthorizeEndpoint(JsonResponseDataTable jsonResponseDataTable) {
        if (jsonResponseDataTable.isStatusOk()) {
            wireMockServer.stubFor(post(urlEqualTo(URL_AUTHORIZE))
                    .withName(URL_AUTHORIZE)
                    .willReturn(okJson(jsonResponseDataTable.getResponse())));
        }
    }
}