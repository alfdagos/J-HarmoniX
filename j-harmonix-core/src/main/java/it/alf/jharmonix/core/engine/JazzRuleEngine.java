package it.alf.jharmonix.core.engine;

import it.alf.jharmonix.core.model.*;
import it.alf.jharmonix.core.port.ProgressionRequest.HarmonyStyle;

import java.util.List;

public final class JazzRuleEngine {

    private final java.util.Random random;

    public JazzRuleEngine(long seed) { this.random = new java.util.Random(seed); }
    public JazzRuleEngine()          { this.random = new java.util.Random(); }

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

    private void applyTurnaround(List<Chord> chords, KeySignature key) {
        if (chords.size() < 4) return;
        Chord last = chords.getLast();
        Chord tonic = key.diatonicChord(0);
        if (!last.getRoot().equals(tonic.getRoot())) return;

        int insertFrom = chords.size() - 4;
        chords.set(insertFrom,     key.diatonicChord(0));
        chords.set(insertFrom + 1, key.diatonicChord(5));
        chords.set(insertFrom + 2, key.diatonicChord(1));
        chords.set(insertFrom + 3, key.diatonicChord(4));
    }

    private void applySecondaryDominants(List<Chord> chords, KeySignature key,
                                         HarmonyStyle style) {
        for (int i = chords.size() - 1; i > 0; i--) {
            Chord target = chords.get(i);
            if (target.getRoot().equals(key.getTonic())) continue;
            if (random.nextInt(10) < 4) {
                Note secDomRoot = target.getRoot().transpose(-7);
                Chord secDom = Chord.of(secDomRoot, ChordQuality.DOMINANT_SEVENTH);
                chords.add(i, secDom);
                i--;
            }
        }
    }

    private void applyTritoneSubstitutions(List<Chord> chords) {
        for (int i = 0; i < chords.size(); i++) {
            Chord c = chords.get(i);
            if (c.getQuality() == ChordQuality.DOMINANT_SEVENTH && random.nextInt(4) == 0) {
                chords.set(i, c.tritoneSubstitution());
            }
        }
    }

    public List<Chord> buildTwoFiveOne(KeySignature target) {
        return List.of(
            target.diatonicChord(1),
            target.diatonicChord(4),
            target.diatonicChord(0)
        );
    }
}
