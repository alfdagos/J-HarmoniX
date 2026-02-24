package it.alf.jharmonix.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import it.alf.jharmonix.core.model.Chord;

/**
 * DTO representing a chord.
 */
@Schema(description = "A musical chord")
public record ChordDTO(
    
    @Schema(description = "Chord symbol in standard notation", example = "Cmaj7")
    String symbol,
    
    @Schema(description = "Root note", example = "C")
    String root,
    
    @Schema(description = "Chord quality", example = "MAJOR_SEVENTH")
    String quality
) {
    
    /**
     * Convert from domain Chord to DTO.
     */
    public static ChordDTO fromChord(Chord chord) {
        return new ChordDTO(
            chord.toString(),
            chord.getRoot().toString(),
            chord.getQuality().name()
        );
    }
}
