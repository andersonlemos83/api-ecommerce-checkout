package br.com.alc.ecommerce.checkout.infrastructure.config;

import br.com.alc.ecommerce.checkout.core.service.watch.WatchService;
import br.com.alc.ecommerce.checkout.core.service.watch.stub.VirtualWatchService;
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
}