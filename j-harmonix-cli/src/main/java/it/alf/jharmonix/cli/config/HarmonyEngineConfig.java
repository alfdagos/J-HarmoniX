package it.alf.jharmonix.cli.config;

import it.alf.jharmonix.core.engine.ChordSelector;
import it.alf.jharmonix.core.engine.JazzRuleEngine;
import it.alf.jharmonix.core.engine.ModulationStrategy;
import it.alf.jharmonix.core.engine.StructureComposer;
import it.alf.jharmonix.core.port.HarmonyGeneratorPort;
import it.alf.jharmonix.core.engine.HarmonyGeneratorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
