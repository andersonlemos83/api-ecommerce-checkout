package br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.processsaleauthorization;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.sale.SaleRequestDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.feature.ProcessSaleAuthorizationFeature;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.StepDefs;
import io.cucumber.java.pt.Quando;
import lombok.AllArgsConstructor;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@SuppressWarnings("squid:S2925") // "Thread.sleep" should not be used in tests
public class ProcessSaleAuthorizationStepsDefs extends StepDefs {

    private final ProcessSaleAuthorizationFeature processSaleAuthorizationFeature;

    @Quando("^processar autorizacao venda$")
    public void processarAutorizacaoVenda() {
        SaleRequestDataTable saleRequestDataTable = transitionDataTable.buildSaleRequestDataTable();
        processSaleAuthorizationFeature.execute(saleRequestDataTable);
    }

    @Quando("^processar autorizacao venda com espera de \"([^\"]*)\" milisegundos$")
    public void processarAutorizacaoVendaComEspera(long timeout) throws Exception {
        processarAutorizacaoVenda();
        TimeUnit.MILLISECONDS.sleep(timeout);
    }
}