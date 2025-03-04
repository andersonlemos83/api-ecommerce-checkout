package br.com.alc.ecommerce.checkout.infrastructure.config;

import br.com.alc.ecommerce.checkout.core.service.watch.WatchService;
import br.com.alc.ecommerce.checkout.core.service.watch.stub.VirtualWatchService;
import br.com.alc.ecommerce.checkout.infrastructure.adapter.output.stub.MostRecentSaleOrderFinderOutPortStub;
import br.com.alc.ecommerce.checkout.infrastructure.persistence.repository.SaleOrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Properties;

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

    @Primary
    @Bean("gitProperties")
    public GitProperties gitProperties() {
        Properties properties = new Properties();
        properties.put("branch", "test");
        properties.put("commit.id", "c4f908dbfef2699ef5d024301d3eb579b12198ea");
        properties.put("commit.id.abbrev", "c4f908d");
        properties.put("commit.time", "2025-03-03T19\\:18\\:33-0300");
        properties.put("build.time", "2025-03-03T19\\:18\\:33-0300");
        return new GitProperties(properties);
    }

    @Primary
    @Bean("buildProperties")
    public BuildProperties buildProperties() {
        Properties properties = new Properties();
        properties.put("group", "br.com.alc");
        properties.put("artifact", "api-ecommerce-checkout-infrastructure");
        properties.put("name", "api-ecommerce-checkout-infrastructure");
        properties.put("version", "1.0.0-TEST");
        properties.put("time", "2025-03-03T19\\:18\\:33-0300");
        return new BuildProperties(properties);
    }
}