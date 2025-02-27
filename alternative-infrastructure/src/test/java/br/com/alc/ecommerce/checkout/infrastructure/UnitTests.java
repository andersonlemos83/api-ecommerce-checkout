package br.com.alc.ecommerce.checkout.infrastructure;

import br.com.alc.ecommerce.checkout.infrastructure.config.BeanConfigurationTest;
import br.com.alc.ecommerce.checkout.infrastructure.messaging.producer.impl.MessagingProducerImplTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses({
        BeanConfigurationTest.class,
        MessagingProducerImplTest.class
})
@SuppressWarnings("squid:S2187")
@SuiteDisplayName("Suite that gathers all Unit tests")
public class UnitTests {

}