package br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.error;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.error.ErrorResponseDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.StepDefs;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.verifier.ErrorResponseVerifier;
import io.cucumber.java.pt.Entao;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ErrorResponseStepDefs extends StepDefs {

    private final ErrorResponseVerifier errorResponseVerifier;

    @Entao("^deveria receber os dados de Error Response$")
    public void deveriaReceberOsDadosDeErrorResponse(List<ErrorResponseDataTable> errorResponseDataTableList) {
        errorResponseVerifier.verify(errorResponseDataTableList, transitionDataTable.getResponse());
    }
}