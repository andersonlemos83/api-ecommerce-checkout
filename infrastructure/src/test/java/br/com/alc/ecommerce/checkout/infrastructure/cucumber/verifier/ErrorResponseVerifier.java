package br.com.alc.ecommerce.checkout.infrastructure.cucumber.verifier;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.error.ErrorResponseDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.dto.error.ErrorResponseDto;
import br.com.alc.ecommerce.checkout.infrastructure.helper.ObjectMapperTestHelper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Component
public class ErrorResponseVerifier {

    public void verify(List<ErrorResponseDataTable> expected, ResultActions response) {
        expected.forEach(e -> verify(e, response));
    }

    @SneakyThrows
    private void verify(ErrorResponseDataTable expected, ResultActions response) {
        ErrorResponseDto returned = ObjectMapperTestHelper.generateErrorResponseDto(response);

        assertEquals(expected.getHttpStatus(), returned.getHttpStatus());
        assertEquals(expected.getMessage(), returned.getMessage());
    }
}