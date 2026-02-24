package it.alf.jharmonix.api.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

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
                .description("""
                    REST API for generating jazz harmonic progressions based on music theory.
                    
                    ## Features
                    - Generate harmonic progressions in any key
                    - Support for 16 different scale types (Major, Dorian, Mixolydian, etc.)
                    - 4 harmony styles (Traditional Jazz, Bebop, Modal, Contemporary Jazz)
                    - Configurable complexity levels and modulation frequencies
                    - Multiple song forms (AABA, ABAC, AAB, Blues, etc.)
                    - Advanced jazz theory: modal interchange, chord extensions, alterations
                    
                    ## Usage
                    The primary endpoint is POST /api/v1/progressions/generate which accepts
                    a JSON request with tonic, scale, style and other parameters.
                    
                    Additional reference endpoints provide lists of valid values for scales,
                    styles, complexity levels and modulation frequencies.
                    """)
                .version("0.1.0")
                .contact(new Contact()
                    .name("J-Harmonix Team")
                    .email("info@j-harmonix.it")
                    .url("https://github.com/alfredodag/j-harmonix"))
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")))
            .servers(List.of(
                new Server()
                    .url("http://localhost:8080")
                    .description("Local development server"),
                new Server()
                    .url("http://localhost:8080")
                    .description("Production server (configurable)")
            ))
            .externalDocs(new ExternalDocumentation()
                .description("Complete Usage Guide (Italian)")
                .url("https://github.com/alfredodag/j-harmonix/blob/main/USAGE_GUIDE.md"));
    }
}
