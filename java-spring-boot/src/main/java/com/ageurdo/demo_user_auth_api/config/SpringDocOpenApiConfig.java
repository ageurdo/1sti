package com.ageurdo.demo_user_auth_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("REST API - Spring User Auth")
                                .description("Api para cadastro e autenticação de usuários")
                                .version("v1")
                                .license(new License().name("Apache 2.0").url("https://apache.org/licenses/LICENSE-2.0"))
                                .contact(new Contact().name("Ageu Rodrigues").email("ageurdo@gmail.com"))
                );
    }
}
