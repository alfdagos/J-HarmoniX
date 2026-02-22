package org.jharmonix.core.engine;

import org.jharmonix.core.model.*;
import org.jharmonix.core.port.ProgressionRequest;
import org.jharmonix.core.port.ProgressionRequest.ComplexityLevel;
import org.jharmonix.core.port.ProgressionRequest.HarmonyStyle;

/**
 * Selects the most appropriate chord for a given harmonic function and context.
 *
 * <p>The selection follows the functional hierarchy of jazz harmony:
 * <ol>
 *   <li><b>TONIC</b>   – degrees I (Imaj7) and VI (VIm7) share the tonic function.</li>
 *   <li><b>SUBDOMINANT</b> – degrees II (IIm7) and IV (IVmaj7).</li>
 *   <li><b>DOMINANT</b>  – degree V (V7), optionally altered for jazz styles.</li>
 * </ol>
 *
 * <p>The {@link ComplexityLevel} controls whether triads, seventh chords, or
 * extensions are used; the {@link HarmonyStyle} enables jazz-specific voicings
 * (altered dominants, tritone substitutions at random).
 */
public final class ChordSelector {

    /** Harmonic functions assigned to each scale degree (0-based). */
    public enum HarmonicFunction { TONIC, SUBDOMINANT, DOMINANT }

    // Functional assignment for major / natural-minor scale degrees (0-based)
    private static final HarmonicFunction[] MAJOR_FUNCTIONS = {
        HarmonicFunction.TONIC,        // I
        HarmonicFunction.SUBDOMINANT,  // II
        HarmonicFunction.TONIC,        // III  (weak tonic)
        HarmonicFunction.SUBDOMINANT,  // IV
        HarmonicFunction.DOMINANT,     // V
        HarmonicFunction.TONIC,        // VI   (tonic substitute)
        HarmonicFunction.DOMINANT      // VII  (leading tone)
    };

    private final java.util.Random random;

    public ChordSelector(long seed) { this.random = new java.util.Random(seed); }
    public ChordSelector()          { this.random = new java.util.Random(); }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    /**
     * Picks a chord to fulfil the requested harmonic function within the given key.
     *
     * @param function   the desired harmonic function
     * @param key        the current key context
     * @param complexity the voicing complexity requested by the user
     * @param style      the overall harmony style
     * @return the selected chord
     */
    public Chord select(HarmonicFunction function,
                        KeySignature key,
                        ComplexityLevel complexity,
                        HarmonyStyle style) {

        Chord base = selectDiatonicChord(function, key);
        return applyComplexityAndStyle(base, function, key, complexity, style);
    }

    // -------------------------------------------------------------------------
    // Internal helpers
    // -------------------------------------------------------------------------

    private Chord selectDiatonicChord(HarmonicFunction function, KeySignature key) {
        // For each function there are one or two candidate degrees; choose with
        // a slight bias toward the primary chord (≈ 70 / 30 split).
        return switch (function) {
            case TONIC ->
                // Primary: I (0),  alternate: VI (5)
                (random.nextInt(10) < 7) ? key.diatonicChord(0) : key.diatonicChord(5);
            case SUBDOMINANT ->
                // Primary: II (1), alternate: IV (3)
                (random.nextInt(10) < 6) ? key.diatonicChord(1) : key.diatonicChord(3);
            case DOMINANT ->
                // Always V (degree index 4 of major / harmonic minor)
                key.diatonicChord(4);
        };
    }

    /**
     * Upgrades or modifies the base chord according to complexity and style.
     *
     * <ul>
     *   <li>TRIADS: downgrades seventh chords to triads.</li>
     *   <li>SEVENTH_CHORDS: uses the diatonic seventh as-is (default).</li>
     *   <li>NINTHS / FULL_EXTENSIONS: adds 9th / 9th + 13th to dominant chords.</li>
     *   <li>JAZZ_MODERN: uses altered dominant (7b9) and occasionally tritone subs.</li>
     * </ul>
     */
    private Chord applyComplexityAndStyle(Chord base,
                                          HarmonicFunction function,
                                          KeySignature key,
                                          ComplexityLevel complexity,
                                          HarmonyStyle style) {
        if (complexity == ComplexityLevel.TRIADS) {
            return downgradeToTriad(base);
        }

        boolean isDominant = function == HarmonicFunction.DOMINANT;

        if (isDominant) {
            if (complexity == ComplexityLevel.NINTHS
                    || complexity == ComplexityLevel.FULL_EXTENSIONS) {
                base = Chord.of(base.getRoot(), ChordQuality.DOMINANT_NINTH);
            }
            if (style == HarmonyStyle.JAZZ_MODERN) {
                // 30 % chance of altered dominant (7b9) or tritone sub
                int roll = random.nextInt(10);
                if (roll < 2) return Chord.of(base.getRoot(), ChordQuality.DOM_SEVENTH_FLAT_NINE);
                if (roll < 4) return base.tritoneSubstitution();
            }
        }
        return base;
    }

    private static Chord downgradeToTriad(Chord chord) {
        ChordQuality triad = switch (chord.getQuality()) {
            case MAJOR_SEVENTH, MAJOR_NINTH   -> ChordQuality.MAJOR_TRIAD;
            case MINOR_SEVENTH, MINOR_NINTH,
                 MINOR_MAJOR_SEVENTH          -> ChordQuality.MINOR_TRIAD;
            case DOMINANT_SEVENTH,
                 DOMINANT_NINTH               -> ChordQuality.MAJOR_TRIAD; // no 7th
            case HALF_DIMINISHED,
                 DIMINISHED_SEVENTH           -> ChordQuality.DIM_TRIAD;
            default                           -> chord.getQuality();
        };
        return Chord.of(chord.getRoot(), triad);
    }
}
