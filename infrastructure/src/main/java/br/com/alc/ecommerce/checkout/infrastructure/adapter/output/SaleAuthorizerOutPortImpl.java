package br.com.alc.ecommerce.checkout.infrastructure.adapter.output;

import br.com.alc.ecommerce.checkout.core.domain.authorize.AuthorizeSaleRequest;
import br.com.alc.ecommerce.checkout.core.domain.authorize.AuthorizeSaleResponse;
import br.com.alc.ecommerce.checkout.core.port.output.SaleAuthorizerOutPort;
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
    private final ModelMapper modelMapper;

    @Override
    public AuthorizeSaleResponse execute(AuthorizeSaleRequest authorizeSaleRequest) {
        AuthorizeSaleRequestDto authorizeSaleRequestDto = modelMapper.map(authorizeSaleRequest, AuthorizeSaleRequestDto.class);
        log.info("---> Request /authorize: {}", generateJson(authorizeSaleRequestDto));
        AuthorizeSaleResponseDto authorizeSaleResponseDto = midClient.authorize(authorizeSaleRequestDto);
        AuthorizeSaleResponse authorizeSaleResponse = modelMapper.map(authorizeSaleResponseDto, AuthorizeSaleResponse.class);
        log.info("<--- Response /authorize: {}", generateJson(authorizeSaleResponse));
        return authorizeSaleResponse;
    }
}