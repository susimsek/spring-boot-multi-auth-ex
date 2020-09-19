package com.spring.auth.config;

import com.spring.auth.security.UserDetailsImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;


@Profile(Profiles.OATH2_AUTH)
@FieldDefaults(level = AccessLevel.PRIVATE)
@EnableSwagger2
@Configuration
public class Oauth2AuhSwaggerConfig {

    @Value("${auth-server-url}")
    String issuerUri;

    @Value("${security.oauth2.client.client-id}")
    String clientId;

    @Value("${security.oauth2.client.client-secret}")
    String clientSecret;

    @Value("${security.oauth2.authorization.realm}")
    String realm;

    private static final String APP_NAME = "rest-api";
    private static final String OAUTH2_AUTH = "oauth2_auth";


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

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .appName(APP_NAME)
                .scopeSeparator(" ")
                .build();
    }


    private SecurityScheme securityScheme() {
        GrantType grantType =
                new AuthorizationCodeGrantBuilder()
                        .tokenEndpoint(new TokenEndpoint(issuerUri + "/realms/" + realm + "/protocol/openid-connect/token", APP_NAME))
                        .tokenRequestEndpoint(
                                new TokenRequestEndpoint(issuerUri + "/realms/" + realm + "/protocol/openid-connect/auth", clientId, clientSecret))
                        .build();

        SecurityScheme oauth =
                new OAuthBuilder()
                        .name(OAUTH2_AUTH)
                        .grantTypes(Arrays.asList(grantType))
                        .scopes(Arrays.asList(globalScope()))
                        .build();
        return oauth;
    }

    private AuthorizationScope globalScope() {
        return new AuthorizationScope("global", "accessEverything");
    }


    private SecurityContext securityContext() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = globalScope();

        return SecurityContext.builder()
                .securityReferences(Arrays.asList(new SecurityReference(OAUTH2_AUTH, authorizationScopes)))
                .forPaths(PathSelectors.regex("^(?!.*signin).*$"))
                .build();
    }
}
