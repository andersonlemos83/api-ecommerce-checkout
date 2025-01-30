package br.com.alc.ecommerce.checkout.infrastructure.cucumber.feature;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.sale.SaleRequestDataTable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static br.com.alc.ecommerce.checkout.infrastructure.helper.ObjectMapperTestHelper.generateJson;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
@AllArgsConstructor
public class AuthorizeSaleFeature {

    private final MockMvc mockMvc;

    public ResultActions execute(SaleRequestDataTable saleRequestDataTable) throws Exception {
        return mockMvc.perform(post("/authorize-sale")
                .content(generateJson(saleRequestDataTable))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON));
    }
}