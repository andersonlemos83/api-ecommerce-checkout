package br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.processsaleauthorization;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.sale.SaleRequestDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.feature.ProcessSaleAuthorizationFeature;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.StepDefs;
import io.cucumber.java.pt.Quando;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProcessSaleAuthorizationStepsDefs extends StepDefs {

    private final ProcessSaleAuthorizationFeature processSaleAuthorizationFeature;

    @Quando("^processar autorizacao venda$")
    public void processarAutorizacaoVenda() {
        SaleRequestDataTable saleRequestDataTable = transitionDataTable.buildSaleRequestDataTable();
        processSaleAuthorizationFeature.execute(saleRequestDataTable);
    }
}