package br.com.alc.ecommerce.checkout.infrastructure.client.stub;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.externalservices.JsonResponseDataTable;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static wiremock.org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;

@Component
@RequiredArgsConstructor
public class TaxClientStub {

    private static final String URL_FIND_BY_CODE = "/findByCode?code={0}";

    private final WireMockServer wireMockServer;

    @SneakyThrows
    public void configureFindByCodeEndpoint(JsonResponseDataTable jsonResponseDataTable) {
        String code = Optional.ofNullable(jsonResponseDataTable.getKey()).map(String::valueOf).orElse(null);
        String url = MessageFormat.format(URL_FIND_BY_CODE, code);
        if (jsonResponseDataTable.isStatusOk()) {
            wireMockServer.stubFor(get(urlEqualTo(url))
                    .withName(url)
                    .willReturn(okJson(jsonResponseDataTable.getResponse())));
        }
        if (jsonResponseDataTable.isStatusBadRequest()) {
            wireMockServer.stubFor(get(urlEqualTo(url))
                    .withName(url)
                    .willReturn(aResponse().withStatus(BAD_REQUEST_400).withBody(jsonResponseDataTable.getResponse())));
        }
    }
}