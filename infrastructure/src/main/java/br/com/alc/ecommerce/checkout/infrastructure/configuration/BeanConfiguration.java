package br.com.alc.ecommerce.checkout.infrastructure.configuration;

import br.com.alc.ecommerce.checkout.core.port.input.SaleIntegratorUseCase;
import br.com.alc.ecommerce.checkout.core.port.input.SaleProcessorUseCase;
import br.com.alc.ecommerce.checkout.core.port.input.impl.SaleIntegratorUseCaseImpl;
import br.com.alc.ecommerce.checkout.core.port.input.impl.SaleProcessorUseCaseImpl;
import br.com.alc.ecommerce.checkout.core.port.output.*;
import br.com.alc.ecommerce.checkout.core.service.authorize.SaleAuthorizerService;
import br.com.alc.ecommerce.checkout.core.service.authorize.factory.AuthorizePaymentFactory;
import br.com.alc.ecommerce.checkout.core.service.authorize.factory.impl.AuthorizePaymentFactoryImpl;
import br.com.alc.ecommerce.checkout.core.service.authorize.impl.SaleAuthorizerServiceImpl;
import br.com.alc.ecommerce.checkout.core.service.validator.SaleValidatorService;
import br.com.alc.ecommerce.checkout.core.service.validator.impl.SaleValidatorServiceImpl;
import br.com.alc.ecommerce.checkout.core.service.watch.WatchService;
import br.com.alc.ecommerce.checkout.core.service.watch.impl.RealWatchService;
import br.com.alc.ecommerce.checkout.infrastructure.EcommerceCheckoutInfrastructureApplication;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@ComponentScan(basePackageClasses = EcommerceCheckoutInfrastructureApplication.class)
public class BeanConfiguration {

    @Lazy
    @Bean
    public SaleIntegratorUseCase saleIntegratorUseCase(SaleIntegratorOutPort saleIntegratorOutPort,
                                                       WatchService watchService) {
        return new SaleIntegratorUseCaseImpl(saleIntegratorOutPort, watchService);
    }

    @Lazy
    @Bean
    public WatchService watchService() {
        return new RealWatchService();
    }

    @Lazy
    @Bean
    public SaleProcessorUseCase saleProcessorUseCase(MostRecentSaleOrderFinderOutPort mostRecentSaleOrderFinderOutPort,
                                                     SaleValidatorService saleValidatorService,
                                                     SaleOrderInserterOutPort saleOrderInserterOutPort,
                                                     SaleAuthorizerService saleAuthorizerService,
                                                     SaleCallbackIntegratorOutPort saleCallbackIntegratorOutPort,
                                                     WatchService watchService) {
        return new SaleProcessorUseCaseImpl(mostRecentSaleOrderFinderOutPort, saleValidatorService, saleOrderInserterOutPort,
                saleAuthorizerService, saleCallbackIntegratorOutPort, watchService);
    }

    @Lazy
    @Bean
    public SaleValidatorService saleValidatorService() {
        return new SaleValidatorServiceImpl();
    }

    @Lazy
    @Bean
    public SaleAuthorizerService saleAuthorizerService(SaleAuthorizerOutPort saleAuthorizerOutPort,
                                                       TaxFinderOutPort taxFinderOutPort,
                                                       AuthorizePaymentFactory authorizePaymentFactory,
                                                       WatchService watchService) {
        return new SaleAuthorizerServiceImpl(saleAuthorizerOutPort, taxFinderOutPort, authorizePaymentFactory, watchService);
    }

    @Lazy
    @Bean
    public AuthorizePaymentFactory authorizePaymentFactory() {
        return new AuthorizePaymentFactoryImpl();
    }

    @Lazy
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}