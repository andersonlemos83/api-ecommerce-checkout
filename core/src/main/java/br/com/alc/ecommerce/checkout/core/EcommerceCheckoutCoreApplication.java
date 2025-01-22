package br.com.alc.ecommerce.checkout.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"br.com.alc.ecommerce.checkout.core"})
public class EcommerceCheckoutCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceCheckoutCoreApplication.class, args);
    }

}