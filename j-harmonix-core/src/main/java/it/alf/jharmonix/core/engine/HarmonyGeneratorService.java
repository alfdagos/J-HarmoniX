package it.alf.jharmonix.core.engine;

import java.util.List;
import java.util.Objects;

import it.alf.jharmonix.core.model.Progression;
import it.alf.jharmonix.core.port.HarmonyGeneratorPort;
import it.alf.jharmonix.core.port.ProgressionRequest;

public final class HarmonyGeneratorService implements HarmonyGeneratorPort {

    private final StructureComposer composer;

    public HarmonyGeneratorService(StructureComposer composer) {
        this.composer = Objects.requireNonNull(composer, "StructureComposer must not be null");
    }

    public static HarmonyGeneratorService withSeed(long seed) {
        JazzRuleEngine rules      = new JazzRuleEngine(seed);
        ChordSelector selector    = new ChordSelector(seed);
        ModulationStrategy mod    = new ModulationStrategy(seed);
        StructureComposer composer = new StructureComposer(selector, rules, mod);
        return new HarmonyGeneratorService(composer);
    }

    public static HarmonyGeneratorService random() {
        return withSeed(System.currentTimeMillis());
    }

    @Override
    public List<Progression> generate(ProgressionRequest request) {
        Objects.requireNonNull(request, "ProgressionRequest must not be null");
        return composer.compose(request);
    }
}
