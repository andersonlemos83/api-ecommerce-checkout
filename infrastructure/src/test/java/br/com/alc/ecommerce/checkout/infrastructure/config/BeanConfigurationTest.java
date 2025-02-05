package br.com.alc.ecommerce.checkout.infrastructure.config;

import br.com.alc.ecommerce.checkout.core.service.watch.WatchService;
import br.com.alc.ecommerce.checkout.core.service.watch.impl.RealWatchService;
import br.com.alc.ecommerce.checkout.infrastructure.configuration.BeanConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertTrue;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class BeanConfigurationTest {

    @Test
    void whenExecutingWatchServiceMethodShouldReturnAnInstanceOfRealWatchService() {
        BeanConfiguration beanConfiguration = new BeanConfiguration();
        WatchService watchService = beanConfiguration.watchService();
        assertTrue("", watchService instanceof RealWatchService);
    }
}