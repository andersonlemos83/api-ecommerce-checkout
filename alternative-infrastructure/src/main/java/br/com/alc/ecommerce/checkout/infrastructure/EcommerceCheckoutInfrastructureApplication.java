package br.com.alc.ecommerce.checkout.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EntityScan(basePackages = {"br.com.alc.ecommerce.checkout.infrastructure.*"})
@SpringBootApplication(scanBasePackages = {"br.com.alc.ecommerce.checkout.infrastructure"})
public class EcommerceCheckoutInfrastructureApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceCheckoutInfrastructureApplication.class, args);
    }

}