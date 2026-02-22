package it.alf.jharmonix.core.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Chord")
class ChordTest {

    @Test
    @DisplayName("Cmaj7 materialises the correct notes")
    void cmaj7Notes() {
        Chord cmaj7 = Chord.of(Note.fromName("C"), ChordQuality.MAJOR_SEVENTH);
        List<Note> notes = cmaj7.notes();
        assertThat(notes).containsExactly(
            Note.fromName("C"),
            Note.fromName("E"),
            Note.fromName("G"),
            Note.fromName("B")
        );
    }

    @Test
    @DisplayName("Dm7 materialises the correct notes")
    void dm7Notes() {
        Chord dm7 = Chord.of(Note.fromName("D"), ChordQuality.MINOR_SEVENTH);
        assertThat(dm7.notes()).containsExactly(
            Note.fromName("D"),
            Note.fromName("F"),
            Note.fromName("A"),
            Note.fromName("C")
        );
    }

    @Test
    @DisplayName("G7 tritone substitution is Db7")
    void tritoneSubstitution() {
        Chord g7 = Chord.of(Note.fromName("G"), ChordQuality.DOMINANT_SEVENTH);
        Chord sub = g7.tritoneSubstitution();
        assertThat(sub.getRoot()).isEqualTo(Note.fromName("Db"));
        assertThat(sub.getQuality()).isEqualTo(ChordQuality.DOMINANT_SEVENTH);
    }

    @Test
    @DisplayName("transpose shifts root correctly")
    void transpose() {
        Chord cmaj7 = Chord.of(Note.fromName("C"), ChordQuality.MAJOR_SEVENTH);
        Chord gmaj7 = cmaj7.transpose(7);
        assertThat(gmaj7.getRoot()).isEqualTo(Note.fromName("G"));
        assertThat(gmaj7.getQuality()).isEqualTo(ChordQuality.MAJOR_SEVENTH);
    }

    @Test
    @DisplayName("toString produces lead-sheet notation")
    void toStringNotation() {
        assertThat(Chord.of(Note.fromName("C"), ChordQuality.MAJOR_SEVENTH).toString()).isEqualTo("Cmaj7");
        assertThat(Chord.of(Note.fromName("D"), ChordQuality.MINOR_SEVENTH).toString()).isEqualTo("Dm7");
        assertThat(Chord.of(Note.fromName("G"), ChordQuality.DOMINANT_SEVENTH).toString()).isEqualTo("G7");
    }

    @Test
    @DisplayName("equals and hashCode based on root + quality")
    void equalsAndHashCode() {
        Chord a = Chord.of(Note.fromName("C"), ChordQuality.MAJOR_SEVENTH);
        Chord b = Chord.of(Note.fromName("C"), ChordQuality.MAJOR_SEVENTH);
        assertThat(a).isEqualTo(b);
        assertThat(a.hashCode()).isEqualTo(b.hashCode());
    }

    @Test
    @DisplayName("constructor rejects null root")
    void rejectsNullRoot() {
        assertThatNullPointerException()
            .isThrownBy(() -> Chord.of(null, ChordQuality.MAJOR_SEVENTH));
    }
}
