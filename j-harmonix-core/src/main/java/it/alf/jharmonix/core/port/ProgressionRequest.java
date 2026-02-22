package it.alf.jharmonix.core.port;

import it.alf.jharmonix.core.model.ScaleType;

import java.util.Objects;

public final class ProgressionRequest {

    public enum HarmonyStyle { SIMPLE, POP, JAZZ_STANDARD, JAZZ_MODERN }
    public enum ComplexityLevel { TRIADS, SEVENTH_CHORDS, NINTHS, FULL_EXTENSIONS }
    public enum ModulationFrequency { NONE, LOW, MEDIUM, HIGH }

    private final String           tonicName;
    private final ScaleType        scaleType;
    private final String           songForm;
    private final HarmonyStyle     style;
    private final ComplexityLevel  complexity;
    private final ModulationFrequency modulationFrequency;
    private final int              beatsPerBar;

    private ProgressionRequest(Builder b) {
        this.tonicName           = Objects.requireNonNull(b.tonicName,           "tonicName");
        this.scaleType           = Objects.requireNonNull(b.scaleType,           "scaleType");
        this.songForm            = Objects.requireNonNull(b.songForm,            "songForm");
        this.style               = Objects.requireNonNull(b.style,               "style");
        this.complexity          = Objects.requireNonNull(b.complexity,          "complexity");
        this.modulationFrequency = Objects.requireNonNull(b.modulationFrequency, "modulationFrequency");
        this.beatsPerBar         = b.beatsPerBar > 0 ? b.beatsPerBar : 4;
    }

    public String            getTonicName()           { return tonicName; }
    public ScaleType         getScaleType()            { return scaleType; }
    public String            getSongForm()             { return songForm; }
    public HarmonyStyle      getStyle()                { return style; }
    public ComplexityLevel   getComplexity()           { return complexity; }
    public ModulationFrequency getModulationFrequency(){ return modulationFrequency; }
    public int               getBeatsPerBar()          { return beatsPerBar; }

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
