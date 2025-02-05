package br.com.alc.ecommerce.checkout.core.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static br.com.alc.ecommerce.checkout.core.domain.sale.SaleStatus.PROCESSED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class EnumUtilTest {

    @Test
    void givenAnValidEnumerationWhenExecutingTheToNameMethodThenReturnTheExpectedName() {
        String nameReturned = EnumUtil.toName(PROCESSED);
        assertEquals("PROCESSED", nameReturned);
    }

    @Test
    void givenAnInvalidEnumerationWhenExecutingTheToNameMethodThenReturnNullValue() {
        assertNull(EnumUtil.toName(null));
    }
}