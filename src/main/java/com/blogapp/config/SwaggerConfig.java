package com.blogapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.blogapp.controllers"))
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(apiInfo());
//    }
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("Blog App API")
//                .description("API documentation for the Blog App")
//                .version("1.0.0")
//                .contact(new Contact("Blog App Team", "www.blogapp.com", "
//    }

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Blog Application APIs")
                        .version("1.0.0")
                        .description("API documentation for the Blog App")
                        .termsOfService("Terms of service URL")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Blog App Team")
                                .url("http://www.blogapp.com")
                                .email("email"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0"))
                );
        openAPI.security(securityReq());
        openAPI.components(new io.swagger.v3.oas.models.Components()
                .addSecuritySchemes("BearerAuth",
                        new io.swagger.v3.oas.models.security.SecurityScheme()
                                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("JWT Bearer Token for authentication")));
        return openAPI;
    }
    private List<SecurityRequirement> securityReq() {
        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList("BearerAuth");
        return List.of(securityRequirement);
    }
}