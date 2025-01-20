package br.com.alc.ecommerce.checkout.infrastructure.web.controller;

import br.com.alc.ecommerce.checkout.infrastructure.adapter.input.SaleIntegratorInAdapter;
import br.com.alc.ecommerce.checkout.infrastructure.dto.SaleRequestDto;
import br.com.alc.ecommerce.checkout.infrastructure.dto.SaleResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static br.com.alc.ecommerce.checkout.core.application.util.ObjectMapperUtils.generateJson;
import static org.springframework.http.HttpStatus.OK;

@Log4j2
@RestController
@AllArgsConstructor
public class SaleController {

    private final SaleIntegratorInAdapter saleIntegratorInAdapter;

    @PostMapping(value = "authorizeSale")
    public ResponseEntity<SaleResponseDto> authorizeSale(@Valid @RequestBody SaleRequestDto saleRequestDto) {
        log.info("---> Request POST /authorizeSale: {}", generateJson(saleRequestDto));
        SaleResponseDto saleResponseDto = saleIntegratorInAdapter.execute(saleRequestDto);
        log.info("<--- Response POST /authorizeSale: {}", generateJson(saleResponseDto));
        return new ResponseEntity<>(saleResponseDto, OK);
    }
}