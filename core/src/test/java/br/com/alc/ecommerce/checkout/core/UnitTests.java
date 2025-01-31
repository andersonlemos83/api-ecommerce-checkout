package br.com.alc.ecommerce.checkout.core;

import br.com.alc.ecommerce.checkout.core.application.port.input.impl.SaleIntegratorUseCaseImplTest;
import br.com.alc.ecommerce.checkout.core.application.service.watch.impl.RealWatchServiceTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses({
        SaleIntegratorUseCaseImplTest.class,
        RealWatchServiceTest.class
})
@SuppressWarnings("squid:S2187")
@SuiteDisplayName(" Suite that gathers all Unit tests")
public class UnitTests {

}