package com.digital.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
public class SwaggerConfig {
    @Bean
    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Customer Management API")
                .description("The Customer Management API is responsible for some basic operations to manage it such create one, search all , search by email , update , delete and so on.")
                .license("")
                .version("1.0")
                .contact(new Contact("Architecture Team - Digital", "", "adm.digital@dom.net.br"))
                .build();
    }

    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.digital"))
                .paths(regex("/api/.*"))
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);

    }
}
