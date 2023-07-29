package com.digital.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Customer Management API")
                        .description("The Customer Management API is responsible for some basic operations to manage it such create one, search all , search by email , update , delete and so on.")
                        .version("1.0"));
    }

}
