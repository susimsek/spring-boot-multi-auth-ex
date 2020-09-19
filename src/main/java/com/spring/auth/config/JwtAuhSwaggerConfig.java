package com.spring.auth.config;

import com.spring.auth.security.UserDetailsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;


@Profile(Profiles.JWT_AUTH)
@EnableSwagger2
@Configuration
public class JwtAuhSwaggerConfig {

    private static final String JWT_AUTH = "jwt_auth";

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.spring.auth.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData())
                .ignoredParameterTypes(UserDetailsImpl.class)
                .securitySchemes(Arrays.asList(securityScheme()))
                .securityContexts(List.of(securityContext()));
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Spring Boot Multi Auth REST API")
                .description("\"Provides access to the core features of Spring Boot Multi Auth Rest Api.\"")
                .version("1.0.0")
                .build();
    }


    private SecurityScheme securityScheme() {
        return new ApiKey(JWT_AUTH, "Authorization", "header");
    }

    private SecurityReference jwtAuthReference() {
        return new SecurityReference(JWT_AUTH, new AuthorizationScope[0]);
    }

    private SecurityContext securityContext() {
        return SecurityContext
                .builder()
                .securityReferences(List.of(jwtAuthReference()))
                .forPaths(PathSelectors.regex("^(?!.*signin).*$"))
                .build();
    }
}
