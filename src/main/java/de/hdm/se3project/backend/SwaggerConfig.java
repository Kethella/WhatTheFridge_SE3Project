package de.hdm.se3project.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    private ApiInfo apiInfo() {
        return new ApiInfo("WhatTheFridge Rest APIs",
                "APIs for MyApp.",
                "1.0",
                null,
                null,
                //new Contact("test", "www.org.com", "test@emaildomain.com"),
                null,
                null,
                Collections.emptyList());
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("de.hdm.se3project.backend"))
                .paths(regex("/api/v1.*"))
                .build();
    }
}


