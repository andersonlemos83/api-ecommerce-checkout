package br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.saleorder;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.order.SaleOrderDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.StepDefs;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.verifier.SaleOrderVerifier;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.Collections.emptyList;

@AllArgsConstructor
public class SaleOrderStepDefs extends StepDefs {

    private final SaleOrderVerifier saleOrderVerifier;

    @Entao("^deveria existir as seguintes Sale Order na base$")
    public void deveriaExistirAsSeguintesSaleOrderNaBase(List<SaleOrderDataTable> saleOrderDataTableList) {
        saleOrderVerifier.verify(saleOrderDataTableList);
    }

    @E("^nao deveria existir nenhuma Sale Order na base$")
    public void naoDeveriaExistirNenhumaSaleOrderNaBase() {
        saleOrderVerifier.verify(emptyList());
    }
}