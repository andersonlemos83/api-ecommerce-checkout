package br.com.alc.ecommerce.checkout.infrastructure.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("squid:S2187")
@Configuration
public class WireMockConfigurationTest {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer wireMockServer() {
        return new WireMockServer(8090);
    }
}