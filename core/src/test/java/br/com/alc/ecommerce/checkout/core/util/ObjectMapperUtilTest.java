package br.com.alc.ecommerce.checkout.core.util;

import br.com.alc.ecommerce.checkout.core.domain.sale.ShoppingCartItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class ObjectMapperUtilTest {

    @Test
    void givenAnSerializableObjectWhenExecutingTheGenerateJsonMethodThenShouldReturnAnSerializedObject() {
        ShoppingCartItem shoppingCartItem = ShoppingCartItem.builder().code(BigInteger.valueOf(100231933559L)).quantity(1).value(BigDecimal.valueOf(7.09)).build();
        String jsonReturned = ObjectMapperUtil.generateJson(shoppingCartItem);
        assertEquals("{\"code\":100231933559,\"quantity\":1,\"value\":7.09,\"totalItemValue\":7.09}", jsonReturned);
    }

    @Test
    void givenAnNoSerializableObjectWhenExecutingTheGenerateJsonMethodThenShouldReturnAnNonSerializedObject() {
        Object nonSerializableObject = new Object();
        String jsonReturned = ObjectMapperUtil.generateJson(nonSerializableObject);
        assertEquals(nonSerializableObject.toString(), jsonReturned);
    }
}