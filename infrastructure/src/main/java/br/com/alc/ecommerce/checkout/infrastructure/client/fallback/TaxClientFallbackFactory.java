package br.com.alc.ecommerce.checkout.infrastructure.client.fallback;

import br.com.alc.ecommerce.checkout.core.exception.DefaultOutPortException;
import br.com.alc.ecommerce.checkout.infrastructure.client.TaxClient;
import feign.FeignException;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;

@Log4j2
@Component
@SuppressWarnings("squid:S7091") // Circular dependencies between classes across packages
public class TaxClientFallbackFactory implements FallbackFactory<TaxClient> {

    @Override
    public TaxClient create(Throwable throwable) {
        log.error("Error in the TaxClientFallbackFactory: {}", getMessage(throwable), throwable);
        DefaultOutPortException defaultOutPortException = Optional.ofNullable(throwable)
                .filter(FeignException.BadRequest.class::isInstance)
                .map(FeignException.BadRequest.class::cast)
                .map(badRequest -> new DefaultOutPortException(badRequest.contentUTF8(), badRequest.getCause()))
                .orElse(new DefaultOutPortException(throwable.getMessage(), throwable.getCause()));
        throw defaultOutPortException;
    }
}