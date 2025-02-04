package br.com.alc.ecommerce.checkout.infrastructure.client.stub;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.externalservices.JsonResponseDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.dto.tax.TaxResponseDto;
import br.com.alc.ecommerce.checkout.infrastructure.helper.ObjectMapperTestHelper;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Component
@RequiredArgsConstructor
public class TaxClientStub {

    private static final String URL_FIND_BY_CODE = "/findByCode?code={0}";

    private final WireMockServer wireMockServer;

    @SneakyThrows
    public void configureFindByCodeEndpoint(JsonResponseDataTable jsonResponseDataTable) {
        TaxResponseDto taxResponseDto = ObjectMapperTestHelper.generateTaxResponseDto(jsonResponseDataTable.getResponse());
        String code = Optional.ofNullable(taxResponseDto).map(TaxResponseDto::getCode).map(String::valueOf).orElse(null);
        String url = MessageFormat.format(URL_FIND_BY_CODE, code);
        if (jsonResponseDataTable.isStatusOk()) {
            wireMockServer.stubFor(get(urlEqualTo(url))
                    .withName(url)
                    .willReturn(okJson(jsonResponseDataTable.getResponse())));
        }
    }
}