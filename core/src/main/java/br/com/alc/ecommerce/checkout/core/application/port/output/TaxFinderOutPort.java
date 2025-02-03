package br.com.alc.ecommerce.checkout.core.application.port.output;

import br.com.alc.ecommerce.checkout.core.domain.model.tax.TaxResponse;

import java.math.BigInteger;

public interface TaxFinderOutPort {

    TaxResponse execute(BigInteger code);

}