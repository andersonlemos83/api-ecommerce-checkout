package br.com.alc.ecommerce.checkout.infrastructure.web.controller;

import br.com.alc.ecommerce.checkout.infrastructure.adapter.input.SaleIntegratorInAdapter;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.SaleRequestDto;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.SaleResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static br.com.alc.ecommerce.checkout.infrastructure.util.ObjectMapperUtil.generateJson;
import static org.springframework.http.HttpStatus.CREATED;

@Log4j2
@RestController
@AllArgsConstructor
public class SaleController {

    private final SaleIntegratorInAdapter saleIntegratorInAdapter;

    @PostMapping(value = "authorize-sale")
    public ResponseEntity<SaleResponseDto> authorizeSale(@Valid @RequestBody SaleRequestDto saleRequestDto) {
        log.info("---> Request POST /authorize-sale: {}", generateJson(saleRequestDto));
        SaleResponseDto saleResponseDto = saleIntegratorInAdapter.execute(saleRequestDto);
        log.info("<--- Response POST /authorize-sale: {}", generateJson(saleResponseDto));
        return new ResponseEntity<>(saleResponseDto, CREATED);
    }
}