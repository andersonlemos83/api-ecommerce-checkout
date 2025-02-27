package br.com.alc.ecommerce.checkout.core;

import br.com.alc.ecommerce.checkout.core.exception.AllExceptionUnitTests;
import br.com.alc.ecommerce.checkout.core.port.input.AllInputPortUnitTests;
import br.com.alc.ecommerce.checkout.core.service.AllServiceUnitTests;
import br.com.alc.ecommerce.checkout.core.util.AllUtilUnitTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses({
        AllExceptionUnitTests.class,
        AllInputPortUnitTests.class,
        AllServiceUnitTests.class,
        AllUtilUnitTests.class
})
@SuppressWarnings("squid:S2187")
@SuiteDisplayName("Suite that gathers all unit tests")
public class UnitTests {

}