package br.com.alc.ecommerce.checkout.infrastructure.adapter.output;

import br.com.alc.ecommerce.checkout.core.application.port.output.SaleAuthorizerOutPort;
import br.com.alc.ecommerce.checkout.core.domain.model.authorize.AuthorizeSaleRequest;
import br.com.alc.ecommerce.checkout.core.domain.model.authorize.AuthorizeSaleResponse;
import br.com.alc.ecommerce.checkout.infrastructure.client.MidClient;
import br.com.alc.ecommerce.checkout.infrastructure.dto.authorize.AuthorizeSaleRequestDto;
import br.com.alc.ecommerce.checkout.infrastructure.dto.authorize.AuthorizeSaleResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import static br.com.alc.ecommerce.checkout.infrastructure.util.ObjectMapperUtil.generateJson;

@Log4j2
@Component
@AllArgsConstructor
public class SaleAuthorizerOutPortImpl implements SaleAuthorizerOutPort {

    private final MidClient midClient;

    @Override
    public AuthorizeSaleResponse execute(AuthorizeSaleRequest authorizeSaleRequest) {
        AuthorizeSaleRequestDto authorizeSaleRequestDto = buildAuthorizeSaleRequestDto(authorizeSaleRequest);
        log.info("---> Request /authorize: {}", generateJson(authorizeSaleRequestDto));
        AuthorizeSaleResponseDto authorizeSaleResponseDto = midClient.authorize(authorizeSaleRequestDto);
        AuthorizeSaleResponse authorizeSaleResponse = buildAuthorizeSaleResponse(authorizeSaleResponseDto);
        log.info("<--- Response /authorize: {}", generateJson(authorizeSaleResponse));
        return authorizeSaleResponse;
    }

    private AuthorizeSaleRequestDto buildAuthorizeSaleRequestDto(AuthorizeSaleRequest authorizeSaleRequest) {
        return new ModelMapper().map(authorizeSaleRequest, AuthorizeSaleRequestDto.class);
    }

    private AuthorizeSaleResponse buildAuthorizeSaleResponse(AuthorizeSaleResponseDto authorizeSaleResponseDto) {
        return new ModelMapper().map(authorizeSaleResponseDto, AuthorizeSaleResponse.class);
    }
}