package br.com.alc.ecommerce.checkout.infrastructure.cucumber.verifier;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.order.SaleOrderDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.helper.repository.SaleOrderRepositoryHelper;
import br.com.alc.ecommerce.checkout.infrastructure.persistence.entity.SaleOrderEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Comparator.*;
import static org.junit.Assert.assertEquals;

@Component
@AllArgsConstructor
public class SaleOrderVerifier {

    private final SaleOrderRepositoryHelper saleOrderRepositoryHelper;

    public void verify(List<SaleOrderDataTable> expecteds) {
        expecteds = expecteds.stream().sorted(comparing(SaleOrderDataTable::getOrderNumber, nullsLast(naturalOrder()))
                .thenComparing(SaleOrderDataTable::getCreatedDate, nullsLast(naturalOrder()))).toList();

        List<SaleOrderEntity> returneds = saleOrderRepositoryHelper.findAll()
                .stream().sorted(comparing(SaleOrderEntity::getOrderNumber, nullsLast(naturalOrder()))
                        .thenComparing(SaleOrderEntity::getCreatedDate, nullsLast(naturalOrder()))).toList();

        assertEquals("Should return the expected number of Sale Orders.", expecteds.size(), returneds.size());

        for (int i = 0; i < expecteds.size(); i++) {
            SaleOrderDataTable esperado = expecteds.get(i);
            SaleOrderEntity returned = returneds.get(i);

            verify(esperado, returned);
        }
    }

    private void verify(SaleOrderDataTable expected, SaleOrderEntity returned) {
        assertEquals(expected.getId(), returned.getId());
        assertEquals(expected.getChannelCode(), returned.getChannelCode());
        assertEquals(expected.getCompanyCode(), returned.getCompanyCode());
        assertEquals(expected.getStoreCode(), returned.getStoreCode());
        assertEquals(expected.getPos(), returned.getPos());
        assertEquals(expected.getOrderNumber(), returned.getOrderNumber());
        assertEquals(expected.getTotalValue(), returned.getTotalValue());
        assertEquals(expected.getFreightValue(), returned.getFreightValue());
        assertEquals(expected.getInvoiceKey(), returned.getInvoiceKey());
        assertEquals(expected.getInvoiceNumber(), returned.getInvoiceNumber());
        assertEquals(expected.getIssuanceDate(), returned.getIssuanceDate());
        assertEquals(expected.getInvoiceBase64(), returned.getInvoiceBase64());
        assertEquals(expected.getStatus(), returned.getStatus());
        assertEquals(expected.getErrorReason(), returned.getErrorReason());
        assertEquals(expected.getCreatedDate(), returned.getCreatedDate());
        assertEquals(expected.getUpdatedDate(), returned.getUpdatedDate());
    }
}