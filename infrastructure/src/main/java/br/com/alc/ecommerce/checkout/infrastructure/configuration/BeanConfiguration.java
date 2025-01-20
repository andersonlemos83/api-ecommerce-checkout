package br.com.alc.ecommerce.checkout.infrastructure.configuration;

import br.com.alc.ecommerce.checkout.core.application.port.input.SaleIntegratorUseCase;
import br.com.alc.ecommerce.checkout.core.application.port.input.impl.SaleIntegratorUseCaseImpl;
import br.com.alc.ecommerce.checkout.core.application.port.output.SaleIntegratorOutPort;
import br.com.alc.ecommerce.checkout.core.application.service.watch.WatchService;
import br.com.alc.ecommerce.checkout.core.application.service.watch.impl.RealWatchService;
import br.com.alc.ecommerce.checkout.infrastructure.EcommerceCheckoutInfrastructureApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = EcommerceCheckoutInfrastructureApplication.class)
public class BeanConfiguration {

    @Bean
    public SaleIntegratorUseCase saleIntegratorUseCase(SaleIntegratorOutPort saleIntegratorOutPort,
                                                       WatchService watchService) {
        return new SaleIntegratorUseCaseImpl(saleIntegratorOutPort, watchService);
    }

    @Bean
    public WatchService watchService() {
        return new RealWatchService();
    }
}