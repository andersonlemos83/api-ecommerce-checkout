package br.com.alc.ecommerce.checkout.infrastructure.adapter.output;

import br.com.alc.ecommerce.checkout.core.application.port.output.SaleAuthorizerOutPort;
import br.com.alc.ecommerce.checkout.core.domain.model.authorize.*;
import br.com.alc.ecommerce.checkout.infrastructure.client.MidClient;
import br.com.alc.ecommerce.checkout.infrastructure.dto.authorize.*;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

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
        AuthorizeSaleRequestDto authorizeSaleRequestDto = AuthorizeSaleRequestDto.builder().build();
        BeanUtils.copyProperties(authorizeSaleRequest, authorizeSaleRequestDto);
        authorizeSaleRequestDto.setCustomer(buildAuthorizeCustomerDto(authorizeSaleRequest.getCustomer()));
        authorizeSaleRequestDto.setItems(buildAuthorizeItemDtoList(authorizeSaleRequest.getItems()));
        authorizeSaleRequestDto.setPayments(buildAuthorizePaymentDtoList(authorizeSaleRequest.getPayments()));
        return authorizeSaleRequestDto;
    }

    private AuthorizeCustomerDto buildAuthorizeCustomerDto(AuthorizeCustomer authorizeCustomer) {
        AuthorizeCustomerDto authorizeCustomerDto = AuthorizeCustomerDto.builder().build();
        BeanUtils.copyProperties(authorizeCustomer, authorizeCustomerDto);
        authorizeCustomerDto.setAddress(buildAddressDto(authorizeCustomer.getAddress()));
        return authorizeCustomerDto;
    }

    private AuthorizeAddressDto buildAddressDto(AuthorizeAddress authorizeAddress) {
        AuthorizeAddressDto authorizeAddressDto = AuthorizeAddressDto.builder().build();
        BeanUtils.copyProperties(authorizeAddress, authorizeAddressDto);
        return authorizeAddressDto;
    }

    private List<AuthorizeItemDto> buildAuthorizeItemDtoList(List<AuthorizeItem> authorizeItemList) {
        return authorizeItemList.stream().map(this::buildAuthorizeItemDto).toList();
    }

    private AuthorizeItemDto buildAuthorizeItemDto(AuthorizeItem authorizeItem) {
        AuthorizeItemDto authorizeItemDto = AuthorizeItemDto.builder().build();
        BeanUtils.copyProperties(authorizeItem, authorizeItemDto);
        return authorizeItemDto;
    }

    private List<AuthorizePaymentDto> buildAuthorizePaymentDtoList(List<AuthorizePayment> authorizePaymentList) {
        return authorizePaymentList.stream().map(this::buildAuthorizePaymentDto).toList();
    }

    private AuthorizePaymentDto buildAuthorizePaymentDto(AuthorizePayment authorizePayment) {
        AuthorizePaymentDto authorizePaymentDto = AuthorizePaymentDto.builder().build();
        BeanUtils.copyProperties(authorizePayment, authorizePaymentDto);
        return authorizePaymentDto;
    }

    private AuthorizeSaleResponse buildAuthorizeSaleResponse(AuthorizeSaleResponseDto authorizeSaleResponseDto) {
        AuthorizeSaleResponse authorizeSaleResponse = AuthorizeSaleResponse.builder().build();
        BeanUtils.copyProperties(authorizeSaleResponseDto, authorizeSaleResponse);
        return authorizeSaleResponse;
    }
}