package br.com.alc.ecommerce.checkout.core.port.output;

import br.com.alc.ecommerce.checkout.core.domain.tax.TaxResponse;

import java.math.BigInteger;

public interface TaxFinderOutPort {

    TaxResponse execute(BigInteger code);

}