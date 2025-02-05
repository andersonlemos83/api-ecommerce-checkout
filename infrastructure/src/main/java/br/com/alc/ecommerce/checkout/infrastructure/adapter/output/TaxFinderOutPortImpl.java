package br.com.alc.ecommerce.checkout.infrastructure.adapter.output;

import br.com.alc.ecommerce.checkout.core.domain.tax.TaxResponse;
import br.com.alc.ecommerce.checkout.core.exception.DefaultOutPortException;
import br.com.alc.ecommerce.checkout.core.port.output.TaxFinderOutPort;
import br.com.alc.ecommerce.checkout.infrastructure.client.TaxClient;
import br.com.alc.ecommerce.checkout.infrastructure.dto.tax.TaxResponseDto;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

import static br.com.alc.ecommerce.checkout.infrastructure.util.ConstantesUtil.TAX_FINDER_CACHE;
import static br.com.alc.ecommerce.checkout.infrastructure.util.ObjectMapperUtil.generateJson;

@Log4j2
@Component
@AllArgsConstructor
public class TaxFinderOutPortImpl implements TaxFinderOutPort {

    private final RetryTemplate retryTemplate;
    private final TaxClient taxClient;
    private final ModelMapper modelMapper;

    @Override
    @Cacheable(cacheNames = TAX_FINDER_CACHE, key = "#code", unless = "#result == null")
    public TaxResponse execute(BigInteger code) {
        log.debug("---> TaxFinderOutPortImpl: {}", generateJson(code));
        try {
            return retryTemplate.execute(callback -> {
                log.info("---> Request GET /findByCode {}: {}", callback.getRetryCount() + 1, code);
                TaxResponseDto taxResponseDto = taxClient.findByCode(code);
                log.info("<--- Response GET /findByCode: {}", generateJson(taxResponseDto));
                log.debug("Save cache {}:{} - {}", TAX_FINDER_CACHE, code, generateJson(taxResponseDto));
                TaxResponse taxResponse = modelMapper.map(taxResponseDto, TaxResponse.class);
                log.debug("<--- TaxFinderOutPortImpl: {}", generateJson(taxResponse));
                return taxResponse;
            });
        } catch (FeignException exception) {
            throw new DefaultOutPortException(exception.contentUTF8(), exception.getCause());
        }
    }
}