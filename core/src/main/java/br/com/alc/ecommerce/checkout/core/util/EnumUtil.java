package br.com.alc.ecommerce.checkout.core.util;

import java.util.Optional;

public final class EnumUtil {

    private EnumUtil() {
    }

    public static String toName(Enum<?> enumeration) {
        return Optional.ofNullable(enumeration)
                .map(Enum::name)
                .orElse(null);
    }
}