package it.alf.jharmonix.core.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Progression")
class ProgressionTest {

    private Chord cm7() { return Chord.of(Note.fromName("C"), ChordQuality.MAJOR_SEVENTH); }
    private Chord dm7() { return Chord.of(Note.fromName("D"), ChordQuality.MINOR_SEVENTH); }
    private Chord g7()  { return Chord.of(Note.fromName("G"), ChordQuality.DOMINANT_SEVENTH); }

    @Test
    @DisplayName("builder creates progression in order")
    void builderOrder() {
        Progression p = Progression.builder().add(dm7()).add(g7()).add(cm7()).build();
        assertThat(p.getChords()).containsExactly(dm7(), g7(), cm7());
    }

    @Test
    @DisplayName("builder rejects empty progression")
    void builderRejectsEmpty() {
        assertThatIllegalStateException().isThrownBy(() -> Progression.builder().build());
    }

    @Test
    @DisplayName("transpose shifts all chords")
    void transpose() {
        Progression p = Progression.builder().add(dm7()).add(g7()).add(cm7()).build();
        Progression t = p.transpose(2);
        assertThat(t.get(0).getRoot()).isEqualTo(Note.fromName("E"));
        assertThat(t.get(1).getRoot()).isEqualTo(Note.fromName("A"));
        assertThat(t.get(2).getRoot()).isEqualTo(Note.fromName("D"));
    }

    @Test
    @DisplayName("toString includes bar lines")
    void toStringBarLines() {
        Progression p = Progression.builder().add(dm7()).add(g7()).add(cm7()).label("Test").build();
        String s = p.toString();
        assertThat(s).contains("[Test]");
        assertThat(s).contains("| Dm7 |");
        assertThat(s).contains("| G7 |");
        assertThat(s).contains("| Cmaj7 |");
    }
}
