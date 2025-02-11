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
import br.com.alc.ecommerce.checkout.infrastructure.EcommerceCheckoutAlternativeInfrastructureApplication;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = EcommerceCheckoutAlternativeInfrastructureApplication.class)
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

    @Bean
    public SaleProcessorUseCase saleProcessorUseCase(MostRecentSaleOrderFinderOutPort mostRecentSaleOrderFinderOutPort,
                                                     SaleValidatorService saleValidatorService,
                                                     SaleOrderInserterOutPort saleOrderInserterOutPort,
                                                     SaleAuthorizerService saleAuthorizerService,
                                                     SaleCallbackIntegrateOutPort saleCallbackIntegrateOutPort,
                                                     WatchService watchService) {
        return new SaleProcessorUseCaseImpl(mostRecentSaleOrderFinderOutPort, saleValidatorService, saleOrderInserterOutPort,
                saleAuthorizerService, saleCallbackIntegrateOutPort, watchService);
    }

    @Bean
    public SaleValidatorService saleValidatorService() {
        return new SaleValidatorServiceImpl();
    }

    @Bean
    public SaleAuthorizerService saleAuthorizerService(SaleAuthorizerOutPort saleAuthorizerOutPort,
                                                       TaxFinderOutPort taxFinderOutPort,
                                                       AuthorizePaymentFactory authorizePaymentFactory,
                                                       WatchService watchService) {
        return new SaleAuthorizerServiceImpl(saleAuthorizerOutPort, taxFinderOutPort, authorizePaymentFactory, watchService);
    }

    @Bean
    public AuthorizePaymentFactory authorizePaymentFactory() {
        return new AuthorizePaymentFactoryImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}