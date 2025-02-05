package br.com.alc.ecommerce.checkout.core.service;

import br.com.alc.ecommerce.checkout.core.service.service.authorize.factory.impl.AuthorizePaymentFactoryImplTest;
import br.com.alc.ecommerce.checkout.core.service.validator.impl.SaleValidatorServiceImplTest;
import br.com.alc.ecommerce.checkout.core.service.watch.impl.RealWatchServiceTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses({
        AuthorizePaymentFactoryImplTest.class,
        SaleValidatorServiceImplTest.class,
        RealWatchServiceTest.class
})
@SuppressWarnings("squid:S2187")
@SuiteDisplayName("Suite that gathers all unit tests of the Service package")
public class AllServiceUnitTests {

}