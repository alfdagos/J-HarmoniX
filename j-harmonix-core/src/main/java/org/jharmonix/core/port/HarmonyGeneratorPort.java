package org.jharmonix.core.port;

import org.jharmonix.core.model.Progression;

import java.util.List;

/**
 * <b>Primary port</b> (driving side) for the harmony generation use case.
 *
 * <p>Any adapter (CLI, REST, test harness) interacts with the core <em>only</em>
 * through this interface. The core domain must never import classes from an
 * adapter module.
 *
 * <h3>Hexagonal Architecture note</h3>
 * This interface is part of the <em>application core</em>. Its implementation
 * lives in the {@code harmony-engine} package of {@code j-harmonix-core}.
 * Adapters ({@code j-harmonix-cli}, {@code j-harmonix-api}) depend on this
 * interface, <em>not</em> on the implementation.
 */
public interface HarmonyGeneratorPort {

    /**
     * Generates a list of {@link Progression} objects that together form the
     * complete song structure described by the request.
     *
     * <p>Each element in the returned list corresponds to one section of the
     * song form (e.g. Verse A, Bridge, Chorus). The sections are ordered as
     * they would appear in performance.
     *
     * @param request the generation parameters (never {@code null})
     * @return ordered list of progressions (never empty)
     * @throws IllegalArgumentException if the request contains invalid parameters
     */
    List<Progression> generate(ProgressionRequest request);
}
