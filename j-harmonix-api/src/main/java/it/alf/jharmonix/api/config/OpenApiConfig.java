package it.alf.jharmonix.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI/Swagger configuration.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI jHarmonixOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("J-Harmonix API")
                .description("REST API for generating jazz harmony progressions using music theory rules")
                .version("0.1.0")
                .contact(new Contact()
                    .name("Alfred Daga")
                    .email("alfred@example.com"))
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")));
    }
}
