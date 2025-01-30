package br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.authorizesale;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.sale.SaleRequestDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.feature.AuthorizeSaleFeature;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.StepDefs;
import io.cucumber.java.pt.Quando;
import lombok.AllArgsConstructor;
import org.springframework.test.web.servlet.ResultActions;

@AllArgsConstructor
public class AuthorizeSaleStepDefs extends StepDefs {

    private final AuthorizeSaleFeature authorizeSaleFeature;

    @Quando("^autorizar venda$")
    public void autorizarVenda() throws Exception {
        SaleRequestDataTable saleRequestDataTable = transitionDataTable.buildSaleRequestDataTable();
        ResultActions response = authorizeSaleFeature.execute(saleRequestDataTable);
        transitionDataTable.setResponse(response);
    }
}