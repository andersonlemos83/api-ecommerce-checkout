package br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.sale;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.sale.CustomerDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.sale.PaymentDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.sale.SaleRequestDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.sale.ShoppingCartItemDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.StepDefs;
import io.cucumber.java.pt.E;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class SaleRequestStepDefs extends StepDefs {

    @E("^que seja informado os dados de Sale Request$")
    public void queSejaInformadoOsDadosDeSaleRequest(List<SaleRequestDataTable> saleRequestDataTableList) {
        saleRequestDataTableList.forEach(transitionDataTable::setSaleRequestDataTable);
    }

    @E("^que seja informado os dados de Customer$")
    public void queSejaInformadoOsDadosDeCustomer$(List<CustomerDataTable> customerDataTableList) {
        customerDataTableList.forEach(transitionDataTable::setCustomerDataTable);
    }

    @E("^que seja informado os dados de Shopping Cart Item$")
    public void queSejaInformadoOsDadosDeShoppingCartItem(List<ShoppingCartItemDataTable> shoppingCartItemDataTableList) {
        transitionDataTable.setShoppingCartItemDataTableList(shoppingCartItemDataTableList);
    }

    @E("^que seja informado os dados de Payment$")
    public void queSejaInformadoOsDadosDePayment$(List<PaymentDataTable> paymentDataTableList) {
        transitionDataTable.setPaymentDataTableList(paymentDataTableList);
    }
}