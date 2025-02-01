package br.com.alc.ecommerce.checkout.infrastructure.util;

import br.com.alc.ecommerce.checkout.infrastructure.dto.error.ErrorResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class ObjectMapperUtilTest {

    @Test
    void whenExecutingGenerateJsonMethodGivenThatTheObjectIsSerializableShouldReturnASerializedObject() {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder().httpStatus(OK).message("O campo totalAmount não foi informado.").build();
        String jsonReturned = ObjectMapperUtil.generateJson(errorResponseDto);
        assertEquals("{\"httpStatus\":\"OK\",\"message\":\"O campo totalAmount não foi informado.\"}", jsonReturned);
    }

    @Test
    void whenExecutingGenerateJsonMethodGivenThatTheObjectIsNotSerializableShouldReturnANonSerializedObject() {
        Object nonSerializableObject = new Object();
        String jsonReturned = ObjectMapperUtil.generateJson(nonSerializableObject);
        assertEquals(nonSerializableObject.toString(), jsonReturned);
    }
}