package br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.context;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.context.SaleOrderContext;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.order.SaleOrderDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.StepDefs;
import io.cucumber.java.pt.E;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ContextStepDefs extends StepDefs {

    private final SaleOrderContext saleOrderContext;

    @E("^que existam as Sale Order cadastradas$")
    public void queExistamAsSaleOrderCadastradas(List<SaleOrderDataTable> orderDataTableList) {
        saleOrderContext.insert(orderDataTableList);
    }
}