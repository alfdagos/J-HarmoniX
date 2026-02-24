package it.alf.jharmonix.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main application class for J-Harmonix REST API.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"it.alf.jharmonix.api", "it.alf.jharmonix.core"})
public class JHarmonixApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JHarmonixApiApplication.class, args);
    }
}
