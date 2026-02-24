package it.alf.jharmonix.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import it.alf.jharmonix.core.model.Progression;

/**
 * DTO for harmony progression generation response.
 */
@Schema(
    description = "Generated harmonic progression with sections and metadata",
    example = """
        {
          "key": "C MAJOR",
          "form": "AABA",
          "style": "TRADITIONAL_JAZZ",
          "sections": [
            {
              "label": "A Section 1",
              "chords": [
                {"symbol": "Cmaj7", "root": "C", "quality": "MAJOR_SEVENTH"},
                {"symbol": "Dm7", "root": "D", "quality": "MINOR_SEVENTH"},
                {"symbol": "G7", "root": "G", "quality": "DOMINANT_SEVENTH"},
                {"symbol": "Cmaj7", "root": "C", "quality": "MAJOR_SEVENTH"}
              ],
              "bars": 4
            }
          ],
          "totalChords": 16,
          "timestamp": "2024-01-15T10:30:00"
        }
        """
)
public record ProgressionGenerationResponse(
    
    @Schema(
        description = "Key signature with tonic and scale type",
        example = "C MAJOR"
    )
    String key,
    
    @Schema(
        description = "Song form structure used for generation",
        example = "AABA"
    )
    String form,
    
    @Schema(
        description = "Harmony style applied to the progression",
        example = "TRADITIONAL_JAZZ"
    )
    String style,
    
    @Schema(
        description = "List of sections, each containing labeled chord progressions"
    )
    List<SectionDTO> sections,
    
    @Schema(
        description = "Total number of chords across all sections",
        example = "32"
    )
    int totalChords,
    
    @Schema(
        description = "ISO 8601 timestamp of generation",
        example = "2024-01-15T10:30:00"
    )
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
