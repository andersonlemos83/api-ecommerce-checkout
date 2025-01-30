package br.com.alc.ecommerce.checkout.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.TimeZone;

@ExtendWith(SpringExtension.class)
public class StaticContext {

    @Test
    public void initializeStaticContext() {
        System.out.println("TimeZone Default: " + TimeZone.getDefault());
        TimeZone.setDefault(TimeZone.getTimeZone("America/Fortaleza"));
        System.out.println("TimeZone Atualizado: " + TimeZone.getDefault());
    }
}