package org.jharmonix.core.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Note")
class NoteTest {

    @Test
    @DisplayName("constructor normalises value to [0, 11]")
    void constructorNormalisesValue() {
        assertThat(new Note(12).getValue()).isEqualTo(0);
        assertThat(new Note(-1).getValue()).isEqualTo(11);
        assertThat(new Note(13).getValue()).isEqualTo(1);
    }

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
        "C,  0", "C#, 1", "Db, 1", "D,  2", "Eb, 3", "E,  4",
        "F,  5", "F#, 6", "Gb, 6", "G,  7", "Ab, 8", "A,  9",
        "Bb,10", "B, 11"
    })
    @DisplayName("fromName parses sharp, flat, and natural names")
    void fromNameParsesAllNames(String name, int expected) {
        assertThat(Note.fromName(name).getValue()).isEqualTo(expected);
    }

    @Test
    @DisplayName("fromName throws on invalid name")
    void fromNameThrowsOnInvalid() {
        assertThatIllegalArgumentException().isThrownBy(() -> Note.fromName("X"));
    }

    @Test
    @DisplayName("fromName throws on null")
    void fromNameThrowsOnNull() {
        assertThatNullPointerException().isThrownBy(() -> Note.fromName(null));
    }

    @Test
    @DisplayName("transpose wraps correctly")
    void transposeWraps() {
        Note c = Note.fromName("C");
        assertThat(c.transpose(12)).isEqualTo(c);          // up one octave
        assertThat(c.transpose(-1)).isEqualTo(Note.fromName("B")); // down semitone
        assertThat(c.transpose(7)).isEqualTo(Note.fromName("G"));  // perfect fifth
    }

    @Test
    @DisplayName("intervalTo returns ascending semitone count in [0, 11]")
    void intervalTo() {
        Note c = Note.fromName("C");
        Note g = Note.fromName("G");
        assertThat(c.intervalTo(g)).isEqualTo(7);  // P5 up
        assertThat(g.intervalTo(c)).isEqualTo(5);  // P4 up
    }

    @Test
    @DisplayName("toString returns sharp names; toFlatString returns flat names")
    void displayNames() {
        Note csharp = new Note(1);
        assertThat(csharp.toString()).isEqualTo("C#");
        assertThat(csharp.toFlatString()).isEqualTo("Db");
    }

    @Test
    @DisplayName("equals and hashCode are pitch-class based")
    void equalsAndHashCode() {
        Note cs = Note.fromName("C#");
        Note db = Note.fromName("Db");
        assertThat(cs).isEqualTo(db);
        assertThat(cs.hashCode()).isEqualTo(db.hashCode());
    }
}
