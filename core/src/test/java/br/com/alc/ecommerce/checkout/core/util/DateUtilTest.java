package br.com.alc.ecommerce.checkout.core.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class DateUtilTest {

    @Test
    public void givenAnValidDateWhenExecutingTheFormatMethodThenReturnTheExpectedFormattedDate() {
        String formattedDate = DateUtil.format(getLocalDateTime());
        assertEquals("2025-01-30T13:45:01", formattedDate);
    }

    @Test
    public void givenAnValidDateWhenExecutingTheCreateSequenceMethodThenReturnTheExpectedSequence() {
        String returnedSequence = DateUtil.createSequence(getLocalDateTime());
        assertEquals("20250130134501", returnedSequence);
    }

    private LocalDateTime getLocalDateTime() {
        return LocalDateTime.now()
                .withYear(2025)
                .withMonth(01)
                .withDayOfMonth(30)
                .withHour(13)
                .withMinute(45)
                .withSecond(01)
                .withNano(0);
    }
}