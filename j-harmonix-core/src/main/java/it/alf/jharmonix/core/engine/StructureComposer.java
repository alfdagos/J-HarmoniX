package it.alf.jharmonix.core.engine;

import java.util.ArrayList;
import java.util.List;

import it.alf.jharmonix.core.engine.ChordSelector.HarmonicFunction;
import it.alf.jharmonix.core.model.Chord;
import it.alf.jharmonix.core.model.KeySignature;
import it.alf.jharmonix.core.model.Progression;
import it.alf.jharmonix.core.port.ProgressionRequest;
import it.alf.jharmonix.core.port.ProgressionRequest.HarmonyStyle;

public final class StructureComposer {

    private static final int VERSE_BARS   = 8;
    private static final int BRIDGE_BARS  = 8;
    private static final int CHORUS_BARS  = 8;
    private static final int OUTRO_BARS   = 4;

    private final ChordSelector     chordSelector;
    private final JazzRuleEngine    ruleEngine;
    private final ModulationStrategy modulation;

    public StructureComposer(ChordSelector chordSelector,
                             JazzRuleEngine ruleEngine,
                             ModulationStrategy modulation) {
        this.chordSelector = chordSelector;
        this.ruleEngine    = ruleEngine;
        this.modulation    = modulation;
    }

    public List<Progression> compose(ProgressionRequest request) {
        KeySignature homeKey = KeySignature.of(request.getTonicName(), request.getScaleType());
        String[] tokens = parseSongForm(request.getSongForm());
        List<Progression> sections = new ArrayList<>();

        KeySignature currentKey = homeKey;

        for (String token : tokens) {
            String label = labelFor(token);
            KeySignature sectionKey = resolveKeyForSection(token, homeKey, currentKey, request);
            List<Chord> bridge = buildBridge(currentKey, sectionKey, request);
            int bars = barsForSection(token);

            Progression raw = buildSection(label, sectionKey, bars, request);
            Progression enriched = ruleEngine.apply(raw, sectionKey, request.getStyle(), request.getComplexity());

            if (!bridge.isEmpty()) {
                List<Chord> withBridge = new ArrayList<>(bridge);
                withBridge.addAll(enriched.getChords());
                enriched = Progression.builder().addAll(withBridge).label(label).build();
            }

            sections.add(enriched);
            currentKey = sectionKey;
        }

        return sections;
    }

    private Progression buildSection(String label, KeySignature key, int bars,
                                     ProgressionRequest request) {
        HarmonyStyle style = request.getStyle();
        Progression.Builder b = Progression.builder().label(label);

        HarmonicFunction[] pattern8 = {
            HarmonicFunction.TONIC, HarmonicFunction.SUBDOMINANT,
            HarmonicFunction.DOMINANT, HarmonicFunction.TONIC,
            HarmonicFunction.TONIC, HarmonicFunction.SUBDOMINANT,
            HarmonicFunction.DOMINANT, HarmonicFunction.TONIC
        };
        HarmonicFunction[] pattern4 = {
            HarmonicFunction.TONIC, HarmonicFunction.SUBDOMINANT,
            HarmonicFunction.DOMINANT, HarmonicFunction.TONIC
        };

        HarmonicFunction[] pattern = bars >= 8 ? pattern8 : pattern4;
        for (int i = 0; i < bars; i++) {
            HarmonicFunction fn = pattern[i % pattern.length];
            b.add(chordSelector.select(fn, key, request.getComplexity(), style));
        }
        return b.build();
    }

    private KeySignature resolveKeyForSection(String token, KeySignature homeKey,
                                              KeySignature currentKey,
                                              ProgressionRequest request) {
        if (request.getModulationFrequency()
                == ProgressionRequest.ModulationFrequency.NONE) {
            return homeKey;
        }
        return switch (token.toLowerCase()) {
            case "b", "bridge" -> homeKey.subdominantKey();
            default            -> homeKey;
        };
    }

    private List<Chord> buildBridge(KeySignature from, KeySignature to,
                                    ProgressionRequest request) {
        if (from.equals(to)) return List.of();
        return modulation.buildBridge(from, to, request.getModulationFrequency());
    }

    private static String[] parseSongForm(String form) {
        if (form == null || form.isBlank()) return new String[]{"A", "A", "B", "A"};
        String trimmed = form.strip();
        if (trimmed.matches("[A-Za-z]+") && !trimmed.contains("-")) {
            String[] letters = new String[trimmed.length()];
            for (int i = 0; i < trimmed.length(); i++) {
                letters[i] = String.valueOf(trimmed.charAt(i)).toUpperCase();
            }
            return letters;
        }
        return trimmed.split("[-_\\s]+");
    }

    private static String labelFor(String token) {
        return switch (token.toUpperCase()) {
            case "A", "VERSE"   -> "Verse A";
            case "B", "BRIDGE"  -> "Bridge B";
            case "C", "CHORUS"  -> "Chorus C";
            case "OUTRO"        -> "Outro";
            default             -> "Section " + token;
        };
    }

    private static int barsForSection(String token) {
        return switch (token.toUpperCase()) {
            case "A", "VERSE"  -> VERSE_BARS;
            case "B", "BRIDGE" -> BRIDGE_BARS;
            case "C", "CHORUS" -> CHORUS_BARS;
            case "OUTRO"       -> OUTRO_BARS;
            default            -> VERSE_BARS;
        };
    }
}
