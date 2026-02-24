package it.alf.jharmonix.core.model;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    @Test
    @DisplayName("inversions() returns root position plus all inversions")
    void inversionsReturnsCorrectCount() {
        // Given - C major triad (3 notes)
        Chord cMajor = Chord.of(Note.fromName("C"), ChordQuality.MAJOR_TRIAD);
        
        // When
        List<List<Note>> inversions = cMajor.inversions();
        
        // Then - should have 3 inversions (root, 1st, 2nd)
        assertThat(inversions).hasSize(3);
    }

    @Test
    @DisplayName("first inversion of C major puts E in bass")
    void firstInversionCMajor() {
        // Given
        Chord cMajor = Chord.of(Note.fromName("C"), ChordQuality.MAJOR_TRIAD);
        
        // When
        List<List<Note>> inversions = cMajor.inversions();
        List<Note> firstInversion = inversions.get(1);
        
        // Then - C moves up an octave: E, G, C+12
        assertThat(firstInversion).hasSize(3);
        assertThat(firstInversion.get(0)).isEqualTo(Note.fromName("E")); // bass
        assertThat(firstInversion.get(1)).isEqualTo(Note.fromName("G"));
        assertThat(firstInversion.get(2)).isEqualTo(Note.fromName("C")); // raised octave
    }

    @Test
    @DisplayName("second inversion of C major puts G in bass")
    void secondInversionCMajor() {
        // Given
        Chord cMajor = Chord.of(Note.fromName("C"), ChordQuality.MAJOR_TRIAD);
        
        // When
        List<List<Note>> inversions = cMajor.inversions();
        List<Note> secondInversion = inversions.get(2);
        
        // Then - E moves up: G, C+12, E+12
        assertThat(secondInversion).hasSize(3);
        assertThat(secondInversion.get(0)).isEqualTo(Note.fromName("G")); // bass
        assertThat(secondInversion.get(1)).isEqualTo(Note.fromName("C")); // raised
        assertThat(secondInversion.get(2)).isEqualTo(Note.fromName("E")); // raised
    }

    @Test
    @DisplayName("inversions of Cmaj7 includes third inversion")
    void inversionsCmaj7() {
        // Given - 4-note chord
        Chord cmaj7 = Chord.of(Note.fromName("C"), ChordQuality.MAJOR_SEVENTH);
        
        // When
        List<List<Note>> inversions = cmaj7.inversions();
        
        // Then - should have 4 inversions
        assertThat(inversions).hasSize(4);
        
        // Root position: C E G B
        assertThat(inversions.get(0)).containsExactly(
            Note.fromName("C"),
            Note.fromName("E"),
            Note.fromName("G"),
            Note.fromName("B")
        );
        
        // First inversion: E G B C+12
        assertThat(inversions.get(1).get(0)).isEqualTo(Note.fromName("E"));
        
        // Second inversion: G B C+12 E+12
        assertThat(inversions.get(2).get(0)).isEqualTo(Note.fromName("G"));
        
        // Third inversion: B C+12 E+12 G+12
        assertThat(inversions.get(3).get(0)).isEqualTo(Note.fromName("B"));
    }

    @Test
    @DisplayName("inversions preserves original chord notes")
    void inversionsDoesNotMutateOriginal() {
        // Given
        Chord cMajor = Chord.of(Note.fromName("C"), ChordQuality.MAJOR_TRIAD);
        List<Note> originalNotes = cMajor.notes();
        
        // When
        List<List<Note>> inversions = cMajor.inversions();
        
        // Then - original chord unchanged
        assertThat(cMajor.notes()).isEqualTo(originalNotes);
        
        // And root position matches original
        assertThat(inversions.get(0)).isEqualTo(originalNotes);
    }
}
