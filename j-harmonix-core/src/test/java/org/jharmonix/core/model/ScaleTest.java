package org.jharmonix.core.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Scale")
class ScaleTest {

    @Test
    @DisplayName("C Major has the correct 7 notes")
    void cMajorNotes() {
        Scale c = Scale.of(Note.fromName("C"), ScaleType.MAJOR);
        assertThat(c.getNotes()).extracting(Note::toString)
            .containsExactly("C", "D", "E", "F", "G", "A", "B");
    }

    @Test
    @DisplayName("A Natural Minor has the correct 7 notes")
    void aNaturalMinorNotes() {
        Scale a = Scale.of(Note.fromName("A"), ScaleType.NATURAL_MINOR);
        assertThat(a.getNotes()).extracting(Note::toString)
            .containsExactly("A", "B", "C", "D", "E", "F", "G");
    }

    @Test
    @DisplayName("C Major diatonic chords match standard jazz voicings")
    void cMajorDiatonicChords() {
        Scale c = Scale.of(Note.fromName("C"), ScaleType.MAJOR);
        // Degrees: I=Cmaj7, II=Dm7, III=Em7, IV=Fmaj7, V=G7, VI=Am7, VII=Bm7b5
        assertThat(c.diatonicChord(0).toString()).isEqualTo("Cmaj7");
        assertThat(c.diatonicChord(1).toString()).isEqualTo("Dm7");
        assertThat(c.diatonicChord(2).toString()).isEqualTo("Em7");
        assertThat(c.diatonicChord(3).toString()).isEqualTo("Fmaj7");
        assertThat(c.diatonicChord(4).toString()).isEqualTo("G7");
        assertThat(c.diatonicChord(5).toString()).isEqualTo("Am7");
        assertThat(c.diatonicChord(6).toString()).isEqualTo("Bm7b5");
    }

    @Test
    @DisplayName("contains returns true for diatonic notes")
    void containsDiatonic() {
        Scale c = Scale.of(Note.fromName("C"), ScaleType.MAJOR);
        assertThat(c.contains(Note.fromName("G"))).isTrue();
        assertThat(c.contains(Note.fromName("F#"))).isFalse();
    }

    @Test
    @DisplayName("degreeOf returns -1 for non-diatonic note")
    void degreeOfNonDiatonic() {
        Scale c = Scale.of(Note.fromName("C"), ScaleType.MAJOR);
        assertThat(c.degreeOf(Note.fromName("F#"))).isEqualTo(-1);
    }

    @Test
    @DisplayName("diatonicChord throws on out-of-range degree")
    void diatonicChordOutOfRange() {
        Scale c = Scale.of(Note.fromName("C"), ScaleType.MAJOR);
        assertThatIllegalArgumentException().isThrownBy(() -> c.diatonicChord(7));
    }

    @Test
    @DisplayName("Blues scale has 6 notes")
    void bluesScaleSize() {
        Scale blues = Scale.of(Note.fromName("C"), ScaleType.BLUES);
        assertThat(blues.size()).isEqualTo(6);
    }
}
