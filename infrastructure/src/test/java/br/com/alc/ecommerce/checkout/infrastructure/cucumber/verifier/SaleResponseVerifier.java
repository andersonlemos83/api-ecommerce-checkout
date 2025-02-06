package br.com.alc.ecommerce.checkout.infrastructure.cucumber.verifier;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.sale.SaleResponseDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.SaleResponseDto;
import br.com.alc.ecommerce.checkout.infrastructure.helper.util.ObjectMapperHelper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class SaleResponseVerifier {

    public void verify(List<SaleResponseDataTable> expected, ResultActions response) {
        expected.forEach(e -> verify(e, response));
    }

    @SneakyThrows
    private void verify(SaleResponseDataTable expected, ResultActions response) {
        response.andExpect(status().isCreated());

        SaleResponseDto returned = ObjectMapperHelper.generateSaleResponseDto(response);

        assertEquals(expected.getStatus(), returned.getStatus());
        assertEquals(expected.getDate(), returned.getDate());
    }
}