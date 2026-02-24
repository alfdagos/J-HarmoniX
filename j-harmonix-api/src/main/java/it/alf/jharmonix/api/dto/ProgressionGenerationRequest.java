package it.alf.jharmonix.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for harmony progression generation request.
 */
@Schema(description = "Request to generate a harmonic progression")
public record ProgressionGenerationRequest(
    
    @Schema(description = "Tonic note of the key", example = "C", required = true)
    @NotBlank(message = "Tonic must not be blank")
    String tonicName,
    
    @Schema(description = "Scale type (MAJOR, NATURAL_MINOR, DORIAN, etc.)", example = "MAJOR", required = true)
    @NotNull(message = "Scale type must not be null")
    String scaleType,
    
    @Schema(description = "Song form/structure (AABA, verse-chorus-bridge, etc.)", example = "AABA", required = true)
    @NotBlank(message = "Song form must not be blank")
    String songForm,
    
    @Schema(description = "Harmony style (SIMPLE, POP, JAZZ_STANDARD, JAZZ_MODERN)", example = "JAZZ_STANDARD")
    String style,
    
    @Schema(description = "Chord complexity level (TRIADS, SEVENTH_CHORDS, NINTHS, FULL_EXTENSIONS)", example = "SEVENTH_CHORDS")
    String complexity,
    
    @Schema(description = "Modulation frequency (NONE, LOW, MEDIUM, HIGH)", example = "MEDIUM")
    String modulationFrequency
) {
    
    /**
     * Constructor with default values.
     */
    public ProgressionGenerationRequest {
        if (style == null || style.isBlank()) {
            style = "JAZZ_STANDARD";
        }
        if (complexity == null || complexity.isBlank()) {
            complexity = "SEVENTH_CHORDS";
        }
        if (modulationFrequency == null || modulationFrequency.isBlank()) {
            modulationFrequency = "MEDIUM";
        }
    }
}
