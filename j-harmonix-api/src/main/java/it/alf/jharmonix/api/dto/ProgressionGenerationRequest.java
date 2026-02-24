package it.alf.jharmonix.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for harmony progression generation request.
 */
@Schema(
    description = "Request parameters for generating a harmonic progression",
    example = """
        {
          "tonicName": "C",
          "scaleType": "MAJOR",
          "songForm": "AABA",
          "style": "TRADITIONAL_JAZZ",
          "complexity": "INTERMEDIATE",
          "modulationFrequency": "LOW"
        }
        """
)
public record ProgressionGenerationRequest(
    
    @Schema(
        description = """
            Tonic note of the key. Supports both sharp and flat notation.
            Valid values: C, C#/Db, D, D#/Eb, E, F, F#/Gb, G, G#/Ab, A, A#/Bb, B
            """,
        example = "C"
    )
    @NotBlank(message = "Tonic must not be blank")
    String tonicName,
    
    @Schema(
        description = """
            Scale type for the progression. Determines the available chord palette.
            Supported scales: MAJOR, NATURAL_MINOR, HARMONIC_MINOR, MELODIC_MINOR,
            DORIAN, PHRYGIAN, LYDIAN, MIXOLYDIAN, AEOLIAN, LOCRIAN, LYDIAN_DOMINANT,
            ALTERED, HALF_WHOLE_DIMINISHED, WHOLE_TONE, CHROMATIC, BLUES.
            Use GET /scales endpoint to retrieve all available options.
            """,
        example = "MAJOR",
        allowableValues = {
            "MAJOR", "NATURAL_MINOR", "HARMONIC_MINOR", "MELODIC_MINOR",
            "DORIAN", "PHRYGIAN", "LYDIAN", "MIXOLYDIAN", "AEOLIAN", "LOCRIAN",
            "LYDIAN_DOMINANT", "ALTERED", "HALF_WHOLE_DIMINISHED", "WHOLE_TONE",
            "CHROMATIC", "BLUES"
        }
    )
    @NotNull(message = "Scale type must not be null")
    String scaleType,
    
    @Schema(
        description = """
            Song form/structure defining the overall architecture.
            - AABA: Classic 32-bar form (8+8+8+8 bars)
            - ABAC: Verse-Bridge-Verse-Coda structure
            - AAB: 12-bar blues form (4+4+4 bars)
            - THROUGH_COMPOSED: Free form without repeating sections
            """,
        example = "AABA",
        allowableValues = {"AABA", "ABAC", "AAB", "THROUGH_COMPOSED"}
    )
    @NotBlank(message = "Song form must not be blank")
    String songForm,
    
    @Schema(
        description = """
            Harmony style defining the character of chord progressions.
            - TRADITIONAL_JAZZ: II-V-I progressions, functional harmony (default)
            - BEBOP: Fast changes, chromatic approaches, tritone subs
            - MODAL: Static harmony, pedal points, limited movement
            - CONTEMPORARY_JAZZ: Quartal voicings, complex extensions, modern techniques
            Use GET /styles endpoint for full descriptions.
            """,
        example = "TRADITIONAL_JAZZ",
        defaultValue = "TRADITIONAL_JAZZ",
        allowableValues = {"TRADITIONAL_JAZZ", "BEBOP", "MODAL", "CONTEMPORARY_JAZZ"}
    )
    String style,
    
    @Schema(
        description = """
            Chord complexity level determining voice density.
            - BASIC: Simple triads (major, minor, diminished, augmented)
            - INTERMEDIATE: Seventh chords (maj7, m7, 7, m7b5) - default
            - ADVANCED: Extended chords (9th, 11th, 13th) with alterations
            Use GET /complexity-levels endpoint for details.
            """,
        example = "INTERMEDIATE",
        defaultValue = "INTERMEDIATE",
        allowableValues = {"BASIC", "INTERMEDIATE", "ADVANCED"}
    )
    String complexity,
    
    @Schema(
        description = """
            Modulation frequency controlling key changes.
            - NONE: Stay in original key
            - LOW: Rare modulations, mainly in bridge
            - MEDIUM: Occasional modulations to related keys (default)
            - HIGH: Frequent modulations to distant keys
            Use GET /modulation-frequencies endpoint for details.
            """,
        example = "MEDIUM",
        defaultValue = "MEDIUM",
        allowableValues = {"NONE", "LOW", "MEDIUM", "HIGH"}
    )
    String modulationFrequency
) {
    
    /**
     * Constructor with default values for optional parameters.
     */
    public ProgressionGenerationRequest {
        if (style == null || style.isBlank()) {
            style = "TRADITIONAL_JAZZ";
        }
        if (complexity == null || complexity.isBlank()) {
            complexity = "INTERMEDIATE";
        }
        if (modulationFrequency == null || modulationFrequency.isBlank()) {
            modulationFrequency = "MEDIUM";
        }
    }
}
