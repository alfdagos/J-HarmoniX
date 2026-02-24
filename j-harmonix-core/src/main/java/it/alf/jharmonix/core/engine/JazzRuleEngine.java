package it.alf.jharmonix.core.engine;

import java.util.List;

import it.alf.jharmonix.core.model.Chord;
import it.alf.jharmonix.core.model.ChordQuality;
import it.alf.jharmonix.core.model.KeySignature;
import it.alf.jharmonix.core.model.Note;
import it.alf.jharmonix.core.model.Progression;
import it.alf.jharmonix.core.port.ProgressionRequest.ComplexityLevel;
import it.alf.jharmonix.core.port.ProgressionRequest.HarmonyStyle;

public final class JazzRuleEngine {

    private final java.util.Random random;

    public JazzRuleEngine(long seed) { this.random = new java.util.Random(seed); }
    public JazzRuleEngine()          { this.random = new java.util.Random(); }

    public Progression apply(Progression progression, KeySignature key, HarmonyStyle style) {
        return apply(progression, key, style, ComplexityLevel.SEVENTH_CHORDS);
    }

    public Progression apply(Progression progression, KeySignature key, HarmonyStyle style, ComplexityLevel complexity) {
        if (style == HarmonyStyle.SIMPLE || style == HarmonyStyle.POP) return progression;

        List<Chord> chords = new java.util.ArrayList<>(progression.getChords());

        if (style == HarmonyStyle.JAZZ_STANDARD || style == HarmonyStyle.JAZZ_MODERN) {
            applyTurnaround(chords, key);
            applySecondaryDominants(chords, key, style);
            
            // Apply extensions based on complexity
            if (complexity == ComplexityLevel.NINTHS || complexity == ComplexityLevel.FULL_EXTENSIONS) {
                applyExtensions(chords, complexity);
            }
            
            // Apply alterations for modern jazz
            if (style == HarmonyStyle.JAZZ_MODERN && complexity == ComplexityLevel.FULL_EXTENSIONS) {
                applyAlterations(chords);
            }
        }

        if (style == HarmonyStyle.JAZZ_MODERN) {
            applyTritoneSubstitutions(chords);
            
            // Apply modal interchange for sophisticated harmony
            if (complexity == ComplexityLevel.FULL_EXTENSIONS) {
                applyModalInterchange(chords, key);
            }
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

    /**
     * Apply chord extensions (9th, 11th, 13th) based on complexity level and chord type.
     * Music theory rules:
     * - Major 7th → maj9, maj13 (avoid 11 due to clash with major 3rd)
     * - Minor 7th → m9, m11 (11 works well on minor chords)
     * - Dominant 7th → 9, 13 (most flexible for extensions)
     */
    private void applyExtensions(List<Chord> chords, ComplexityLevel complexity) {
        for (int i = 0; i < chords.size(); i++) {
            Chord chord = chords.get(i);
            ChordQuality quality = chord.getQuality();
            
            // Apply 9th extensions for NINTHS level
            if (complexity == ComplexityLevel.NINTHS) {
                if (quality == ChordQuality.MAJOR_SEVENTH) {
                    chords.set(i, Chord.of(chord.getRoot(), ChordQuality.MAJOR_NINTH));
                } else if (quality == ChordQuality.MINOR_SEVENTH) {
                    chords.set(i, Chord.of(chord.getRoot(), ChordQuality.MINOR_NINTH));
                } else if (quality == ChordQuality.DOMINANT_SEVENTH && random.nextInt(10) < 7) {
                    chords.set(i, Chord.of(chord.getRoot(), ChordQuality.DOMINANT_NINTH));
                }
            }
            
            // Apply full extensions (11th, 13th) for FULL_EXTENSIONS level
            else if (complexity == ComplexityLevel.FULL_EXTENSIONS) {
                if (quality == ChordQuality.MAJOR_SEVENTH || quality == ChordQuality.MAJOR_NINTH) {
                    // Major chords: prefer 13th or #11, avoid natural 11
                    int choice = random.nextInt(3);
                    if (choice == 0) {
                        chords.set(i, Chord.of(chord.getRoot(), ChordQuality.MAJOR_THIRTEENTH));
                    } else if (choice == 1) {
                        chords.set(i, Chord.of(chord.getRoot(), ChordQuality.MAJOR_NINTH_SHARP_ELEVEN));
                    } else {
                        chords.set(i, Chord.of(chord.getRoot(), ChordQuality.MAJOR_NINTH));
                    }
                } else if (quality == ChordQuality.MINOR_SEVENTH || quality == ChordQuality.MINOR_NINTH) {
                    // Minor chords: 11th works well, 13th optional
                    int choice = random.nextInt(3);
                    if (choice == 0) {
                        chords.set(i, Chord.of(chord.getRoot(), ChordQuality.MINOR_ELEVENTH));
                    } else if (choice == 1) {
                        chords.set(i, Chord.of(chord.getRoot(), ChordQuality.MINOR_THIRTEENTH));
                    } else {
                        chords.set(i, Chord.of(chord.getRoot(), ChordQuality.MINOR_NINTH));
                    }
                } else if (quality == ChordQuality.DOMINANT_SEVENTH || quality == ChordQuality.DOMINANT_NINTH) {
                    // Dominant chords: very flexible, prefer 13th for full sound
                    int choice = random.nextInt(3);
                    if (choice == 0) {
                        chords.set(i, Chord.of(chord.getRoot(), ChordQuality.DOMINANT_THIRTEENTH));
                    } else if (choice == 1) {
                        chords.set(i, Chord.of(chord.getRoot(), ChordQuality.DOM_NINTH_SHARP_ELEVEN));
                    } else {
                        chords.set(i, Chord.of(chord.getRoot(), ChordQuality.DOMINANT_NINTH));
                    }
                }
            }
        }
    }

    /**
     * Apply altered tensions to dominant chords (b9, #9, #11, b13, 7alt).
     * Used in modern jazz for tension and resolution.
     */
    private void applyAlterations(List<Chord> chords) {
        for (int i = 0; i < chords.size(); i++) {
            Chord chord = chords.get(i);
            ChordQuality quality = chord.getQuality();
            
            // Apply alterations to dominant-function chords
            if (quality == ChordQuality.DOMINANT_SEVENTH || 
                quality == ChordQuality.DOMINANT_NINTH ||
                quality == ChordQuality.DOMINANT_THIRTEENTH) {
                
                // 30% chance to alter a dominant chord
                if (random.nextInt(10) < 3) {
                    int altChoice = random.nextInt(6);
                    ChordQuality alteredQuality = switch (altChoice) {
                        case 0 -> ChordQuality.DOM_SEVENTH_FLAT_NINE;
                        case 1 -> ChordQuality.DOM_SEVENTH_SHARP_NINE;
                        case 2 -> ChordQuality.DOM_SEVENTH_SHARP_ELEVEN;
                        case 3 -> ChordQuality.DOM_SEVEN_ALTERED;  // Full altered scale
                        case 4 -> ChordQuality.DOM_THIRTEENTH_FLAT_NINE;
                        default -> ChordQuality.DOM_THIRTEENTH_SHARP_ELEVEN;
                    };
                    chords.set(i, Chord.of(chord.getRoot(), alteredQuality));
                }
            }
        }
    }

    /**
     * Apply modal interchange: borrow chords from parallel minor/major.
     * In major key: bVIMaj7 (Abmaj7 in C), bVII7 (Bb7 in C), iv (Fm in C)
     * In minor key: borrow major chord qualities
     */
    private void applyModalInterchange(List<Chord> chords, KeySignature key) {
        boolean isMajor = key.getScale().getType().toString().contains("MAJOR");
        
        for (int i = 0; i < chords.size(); i++) {
            // 15% chance to apply modal interchange
            if (random.nextInt(100) < 15) {
                Chord chord = chords.get(i);
                
                if (isMajor) {
                    // In major key, borrow from parallel minor
                    int choice = random.nextInt(3);
                    if (choice == 0) {
                        // bVI major 7 (borrowed from minor)
                        Note bVI = key.getTonic().transpose(8);  // Ab in C major
                        chords.set(i, Chord.of(bVI, ChordQuality.MAJOR_SEVENTH));
                    } else if (choice == 1) {
                        // bVII dominant 7
                        Note bVII = key.getTonic().transpose(10);  // Bb in C major
                        chords.set(i, Chord.of(bVII, ChordQuality.DOMINANT_SEVENTH));
                    } else {
                        // iv minor (subdominant from minor)
                        Note iv = key.getTonic().transpose(5);  // F in C major
                        chords.set(i, Chord.of(iv, ChordQuality.MINOR_SEVENTH));
                    }
                } else {
                    // In minor key, occasionally borrow major tonality
                    if (random.nextInt(2) == 0) {
                        // IV major 7 instead of iv minor
                        Note IV = key.getTonic().transpose(5);
                        chords.set(i, Chord.of(IV, ChordQuality.MAJOR_SEVENTH));
                    }
                }
            }
        }
    }
}
