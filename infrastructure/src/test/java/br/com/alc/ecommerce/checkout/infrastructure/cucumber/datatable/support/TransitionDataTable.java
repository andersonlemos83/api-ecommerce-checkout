package br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.support;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.sale.CustomerDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.sale.PaymentDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.sale.SaleRequestDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.sale.ShoppingCartItemDataTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.test.web.servlet.ResultActions;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransitionDataTable implements Serializable {

    private ResultActions response;

    private SaleRequestDataTable saleRequestDataTable;
    private CustomerDataTable customerDataTable;
    private List<ShoppingCartItemDataTable> shoppingCartItemDataTableList;
    private List<PaymentDataTable> paymentDataTableList;

    public SaleRequestDataTable buildSaleRequestDataTable() {
        if (saleRequestDataTable == null) {
            saleRequestDataTable = SaleRequestDataTable.builder().build();
        }

        if (customerDataTable != null) {
            saleRequestDataTable.setCustomer(customerDataTable);
        }

        if (shoppingCartItemDataTableList != null) {
            saleRequestDataTable.setItems(shoppingCartItemDataTableList);
        }

        if (paymentDataTableList != null) {
            saleRequestDataTable.setPayments(paymentDataTableList);
        }

        return saleRequestDataTable;
    }
}