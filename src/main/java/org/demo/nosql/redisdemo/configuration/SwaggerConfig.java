package org.demo.nosql.redisdemo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex(".*/redis.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("REDIS DEMO API", "Http Interface to the redis db.", "0.0.1-SNAPSHOT", "Terms of service", new Contact("Marc Borras", "www.barcelona.com", "borras@barcelona.com"), "License of API", "API license URL", Collections.emptyList());
    }
}
