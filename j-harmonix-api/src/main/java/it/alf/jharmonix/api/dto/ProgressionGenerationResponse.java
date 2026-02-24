package it.alf.jharmonix.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import it.alf.jharmonix.core.model.Progression;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for harmony progression generation response.
 */
@Schema(description = "Generated harmonic progression result")
public record ProgressionGenerationResponse(
    
    @Schema(description = "Key signature", example = "C MAJOR")
    String key,
    
    @Schema(description = "Song form", example = "AABA")
    String form,
    
    @Schema(description = "Harmony style used", example = "JAZZ_STANDARD")
    String style,
    
    @Schema(description = "List of sections with chords")
    List<SectionDTO> sections,
    
    @Schema(description = "Total number of chords", example = "32")
    int totalChords,
    
    @Schema(description = "Generation timestamp")
    LocalDateTime timestamp
) {
    
    /**
     * Create response from domain progressions.
     */
    public static ProgressionGenerationResponse fromProgressions(
            List<Progression> progressions,
            String keyStr,
            String formStr,
            String styleStr) {
        
        List<SectionDTO> sectionDTOs = progressions.stream()
            .map(SectionDTO::fromProgression)
            .toList();
        
        int totalChords = progressions.stream()
            .mapToInt(p -> p.getChords().size())
            .sum();
        
        return new ProgressionGenerationResponse(
            keyStr,
            formStr,
            styleStr,
            sectionDTOs,
            totalChords,
            LocalDateTime.now()
        );
    }
}
