package br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.sale;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.sale.SaleResponseDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.StepDefs;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.verifier.SaleResponseVerifier;
import io.cucumber.java.pt.Entao;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class SaleResponseStepDefs extends StepDefs {

    private final SaleResponseVerifier saleResponseVerifier;

    @Entao("^deveria receber os dados de Sale Response$")
    public void deveriaReceberOsDadosDeVendaResponse(List<SaleResponseDataTable> saleResponseDataTableList) throws Exception {
        saleResponseVerifier.verify(saleResponseDataTableList, transitionDataTable.getResponse());
    }
}