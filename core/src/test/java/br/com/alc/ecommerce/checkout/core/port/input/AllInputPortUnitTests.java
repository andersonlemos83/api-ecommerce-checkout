package br.com.alc.ecommerce.checkout.core.port.input;

import br.com.alc.ecommerce.checkout.core.port.input.impl.SaleIntegratorUseCaseImplTest;
import br.com.alc.ecommerce.checkout.core.port.input.impl.SaleProcessorUseCaseImplTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses({
        SaleIntegratorUseCaseImplTest.class,
        SaleProcessorUseCaseImplTest.class
})
@SuppressWarnings("squid:S2187")
@SuiteDisplayName("Suite that gathers all unit tests of the Input package.")
public class AllInputPortUnitTests {

}