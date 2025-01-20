package br.com.alc.ecommerce.checkout.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.service.RequestHandlerProvider;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.EMPTY_LIST;
import static java.util.Collections.singletonList;
import static springfox.documentation.builders.PathSelectors.any;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
public class SpringFoxConfiguration {

    @Bean
    @Primary
    public RequestHandlerProvider entityServicesProvider() {
        // fix para usar o Springfox 3.0.0 com Spring Boot maior que 2.6.0 e não tem ambiguidade entre métodos
        // Ver AbstractDocumentationPluginsBootstrapper
        return () -> EMPTY_LIST;
    }

    @Bean
    public Docket customImplementation(@Value("${springfox.documentation.info.title}") String title,
                                       @Value("${springfox.documentation.info.version}") String version,
                                       @Value("${springfox.documentation.info.description}") String description,
                                       @Value("${springfox.documentation.base-package}") String basePackage) {
        return new Docket(SWAGGER_2)
                .apiInfo(buildApiInfo(title, description, version))
                .securitySchemes(singletonList(buildApiKey()))
                .securityContexts(singletonList(buildSecurityContext()))
                .directModelSubstitute(LocalDate.class, String.class)
                .useDefaultResponseMessages(false)
                .select()
                .apis(basePackage(basePackage))
                .paths(any())
                .build();
    }

    private ApiInfo buildApiInfo(String title, String description, String version) {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .build();
    }

    private ApiKey buildApiKey() {
        return new ApiKey("Token Bearer", "Authorization", "header");
    }

    private SecurityContext buildSecurityContext() {
        return SecurityContext.builder()
                .securityReferences(buildDefaultAuth())
                .forPaths(any())
                .build();
    }

    private List<SecurityReference> buildDefaultAuth() {
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = new AuthorizationScope("ui", "ui");
        return singletonList(new SecurityReference("key", scopes));
    }
}