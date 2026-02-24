package it.alf.jharmonix.api.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import it.alf.jharmonix.core.model.Progression;

/**
 * DTO representing a section of a progression.
 */
@Schema(
    description = "A labeled section containing a sequence of chords",
    example = """
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
        """
)
public record SectionDTO(
    
    @Schema(
        description = """
            Section label identifying the structural component.
            Common labels: "A Section 1", "A Section 2", "B Section" (bridge),
            "Verse A", "Chorus", "Bridge", etc.
            """,
        example = "A Section 1"
    )
    String label,
    
    @Schema(
        description = "Ordered list of chords forming the harmonic progression of this section"
    )
    List<ChordDTO> chords,
    
    @Schema(
        description = """
            Number of measures/bars in this section. Each chord typically occupies
            one bar, though the exact duration depends on the arrangement.
            """,
        example = "8"
    )
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
