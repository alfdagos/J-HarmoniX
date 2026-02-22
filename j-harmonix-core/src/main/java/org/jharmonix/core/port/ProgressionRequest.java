package org.jharmonix.core.port;

import org.jharmonix.core.model.ScaleType;

import java.util.Objects;

/**
 * Value object carrying all user-supplied parameters needed to generate a
 * harmonic progression.
 *
 * <p>Constructed via the builder pattern to ensure immutability and clear
 * naming at the call site. Every field has a sensible default so callers
 * only need to specify what they care about.
 *
 * <h3>Example</h3>
 * <pre>
 *   ProgressionRequest request = ProgressionRequest.builder()
 *       .tonicName("C")
 *       .scaleType(ScaleType.MAJOR)
 *       .songForm("AABA")
 *       .style(HarmonyStyle.JAZZ_STANDARD)
 *       .complexity(ComplexityLevel.SEVENTH_CHORDS)
 *       .modulationFrequency(ModulationFrequency.MEDIUM)
 *       .build();
 * </pre>
 */
public final class ProgressionRequest {

    // -------------------------------------------------------------------------
    // Nested enumerations
    // -------------------------------------------------------------------------

    /**
     * The overall harmonic style that guides chord selection and extensions.
     */
    public enum HarmonyStyle {
        /** Triads only, straightforward diatonic harmony. */
        SIMPLE,
        /** Classic popular / rock harmony, some sevenths. */
        POP,
        /** Standard jazz harmony: ii-V-I, seventh chords, basic extensions. */
        JAZZ_STANDARD,
        /** Modern jazz: advanced extensions, altered dominants, tritone subs. */
        JAZZ_MODERN
    }

    /**
     * Controls the maximum chord complexity (voicing density).
     */
    public enum ComplexityLevel {
        /** Triads (1-3-5 only). */
        TRIADS,
        /** Seventh chords (1-3-5-7). */
        SEVENTH_CHORDS,
        /** Ninths (1-3-5-7-9). */
        NINTHS,
        /** Full extensions (9, 11, 13) and alterations. */
        FULL_EXTENSIONS
    }

    /**
     * Controls how often the engine introduces key changes within the form.
     */
    public enum ModulationFrequency {
        NONE, LOW, MEDIUM, HIGH
    }

    // -------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------

    private final String           tonicName;
    private final ScaleType        scaleType;
    private final String           songForm;          // e.g. "AABA", "verse-chorus"
    private final HarmonyStyle     style;
    private final ComplexityLevel  complexity;
    private final ModulationFrequency modulationFrequency;
    private final int              beatsPerBar;

    // -------------------------------------------------------------------------
    // Constructor (private – use Builder)
    // -------------------------------------------------------------------------

    private ProgressionRequest(Builder b) {
        this.tonicName           = Objects.requireNonNull(b.tonicName,           "tonicName");
        this.scaleType           = Objects.requireNonNull(b.scaleType,           "scaleType");
        this.songForm            = Objects.requireNonNull(b.songForm,            "songForm");
        this.style               = Objects.requireNonNull(b.style,               "style");
        this.complexity          = Objects.requireNonNull(b.complexity,          "complexity");
        this.modulationFrequency = Objects.requireNonNull(b.modulationFrequency, "modulationFrequency");
        this.beatsPerBar         = b.beatsPerBar > 0 ? b.beatsPerBar : 4;
    }

    // -------------------------------------------------------------------------
    // Accessors
    // -------------------------------------------------------------------------

    public String            getTonicName()           { return tonicName; }
    public ScaleType         getScaleType()            { return scaleType; }
    public String            getSongForm()             { return songForm; }
    public HarmonyStyle      getStyle()                { return style; }
    public ComplexityLevel   getComplexity()           { return complexity; }
    public ModulationFrequency getModulationFrequency(){ return modulationFrequency; }
    public int               getBeatsPerBar()          { return beatsPerBar; }

    // -------------------------------------------------------------------------
    // Builder
    // -------------------------------------------------------------------------

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String            tonicName           = "C";
        private ScaleType         scaleType           = ScaleType.MAJOR;
        private String            songForm            = "AABA";
        private HarmonyStyle      style               = HarmonyStyle.JAZZ_STANDARD;
        private ComplexityLevel   complexity          = ComplexityLevel.SEVENTH_CHORDS;
        private ModulationFrequency modulationFrequency = ModulationFrequency.MEDIUM;
        private int               beatsPerBar         = 4;

        public Builder tonicName(String v)               { this.tonicName = v; return this; }
        public Builder scaleType(ScaleType v)            { this.scaleType = v; return this; }
        public Builder songForm(String v)                { this.songForm = v;  return this; }
        public Builder style(HarmonyStyle v)             { this.style = v;     return this; }
        public Builder complexity(ComplexityLevel v)     { this.complexity = v; return this; }
        public Builder modulationFrequency(ModulationFrequency v) { this.modulationFrequency = v; return this; }
        public Builder beatsPerBar(int v)                { this.beatsPerBar = v; return this; }

        public ProgressionRequest build() { return new ProgressionRequest(this); }
    }

    @Override
    public String toString() {
        return "ProgressionRequest{tonic=%s, scale=%s, form=%s, style=%s, complexity=%s, modulation=%s}"
            .formatted(tonicName, scaleType, songForm, style, complexity, modulationFrequency);
    }
}
