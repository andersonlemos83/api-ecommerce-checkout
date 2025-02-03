package br.com.alc.ecommerce.checkout.infrastructure.client;

import br.com.alc.ecommerce.checkout.infrastructure.dto.authorize.AuthorizeSaleRequestDto;
import br.com.alc.ecommerce.checkout.infrastructure.dto.authorize.AuthorizeSaleResponseDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "api-mid-client", url = "${client.mid-client.url}")
public interface MidClient {

    @Headers("Content-Type: " + APPLICATION_JSON_VALUE)
    @PostMapping(value = "/authorize", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    AuthorizeSaleResponseDto authorize(AuthorizeSaleRequestDto authorizeSaleRequestDto);

}