package br.com.alc.ecommerce.checkout.infrastructure;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.RunCucumberTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses({
        BeforeTests.class, // Don't change the order!
        UnitTests.class,
        RunCucumberTest.class
})
@SuppressWarnings("squid:S2187")
@SuiteDisplayName("Suite that gathers all Unit and Acceptance tests")
public class AllTests {

}