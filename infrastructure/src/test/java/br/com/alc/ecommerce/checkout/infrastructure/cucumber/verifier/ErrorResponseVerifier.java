package br.com.alc.ecommerce.checkout.infrastructure.cucumber.verifier;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.error.ErrorResponseDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.dto.error.ErrorResponseDto;
import br.com.alc.ecommerce.checkout.infrastructure.helper.util.ObjectMapperHelper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Component
public class ErrorResponseVerifier {

    public void verify(List<ErrorResponseDataTable> expected, ResultActions resultActions) {
        expected.forEach(e -> verify(e, resultActions));
    }

    @SneakyThrows
    private void verify(ErrorResponseDataTable expected, ResultActions resultActions) {
        ErrorResponseDto response = ObjectMapperHelper.generateErrorResponseDto(resultActions);

        assertEquals(expected.getHttpStatus(), response.getHttpStatus());
        assertEquals(expected.getMessage(), response.getMessage());
    }
}