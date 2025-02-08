package br.com.alc.ecommerce.checkout.infrastructure.cucumber.verifier;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.externalservices.JsonRequestDataTable;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;

@Component
@AllArgsConstructor
public class MidClientVerifier {

    private static final String URL_AUTHORIZE = "/authorize";

    private final WireMockServer wireMockServer;

    public void verifyAuthorizeEnpoint(List<JsonRequestDataTable> expecteds) {
        List<LoggedRequest> requests = wireMockServer.findAll(postRequestedFor(urlEqualTo(URL_AUTHORIZE)));

        String expected = expecteds.stream()
                .map(JsonRequestDataTable::getRequest)
                .toList()
                .toString();

        String request = requests.stream()
                .map(LoggedRequest::getBodyAsString)
                .toList()
                .toString();

        assertEquals(expected, request);
    }
}