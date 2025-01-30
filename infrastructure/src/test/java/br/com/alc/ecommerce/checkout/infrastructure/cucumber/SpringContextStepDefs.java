package br.com.alc.ecommerce.checkout.infrastructure.cucumber;

import br.com.alc.ecommerce.checkout.infrastructure.EcommerceCheckoutInfrastructureApplication;
import br.com.alc.ecommerce.checkout.infrastructure.config.EcommerceCheckoutInfrastructureConfigurationTest;
import br.com.alc.ecommerce.checkout.infrastructure.config.RabbitConfigurationTest;
import br.com.alc.ecommerce.checkout.infrastructure.config.WireMockConfigurationTest;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.StepDefs;
import br.com.alc.ecommerce.checkout.infrastructure.helper.ObjectMapperTestHelper;
import br.com.alc.ecommerce.checkout.infrastructure.testcontainers.ContainerManager;
import br.com.alc.ecommerce.checkout.infrastructure.testcontainers.factory.impl.ContainerFactoryImpl;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.*;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

@WebAppConfiguration
@ActiveProfiles("test")
@CucumberContextConfiguration
@AutoConfigureMockMvc(printOnlyOnFailure = false) // Set false for debug
@SpringBootTest(classes = EcommerceCheckoutInfrastructureApplication.class)
@ContextConfiguration(classes = {EcommerceCheckoutInfrastructureApplication.class, EcommerceCheckoutInfrastructureConfigurationTest.class, RabbitConfigurationTest.class, WireMockConfigurationTest.class})
public class SpringContextStepDefs extends StepDefs {

    @DefaultParameterTransformer
    @DefaultDataTableEntryTransformer
    @DefaultDataTableCellTransformer
    public Object defaultTransformer(Object value, Type type) {
        final ObjectMapper objectMapper = ObjectMapperTestHelper.getInstance().copy();
        final Object handledValue = handleEmptyValues(value);
        final JavaType javaType = objectMapper.constructType(type);
        return objectMapper.convertValue(handledValue, javaType);
    }

    private Object handleEmptyValues(Object value) {
        if (value instanceof Map) {
            Map handledMap = new LinkedHashMap();
            for (Object o : ((Map) value).entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                Object handledValue = handleEmptyValue(entry.getValue());
                entry.setValue(handledValue);
                handledMap.put(entry.getKey(), entry.getValue());
            }
            return handledMap;
        }

        return handleEmptyValue(value);
    }

    private Object handleEmptyValue(Object o) {
        if (o instanceof String && "<empty>".equalsIgnoreCase((String) o)) {
            return "";
        }
        return o;
    }

    @Before
    public void initializeContext() {
        super.initializeContext();
    }

    @BeforeAll
    public static void initializeContainers() {
        new ContainerFactoryImpl().getInstances().forEach(ContainerManager::start);
    }
}