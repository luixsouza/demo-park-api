package com.compass.demo_park_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
        .components(new Components().addSecuritySchemes("security", securityScheme()))
        .info(new Info()
        .title("REST API - Spring Park")
        .description("API para gestão de estacionamento de veículos")
        .version("v1")
        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
        .contact(new Contact().name("Luis")));
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .description("Insira um Bearer token valido para prosseguir")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("security");
    }
}
