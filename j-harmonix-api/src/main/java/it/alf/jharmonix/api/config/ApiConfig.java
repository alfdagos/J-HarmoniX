package it.alf.jharmonix.api.config;

import it.alf.jharmonix.core.engine.HarmonyGeneratorService;
import it.alf.jharmonix.core.port.HarmonyGeneratorPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration for J-Harmonix API.
 */
@Configuration
public class ApiConfig {

    @Value("${jharmonix.engine.seed:0}")
    private long seed;

    @Bean
    public HarmonyGeneratorPort harmonyGeneratorPort() {
        return seed == 0 
            ? HarmonyGeneratorService.random()
            : HarmonyGeneratorService.withSeed(seed);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                    .allowedOrigins("http://localhost:3000", "http://localhost:8080")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
            }
        };
    }
}
