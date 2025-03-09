package br.com.alc.ecommerce.checkout.infrastructure.client;

import br.com.alc.ecommerce.checkout.infrastructure.client.fallback.TaxClientFallbackFactory;
import br.com.alc.ecommerce.checkout.infrastructure.dto.tax.TaxResponseDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SuppressWarnings("squid:S7091") // Circular dependencies between classes across packages
@FeignClient(name = "api-tax",
        url = "${client.tax.url}",
        fallbackFactory = TaxClientFallbackFactory.class)
public interface TaxClient {

    @Headers("Content-Type: " + APPLICATION_JSON_VALUE)
    @GetMapping(value = "/findByCode", consumes = APPLICATION_JSON_VALUE)
    TaxResponseDto findByCode(@RequestParam("code") BigInteger code);

}