package org.jharmonix.core.engine;

import org.jharmonix.core.model.Progression;
import org.jharmonix.core.port.HarmonyGeneratorPort;
import org.jharmonix.core.port.ProgressionRequest;

import java.util.List;
import java.util.Objects;

/**
 * Default implementation of {@link HarmonyGeneratorPort}.
 *
 * <p>This service is the entry point into the harmony engine from any adapter.
 * It delegates to the {@link StructureComposer} which orchestrates
 * {@link ChordSelector}, {@link JazzRuleEngine}, and {@link ModulationStrategy}.
 *
 * <p>The class is intentionally free of any Spring annotations so it can be
 * used as a plain Java object in unit tests. Spring Boot's {@code @Component}
 * annotation is added in the CLI adapter's configuration class, or the service
 * can be registered via a {@code @Bean} factory method.
 *
 * <h3>Default wiring (deterministic seed for reproducibility)</h3>
 * <pre>
 *   long seed = 42L;
 *   JazzRuleEngine     rules     = new JazzRuleEngine(seed);
 *   ChordSelector      selector  = new ChordSelector(seed);
 *   ModulationStrategy modulation = new ModulationStrategy(rules, seed);
 *   StructureComposer  composer  = new StructureComposer(selector, rules, modulation);
 *   HarmonyGeneratorPort engine  = new HarmonyGeneratorService(composer);
 * </pre>
 */
public final class HarmonyGeneratorService implements HarmonyGeneratorPort {

    private final StructureComposer composer;

    public HarmonyGeneratorService(StructureComposer composer) {
        this.composer = Objects.requireNonNull(composer, "StructureComposer must not be null");
    }

    /**
     * Convenience factory that wires all internal components with the given seed.
     * Use seed = 0 for a fully random output.
     *
     * @param seed random seed; a fixed value yields deterministic progressions
     * @return a fully wired {@code HarmonyGeneratorService}
     */
    public static HarmonyGeneratorService withSeed(long seed) {
        JazzRuleEngine rules      = new JazzRuleEngine(seed);
        ChordSelector selector    = new ChordSelector(seed);
        ModulationStrategy mod    = new ModulationStrategy(rules, seed);
        StructureComposer composer = new StructureComposer(selector, rules, mod);
        return new HarmonyGeneratorService(composer);
    }

    /** Creates an instance with a random seed (non-deterministic). */
    public static HarmonyGeneratorService random() {
        return withSeed(System.currentTimeMillis());
    }

    // -------------------------------------------------------------------------
    // HarmonyGeneratorPort
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if the request tonic name is invalid
     */
    @Override
    public List<Progression> generate(ProgressionRequest request) {
        Objects.requireNonNull(request, "ProgressionRequest must not be null");
        return composer.compose(request);
    }
}
