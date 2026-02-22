package org.jharmonix.cli.config;

import org.jharmonix.core.engine.*;
import org.jharmonix.core.port.HarmonyGeneratorPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration that wires the core engine components.
 *
 * <p>All core classes are framework-agnostic (plain Java). This {@code @Configuration}
 * is the single place where Spring learns about them. This approach keeps the
 * core free of Spring annotations, in line with the Hexagonal Architecture.
 */
@Configuration
public class HarmonyEngineConfig {

    @Value("${jharmonix.engine.seed:0}")
    private long seed;

    @Bean
    JazzRuleEngine jazzRuleEngine() {
        return seed == 0 ? new JazzRuleEngine() : new JazzRuleEngine(seed);
    }

    @Bean
    ChordSelector chordSelector() {
        return seed == 0 ? new ChordSelector() : new ChordSelector(seed);
    }

    @Bean
    ModulationStrategy modulationStrategy(JazzRuleEngine ruleEngine) {
        return seed == 0
            ? new ModulationStrategy(ruleEngine)
            : new ModulationStrategy(ruleEngine, seed);
    }

    @Bean
    StructureComposer structureComposer(ChordSelector selector,
                                        JazzRuleEngine ruleEngine,
                                        ModulationStrategy modulation) {
        return new StructureComposer(selector, ruleEngine, modulation);
    }

    @Bean
    HarmonyGeneratorPort harmonyGeneratorPort(StructureComposer composer) {
        return new HarmonyGeneratorService(composer);
    }
}
