package it.alf.jharmonix.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import it.alf.jharmonix.core.model.Chord;

/**
 * DTO representing a chord.
 */
@Schema(
    description = "A musical chord with symbol, root note, and quality",
    example = """
        {
          "symbol": "Cmaj7",
          "root": "C",
          "quality": "MAJOR_SEVENTH"
        }
        """
)
public record ChordDTO(
    
    @Schema(
        description = """
            Chord symbol in standard jazz notation.
            Examples: Cmaj7 (C major seventh), Dm7 (D minor seventh), G7 (G dominant seventh),
            Bm7b5 (B half-diminished), F#dim7 (F# diminished seventh), Asus4 (A suspended fourth),
            Eb9#11 (Eb dominant ninth with raised eleventh)
            """,
        example = "Cmaj7"
    )
    String symbol,
    
    @Schema(
        description = "Root note of the chord. Supports sharp (#) and flat (b) notation.",
        example = "C"
    )
    String root,
    
    @Schema(
        description = """
            Chord quality/type defining the interval structure.
            Common qualities: MAJOR, MINOR, DOMINANT, MAJOR_SEVENTH, MINOR_SEVENTH,
            DOMINANT_SEVENTH, HALF_DIMINISHED, DIMINISHED, AUGMENTED, SUS4, SUS2,
            and extended qualities with 9th, 11th, 13th and alterations.
            """,
        example = "MAJOR_SEVENTH"
    )
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
