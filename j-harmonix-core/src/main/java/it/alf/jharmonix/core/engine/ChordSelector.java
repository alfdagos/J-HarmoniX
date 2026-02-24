package it.alf.jharmonix.core.engine;

import it.alf.jharmonix.core.model.Chord;
import it.alf.jharmonix.core.model.ChordQuality;
import it.alf.jharmonix.core.model.KeySignature;
import it.alf.jharmonix.core.port.ProgressionRequest.ComplexityLevel;
import it.alf.jharmonix.core.port.ProgressionRequest.HarmonyStyle;

public final class ChordSelector {

    public enum HarmonicFunction { TONIC, SUBDOMINANT, DOMINANT }

    private final java.util.Random random;

    public ChordSelector(long seed) { this.random = new java.util.Random(seed); }
    public ChordSelector()          { this.random = new java.util.Random(); }

    public Chord select(HarmonicFunction function,
                        KeySignature key,
                        ComplexityLevel complexity,
                        HarmonyStyle style) {

        Chord base = selectDiatonicChord(function, key);
        return applyComplexityAndStyle(base, function, complexity, style);
    }

    private Chord selectDiatonicChord(HarmonicFunction function, KeySignature key) {
        return switch (function) {
            case TONIC -> (random.nextInt(10) < 7) ? key.diatonicChord(0) : key.diatonicChord(5);
            case SUBDOMINANT -> (random.nextInt(10) < 6) ? key.diatonicChord(1) : key.diatonicChord(3);
            case DOMINANT -> key.diatonicChord(4);
        };
    }

    private Chord applyComplexityAndStyle(Chord base,
                                          HarmonicFunction function,
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
                 DOMINANT_NINTH               -> ChordQuality.MAJOR_TRIAD;
            case HALF_DIMINISHED,
                 DIMINISHED_SEVENTH           -> ChordQuality.DIM_TRIAD;
            default                           -> chord.getQuality();
        };
        return Chord.of(chord.getRoot(), triad);
    }
}
