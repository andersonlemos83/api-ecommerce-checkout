package br.com.alc.ecommerce.checkout.core.util;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses({
        DateUtilTest.class,
        EnumUtilTest.class,
        ObjectMapperUtilTest.class
})
@SuppressWarnings("squid:S2187")
@SuiteDisplayName("Suite that gathers all unit tests of the Util package")
public class AllUtilUnitTests {

}