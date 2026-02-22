package org.jharmonix.cli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot entry point for the J-Harmonix CLI application.
 *
 * <p>Spring Shell auto-discovers all {@code @ShellComponent} beans and presents
 * an interactive shell prompt when the application starts.
 *
 * <p>Run with:
 * <pre>
 *   mvn -pl j-harmonix-cli spring-boot:run
 * </pre>
 * or after packaging:
 * <pre>
 *   java -jar j-harmonix-cli/target/j-harmonix-cli-0.1.0-SNAPSHOT.jar
 * </pre>
 */
@SpringBootApplication
public class JHarmonixApplication {

    public static void main(String[] args) {
        SpringApplication.run(JHarmonixApplication.class, args);
    }
}
