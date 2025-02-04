package br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.externalservices;

import br.com.alc.ecommerce.checkout.infrastructure.client.stub.MidClientStub;
import br.com.alc.ecommerce.checkout.infrastructure.client.stub.TaxClientStub;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.externalservices.JsonRequestDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.externalservices.JsonResponseDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.StepDefs;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.verifier.MidClientVerifier;
import io.cucumber.java.pt.E;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ExternalServicesStepDefs extends StepDefs {

    private final TaxClientStub taxClientStub;
    private final MidClientStub midClientStub;

    private final MidClientVerifier midClientVerifier;

    @E("^que existam os seguintes responses disponiveis no endpoint findByCode$")
    public void queExistamOsSeguintesResponsesDisponiveisNoEndpointFindByCode(List<JsonResponseDataTable> jsonResponseDataTableList) {
        jsonResponseDataTableList.forEach(taxClientStub::configureFindByCodeEndpoint);
    }

    @E("^que existam os seguintes responses disponiveis no endpoint authorize$")
    public void queExistamOsSeguintesResponsesDisponiveisNoEndpointAuthorize(List<JsonResponseDataTable> jsonResponseDataTableList) {
        jsonResponseDataTableList.forEach(midClientStub::configureAuthorizeEndpoint);
    }

    @E("^deveria enviar para o endpoint authorize os requests esperados$")
    public void deveriaEnviarParaOhEndpointAuthorizeOsRequestsEsperados(List<JsonRequestDataTable> jsonRequestDataTableList) {
        midClientVerifier.verifyAuthorizeEnpoint(jsonRequestDataTableList);
    }
}