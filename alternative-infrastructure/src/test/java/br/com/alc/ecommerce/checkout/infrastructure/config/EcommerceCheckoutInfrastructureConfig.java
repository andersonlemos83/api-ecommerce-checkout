package br.com.alc.ecommerce.checkout.infrastructure.config;

import br.com.alc.ecommerce.checkout.core.service.watch.WatchService;
import br.com.alc.ecommerce.checkout.core.service.watch.stub.VirtualWatchService;
import br.com.alc.ecommerce.checkout.infrastructure.adapter.output.stub.MostRecentSaleOrderFinderOutPortStub;
import br.com.alc.ecommerce.checkout.infrastructure.persistence.repository.SaleOrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class EcommerceCheckoutInfrastructureConfig {

    @Primary
    @Bean("watchService")
    public WatchService watchService() {
        return new VirtualWatchService();
    }

    @Primary
    @Bean("mostRecentSaleOrderFinderOutPort")
    public MostRecentSaleOrderFinderOutPortStub mostRecentSaleOrderFinderOutPort(SaleOrderRepository saleOrderRepository,
                                                                                 ModelMapper modelMapper) {
        return new MostRecentSaleOrderFinderOutPortStub(saleOrderRepository, modelMapper);
    }
}