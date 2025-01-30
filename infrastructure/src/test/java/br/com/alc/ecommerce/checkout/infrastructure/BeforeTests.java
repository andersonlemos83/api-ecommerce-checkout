package br.com.alc.ecommerce.checkout.infrastructure;

import br.com.alc.ecommerce.checkout.infrastructure.config.StaticContext;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses({
        StaticContext.class
})
@SuppressWarnings("squid:S2187")
@SuiteDisplayName("Suite used to initialize static context")
public class BeforeTests {

}