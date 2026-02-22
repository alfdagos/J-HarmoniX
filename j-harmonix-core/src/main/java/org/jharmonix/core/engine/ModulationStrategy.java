package org.jharmonix.core.engine;

import org.jharmonix.core.model.*;
import org.jharmonix.core.port.ProgressionRequest.ModulationFrequency;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the various modulation techniques described in the J-Harmonix
 * technical specification.
 *
 * <h3>Supported techniques</h3>
 * <ol>
 *   <li><b>Pivot chord</b> – finds an accord common to both the source and target
 *       key and uses it as a bridge (diatonic modulation).</li>
 *   <li><b>Secondary ii-V</b> – inserts a ii-V cadence that resolves to the new
 *       tonic. Used for stronger, more intentional key changes.</li>
 *   <li><b>Tritone substitute modulation</b> – uses the tritone substitute of the
 *       dominant to arrive at a target key a half step below the substitute root
 *       (chromatic modulation, modern jazz).</li>
 * </ol>
 *
 * <p>The {@link ModulationFrequency} parameter controls how often modulations are
 * triggered:
 * <ul>
 *   <li>NONE – no modulations inserted.</li>
 *   <li>LOW  – pivot chord only, at most once per form.</li>
 *   <li>MEDIUM – secondary ii-V modulations at section boundaries.</li>
 *   <li>HIGH – all techniques, including chromatic/tritone modulations.</li>
 * </ul>
 */
public final class ModulationStrategy {

    private final JazzRuleEngine ruleEngine;
    private final java.util.Random random;

    public ModulationStrategy(JazzRuleEngine ruleEngine, long seed) {
        this.ruleEngine = ruleEngine;
        this.random = new java.util.Random(seed);
    }

    public ModulationStrategy(JazzRuleEngine ruleEngine) {
        this(ruleEngine, System.currentTimeMillis());
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    /**
     * Returns a list of "bridge" chords that smoothly transition from the source
     * key to the target key, given the requested modulation frequency.
     *
     * <p>The returned list is empty when {@code frequency == NONE} or when no
     * suitable technique is found.
     *
     * @param source    the current key
     * @param target    the destination key
     * @param frequency how aggressively to modulate
     * @return ordered list of bridging chords (may be empty)
     */
    public List<Chord> buildBridge(KeySignature source,
                                   KeySignature target,
                                   ModulationFrequency frequency) {
        return switch (frequency) {
            case NONE   -> List.of();
            case LOW    -> pivotChordBridge(source, target);
            case MEDIUM -> secondaryIIVBridge(target);
            case HIGH   -> chooseAdvanced(source, target);
        };
    }

    // -------------------------------------------------------------------------
    // Technique 1 – Pivot chord (diatonic modulation)
    // -------------------------------------------------------------------------

    /**
     * Identifies a chord that is diatonic in both source and target keys and
     * returns it as a single-chord "pivot" bridge.
     *
     * <p>If no common chord is found, falls back to the secondary ii-V technique.
     */
    private List<Chord> pivotChordBridge(KeySignature source, KeySignature target) {
        Scale sourcScale = source.getScale();
        Scale targetScale = target.getScale();

        for (int sd = 0; sd < sourcScale.size(); sd++) {
            Chord sourceChord = source.diatonicChord(sd);
            for (int td = 0; td < targetScale.size(); td++) {
                Chord targetChord = target.diatonicChord(td);
                // Two diatonic chords are "pivot-compatible" when they share root and quality
                if (sourceChord.equals(targetChord)) {
                    return List.of(sourceChord);
                }
            }
        }
        // Fall back to secondary ii-V if no pivot found
        return secondaryIIVBridge(target);
    }

    // -------------------------------------------------------------------------
    // Technique 2 – Secondary ii-V modulation
    // -------------------------------------------------------------------------

    /**
     * Inserts a ii-V cadence (two chords) that resolves to the target tonic,
     * announcing the new key clearly.
     *
     * <p>Example: Modulating to Eb Major → [Fm7, Bb7] before Ebmaj7.
     */
    private List<Chord> secondaryIIVBridge(KeySignature target) {
        List<Chord> bridge = new ArrayList<>();
        bridge.add(target.diatonicChord(1)); // IIm7 of target
        bridge.add(target.diatonicChord(4)); // V7  of target
        return bridge;
    }

    // -------------------------------------------------------------------------
    // Technique 3 – Advanced (tritone / chromatic) modulation
    // -------------------------------------------------------------------------

    /**
     * Randomly chooses between tritone-substitute modulation and secondary ii-V,
     * weighted toward the more sophisticated technique.
     */
    private List<Chord> chooseAdvanced(KeySignature source, KeySignature target) {
        int roll = random.nextInt(3);
        return switch (roll) {
            case 0 -> tritoneSubModulation(target);
            case 1 -> pivotChordBridge(source, target);
            default -> secondaryIIVBridge(target);
        };
    }

    /**
     * Builds a tritone-substitute modulation: V7 of target → tritone sub of V7.
     *
     * <p>The substitute dominant resolves a half step down to the target tonic,
     * producing a characteristic chromatic bass line.
     *
     * <p>Example: Modulating to C → [Db7] (tritone sub of G7) → Cmaj7.
     */
    private List<Chord> tritoneSubModulation(KeySignature target) {
        Chord dominant = target.diatonicChord(4); // V7
        Chord tritoneSub = dominant.tritoneSubstitution();
        return List.of(tritoneSub);
    }
}
