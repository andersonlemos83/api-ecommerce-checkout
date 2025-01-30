package br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class BigDecimalDeserializer extends JsonDeserializer<BigDecimal> {

    @Override
    public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            if (jsonParser == null || jsonParser.getText() == null || "".equalsIgnoreCase(jsonParser.getText())) {
                return null;
            }
            final Locale locale = new Locale("pt", "BR");
            final Number number = NumberFormat.getNumberInstance(locale).parse(jsonParser.getText());
            return BigDecimal.valueOf(number.doubleValue());
        } catch (ParseException exception) {
            throw new IOException(exception);
        }
    }
}