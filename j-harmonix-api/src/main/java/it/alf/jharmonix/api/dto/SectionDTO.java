package it.alf.jharmonix.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import it.alf.jharmonix.core.model.Progression;

import java.util.List;

/**
 * DTO representing a section of a progression.
 */
@Schema(description = "A labeled section containing chords")
public record SectionDTO(
    
    @Schema(description = "Section label", example = "Verse A")
    String label,
    
    @Schema(description = "List of chords in this section")
    List<ChordDTO> chords,
    
    @Schema(description = "Number of bars/measures", example = "8")
    int bars
) {
    
    /**
     * Convert from domain Progression to DTO.
     */
    public static SectionDTO fromProgression(Progression progression) {
        List<ChordDTO> chordDTOs = progression.getChords().stream()
            .map(ChordDTO::fromChord)
            .toList();
        
        return new SectionDTO(
            progression.getSectionLabel(),
            chordDTOs,
            chordDTOs.size()
        );
    }
}
