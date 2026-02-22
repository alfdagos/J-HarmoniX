package it.alf.jharmonix.cli.command;

import it.alf.jharmonix.core.model.Progression;
import it.alf.jharmonix.core.model.ScaleType;
import it.alf.jharmonix.core.port.HarmonyGeneratorPort;
import it.alf.jharmonix.core.port.ProgressionRequest;
import it.alf.jharmonix.core.port.ProgressionRequest.*;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

/**
 * Spring Shell commands for J-Harmonix.
 */
@ShellComponent
public class HarmonyCliCommands {

    private final HarmonyGeneratorPort engine;

    public HarmonyCliCommands(HarmonyGeneratorPort engine) {
        this.engine = engine;
    }

    @ShellMethod(value = "Generate a harmonic progression", key = "generate")
    public String generate(
        @ShellOption(value = "--tonic",       defaultValue = "C",              help = "Key tonic (e.g. C, F#, Bb)")        String tonic,
        @ShellOption(value = "--scale",       defaultValue = "MAJOR",           help = "Scale type (MAJOR, NATURAL_MINOR…)") String scale,
        @ShellOption(value = "--form",        defaultValue = "AABA",            help = "Song form (AABA, verse-chorus…)")    String form,
        @ShellOption(value = "--style",       defaultValue = "JAZZ_STANDARD",   help = "Harmony style")                     String style,
        @ShellOption(value = "--complexity",  defaultValue = "SEVENTH_CHORDS",  help = "Chord complexity level")            String complexity,
        @ShellOption(value = "--modulation",  defaultValue = "MEDIUM",          help = "Modulation frequency")              String modulation
    ) {
        ScaleType scaleType;
        HarmonyStyle harmonyStyle;
        ComplexityLevel complexityLevel;
        ModulationFrequency modulationFrequency;

        try {
            scaleType = ScaleType.valueOf(scale.toUpperCase());
        } catch (IllegalArgumentException e) {
            return "Unknown scale type: " + scale + ". Use 'scales' to list valid values.";
        }
        try {
            harmonyStyle = HarmonyStyle.valueOf(style.toUpperCase());
        } catch (IllegalArgumentException e) {
            return "Unknown harmony style: " + style + ". Use 'styles' to list valid values.";
        }
        try {
            complexityLevel = ComplexityLevel.valueOf(complexity.toUpperCase());
        } catch (IllegalArgumentException e) {
            return "Unknown complexity level: " + complexity
                + ". Valid: TRIADS, SEVENTH_CHORDS, NINTHS, FULL_EXTENSIONS";
        }
        try {
            modulationFrequency = ModulationFrequency.valueOf(modulation.toUpperCase());
        } catch (IllegalArgumentException e) {
            return "Unknown modulation frequency: " + modulation
                + ". Valid: NONE, LOW, MEDIUM, HIGH";
        }

        ProgressionRequest request = ProgressionRequest.builder()
            .tonicName(tonic)
            .scaleType(scaleType)
            .songForm(form)
            .style(harmonyStyle)
            .complexity(complexityLevel)
            .modulationFrequency(modulationFrequency)
            .build();

        List<Progression> sections;
        try {
            sections = engine.generate(request);
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }

        return formatOutput(tonic, scale, form, sections);
    }

    @ShellMethod(value = "List available scale types", key = "scales")
    public String scales() {
        StringBuilder sb = new StringBuilder("Available scale types:\n");
        for (ScaleType t : ScaleType.values()) {
            sb.append("  %-20s  %s%n".formatted(t.name(), t.getDisplayName()));
        }
        return sb.toString();
    }

    @ShellMethod(value = "List available harmony styles", key = "styles")
    public String styles() {
        return """
            Available harmony styles:
              SIMPLE           – Triads, diatonic only
              POP              – Some seventh chords, no jazz substitutions
              JAZZ_STANDARD    – ii-V-I, turnarounds, basic extensions
              JAZZ_MODERN      – Altered dominants, tritone subs, advanced extensions
            """;
    }

    private String formatOutput(String tonic, String scale, String form,
                                List<Progression> sections) {
        String separator = "═".repeat(60);
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(separator).append("\n");
        sb.append("  J-Harmonix  │  Key: %s %s  │  Form: %s%n"
            .formatted(tonic.toUpperCase(), scale, form.toUpperCase()));
        sb.append(separator).append("\n\n");

        for (Progression p : sections) {
            sb.append(p).append("\n\n");
        }
        sb.append(separator).append("\n");
        return sb.toString();
    }
}
