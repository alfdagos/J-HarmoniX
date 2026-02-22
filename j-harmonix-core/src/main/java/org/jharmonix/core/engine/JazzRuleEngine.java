package org.jharmonix.core.engine;

import org.jharmonix.core.model.*;
import org.jharmonix.core.port.ProgressionRequest.HarmonyStyle;

import java.util.List;

/**
 * Applies jazz-specific harmonic transformations and rules to a raw progression.
 *
 * <p>This class acts as a post-processing step: it receives a structurally valid
 * diatonic progression and enriches it with the following techniques:
 *
 * <ul>
 *   <li><b>Turnaround (I-VI-ii-V)</b>: replaces the last two bars of a section
 *       with a standard jazz turnaround when the section ends on the tonic.</li>
 *   <li><b>Secondary dominant</b>: inserts a V7/x chord before a diatonic chord
 *       to intensify the resolution.</li>
 *   <li><b>Tritone substitution</b>: replaces a dominant chord with its tritone
 *       substitute (root ±6 semitones) for a chromatic bass line effect.</li>
 *   <li><b>Backdoor dominant</b>: substitutes the V7 with the bVII7 (approaching
 *       the tonic from a half step above the b7).</li>
 * </ul>
 *
 * <p>Rules are only applied when the requested style is
 * {@link HarmonyStyle#JAZZ_STANDARD} or {@link HarmonyStyle#JAZZ_MODERN}.
 */
public final class JazzRuleEngine {

    private final java.util.Random random;

    public JazzRuleEngine(long seed) { this.random = new java.util.Random(seed); }
    public JazzRuleEngine()          { this.random = new java.util.Random(); }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    /**
     * Applies jazz transformations to the given progression.
     *
     * @param progression the input progression (will not be mutated)
     * @param key         the current key context
     * @param style       the requested harmony style
     * @return a new progression with jazz rules applied (or the original if SIMPLE/POP)
     */
    public Progression apply(Progression progression, KeySignature key, HarmonyStyle style) {
        if (style == HarmonyStyle.SIMPLE || style == HarmonyStyle.POP) return progression;

        List<Chord> chords = new java.util.ArrayList<>(progression.getChords());

        if (style == HarmonyStyle.JAZZ_STANDARD || style == HarmonyStyle.JAZZ_MODERN) {
            applyTurnaround(chords, key);
            applySecondaryDominants(chords, key, style);
        }

        if (style == HarmonyStyle.JAZZ_MODERN) {
            applyTritoneSubstitutions(chords);
        }

        return Progression.builder()
            .addAll(chords)
            .label(progression.getSectionLabel())
            .build();
    }

    // -------------------------------------------------------------------------
    // Transformations
    // -------------------------------------------------------------------------

    /**
     * Replaces the last two chords with a I-VI-ii-V turnaround when the section
     * ends on the tonic chord.
     *
     * <p>Classic jazz turnaround in C Major: {@code Cmaj7 | Am7 | Dm7 | G7 }
     */
    private void applyTurnaround(List<Chord> chords, KeySignature key) {
        if (chords.size() < 4) return;
        Chord last = chords.getLast();
        Chord tonic = key.diatonicChord(0);
        if (!last.getRoot().equals(tonic.getRoot())) return; // not ending on tonic – skip

        // Replace last 4 chords with turnaround I-VI-ii-V
        int insertFrom = chords.size() - 4;
        chords.set(insertFrom,     key.diatonicChord(0)); // I
        chords.set(insertFrom + 1, key.diatonicChord(5)); // VI
        chords.set(insertFrom + 2, key.diatonicChord(1)); // ii
        chords.set(insertFrom + 3, key.diatonicChord(4)); // V
    }

    /**
     * With a 40 % probability per chord, inserts a secondary dominant (V7/x) before
     * a diatonic non-tonic chord to create stronger motion.
     *
     * <p>Example: before Dm7 insert A7 (V7/ii), before Am7 insert E7 (V7/vi).
     */
    private void applySecondaryDominants(List<Chord> chords, KeySignature key,
                                         HarmonyStyle style) {
        // Work backwards to avoid index shifting issues
        for (int i = chords.size() - 1; i > 0; i--) {
            Chord target = chords.get(i);
            // Skip the tonic chord – a secondary dominant before the tonic is rare
            if (target.getRoot().equals(key.getTonic())) continue;
            if (random.nextInt(10) < 4) { // ~40 %
                // V7/x: dominant seventh a perfect fifth above the target root
                Note secDomRoot = target.getRoot().transpose(-7); // P5 below = tritone's complement
                Chord secDom = Chord.of(secDomRoot, ChordQuality.DOMINANT_SEVENTH);
                chords.add(i, secDom);
                i--; // adjust index after insertion
            }
        }
    }

    /**
     * Replaces each dominant seventh chord with its tritone substitute with a
     * 25 % probability (style = JAZZ_MODERN only).
     */
    private void applyTritoneSubstitutions(List<Chord> chords) {
        for (int i = 0; i < chords.size(); i++) {
            Chord c = chords.get(i);
            if (c.getQuality() == ChordQuality.DOMINANT_SEVENTH && random.nextInt(4) == 0) {
                chords.set(i, c.tritoneSubstitution());
            }
        }
    }

    // -------------------------------------------------------------------------
    // Utility: build a ii-V-I cadence in any key (used by ModulationStrategy)
    // -------------------------------------------------------------------------

    /**
     * Builds a standard ii-V-I cadence (three chords) resolving to the given key.
     *
     * @param target the destination key
     * @return list [ IIm7, V7, Imaj7 ] in the target key
     */
    public List<Chord> buildTwoFiveOne(KeySignature target) {
        return List.of(
            target.diatonicChord(1), // IIm7
            target.diatonicChord(4), // V7
            target.diatonicChord(0)  // Imaj7
        );
    }
}
