package br.com.alc.ecommerce.checkout.core;

import br.com.alc.ecommerce.checkout.core.port.input.impl.AllInputPortUnitTests;
import br.com.alc.ecommerce.checkout.core.service.AllServiceUnitTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses({
        AllInputPortUnitTests.class,
        AllServiceUnitTests.class
})
@SuppressWarnings("squid:S2187")
@SuiteDisplayName(" Suite that gathers all Unit tests")
public class UnitTests {

}