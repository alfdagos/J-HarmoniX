package it.alf.jharmonix.core.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class IntervalTest {

    @Test
    void shouldCreateIntervalWithPositiveSemitones() {
        // Given / When
        Interval interval = Interval.of(5, "Perfect Fourth");
        
        // Then
        assertThat(interval.getSemitones()).isEqualTo(5);
        assertThat(interval.getName()).isEqualTo("Perfect Fourth");
    }

    @Test
    void shouldRejectNegativeSemitones() {
        assertThatThrownBy(() -> Interval.of(-1, "Invalid"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Semitones must be >= 0");
    }

    @Test
    void shouldRejectNullName() {
        assertThatThrownBy(() -> Interval.of(5, null))
            .isInstanceOf(NullPointerException.class)
            .hasMessageContaining("Interval name must not be null");
    }

    @Test
    void shouldHaveCorrectToString() {
        // Given
        Interval interval = Interval.of(7, "P5");
        
        // When
        String result = interval.toString();
        
        // Then
        assertThat(result).isEqualTo("P5(7st)");
    }

    @Test
    void shouldImplementEqualsCorrectly() {
        // Given
        Interval interval1 = Interval.of(5, "P4");
        Interval interval2 = Interval.of(5, "P4");
        Interval interval3 = Interval.of(7, "P5");
        Interval interval4 = Interval.of(5, "dim5");
        
        // Then
        assertThat(interval1).isEqualTo(interval2);
        assertThat(interval1).isNotEqualTo(interval3); // different semitones
        assertThat(interval1).isNotEqualTo(interval4); // different name
        assertThat(interval1).isNotEqualTo(null);
        assertThat(interval1).isNotEqualTo("string");
    }

    @Test
    void shouldImplementHashCodeConsistently() {
        // Given
        Interval interval1 = Interval.of(5, "P4");
        Interval interval2 = Interval.of(5, "P4");
        
        // Then
        assertThat(interval1.hashCode()).isEqualTo(interval2.hashCode());
    }

    // Test predefined interval constants
    @Test
    void shouldHaveCorrectUnisonAndOctave() {
        assertThat(Interval.UNISON.getSemitones()).isEqualTo(0);
        assertThat(Interval.UNISON.getName()).isEqualTo("P1");
        
        assertThat(Interval.OCTAVE.getSemitones()).isEqualTo(12);
        assertThat(Interval.OCTAVE.getName()).isEqualTo("P8");
    }

    @Test
    void shouldHaveCorrectSeconds() {
        assertThat(Interval.MINOR_SECOND.getSemitones()).isEqualTo(1);
        assertThat(Interval.MINOR_SECOND.getName()).isEqualTo("m2");
        
        assertThat(Interval.MAJOR_SECOND.getSemitones()).isEqualTo(2);
        assertThat(Interval.MAJOR_SECOND.getName()).isEqualTo("M2");
    }

    @Test
    void shouldHaveCorrectThirds() {
        assertThat(Interval.MINOR_THIRD.getSemitones()).isEqualTo(3);
        assertThat(Interval.MINOR_THIRD.getName()).isEqualTo("m3");
        
        assertThat(Interval.MAJOR_THIRD.getSemitones()).isEqualTo(4);
        assertThat(Interval.MAJOR_THIRD.getName()).isEqualTo("M3");
    }

    @Test
    void shouldHaveCorrectFourths() {
        assertThat(Interval.PERFECT_FOURTH.getSemitones()).isEqualTo(5);
        assertThat(Interval.PERFECT_FOURTH.getName()).isEqualTo("P4");
        
        assertThat(Interval.AUG_FOURTH.getSemitones()).isEqualTo(6);
        assertThat(Interval.AUG_FOURTH.getName()).isEqualTo("A4");
    }

    @Test
    void shouldHaveCorrectFifths() {
        assertThat(Interval.DIM_FIFTH.getSemitones()).isEqualTo(6);
        assertThat(Interval.DIM_FIFTH.getName()).isEqualTo("d5");
        
        assertThat(Interval.PERFECT_FIFTH.getSemitones()).isEqualTo(7);
        assertThat(Interval.PERFECT_FIFTH.getName()).isEqualTo("P5");
        
        assertThat(Interval.AUG_FIFTH.getSemitones()).isEqualTo(8);
        assertThat(Interval.AUG_FIFTH.getName()).isEqualTo("A5");
    }

    @Test
    void shouldHaveCorrectSixths() {
        assertThat(Interval.MINOR_SIXTH.getSemitones()).isEqualTo(8);
        assertThat(Interval.MINOR_SIXTH.getName()).isEqualTo("m6");
        
        assertThat(Interval.MAJOR_SIXTH.getSemitones()).isEqualTo(9);
        assertThat(Interval.MAJOR_SIXTH.getName()).isEqualTo("M6");
    }

    @Test
    void shouldHaveCorrectSevenths() {
        assertThat(Interval.DIM_SEVENTH.getSemitones()).isEqualTo(9);
        assertThat(Interval.DIM_SEVENTH.getName()).isEqualTo("d7");
        
        assertThat(Interval.MINOR_SEVENTH.getSemitones()).isEqualTo(10);
        assertThat(Interval.MINOR_SEVENTH.getName()).isEqualTo("m7");
        
        assertThat(Interval.MAJOR_SEVENTH.getSemitones()).isEqualTo(11);
        assertThat(Interval.MAJOR_SEVENTH.getName()).isEqualTo("M7");
    }

    @Test
    void shouldHaveCorrectExtensions() {
        // Ninths
        assertThat(Interval.MINOR_NINTH.getSemitones()).isEqualTo(13);
        assertThat(Interval.MINOR_NINTH.getName()).isEqualTo("m9");
        
        assertThat(Interval.MAJOR_NINTH.getSemitones()).isEqualTo(14);
        assertThat(Interval.MAJOR_NINTH.getName()).isEqualTo("M9");
        
        assertThat(Interval.AUG_NINTH.getSemitones()).isEqualTo(15);
        assertThat(Interval.AUG_NINTH.getName()).isEqualTo("A9");
        
        // Elevenths
        assertThat(Interval.PERFECT_ELEVENTH.getSemitones()).isEqualTo(17);
        assertThat(Interval.PERFECT_ELEVENTH.getName()).isEqualTo("P11");
        
        assertThat(Interval.AUG_ELEVENTH.getSemitones()).isEqualTo(18);
        assertThat(Interval.AUG_ELEVENTH.getName()).isEqualTo("A11");
        
        // Thirteenths
        assertThat(Interval.MINOR_THIRTEENTH.getSemitones()).isEqualTo(20);
        assertThat(Interval.MINOR_THIRTEENTH.getName()).isEqualTo("m13");
        
        assertThat(Interval.MAJOR_THIRTEENTH.getSemitones()).isEqualTo(21);
        assertThat(Interval.MAJOR_THIRTEENTH.getName()).isEqualTo("M13");
    }

    @Test
    void shouldCalculateIntervalFromNotes() {
        // Given
        Note c = Note.fromName("C");
        Note e = Note.fromName("E");
        Note g = Note.fromName("G");
        
        // When
        int cToE = c.intervalTo(e);
        int cToG = c.intervalTo(g);
        int eToG = e.intervalTo(g);
        
        // Then
        assertThat(cToE).isEqualTo(Interval.MAJOR_THIRD.getSemitones());
        assertThat(cToG).isEqualTo(Interval.PERFECT_FIFTH.getSemitones());
        assertThat(eToG).isEqualTo(3); // minor third
    }

    @Test
    void shouldConstructChordFromIntervals() {
        // Given - Build C Major chord using intervals
        Note root = Note.fromName("C");
        
        // When - Apply major third (C -> E) and perfect fifth (C -> G)
        Note third = root.transpose(Interval.MAJOR_THIRD.getSemitones());
        Note fifth = root.transpose(Interval.PERFECT_FIFTH.getSemitones());
        
        // Then
        assertThat(third).isEqualTo(Note.fromName("E"));
        assertThat(fifth).isEqualTo(Note.fromName("G"));
        
        // Verify chord
        Chord cMajor = Chord.of(root, ChordQuality.MAJOR_TRIAD);
        assertThat(cMajor.notes()).containsExactly(
            Note.fromName("C"),
            Note.fromName("E"),
            Note.fromName("G")
        );
    }

    @Test
    void shouldConstructDominantSeventhFromIntervals() {
        // Given - Build G7 chord using intervals
        Note root = Note.fromName("G");
        
        // When
        Note third = root.transpose(Interval.MAJOR_THIRD.getSemitones());
        Note fifth = root.transpose(Interval.PERFECT_FIFTH.getSemitones());
        Note seventh = root.transpose(Interval.MINOR_SEVENTH.getSemitones());
        
        // Then
        assertThat(third).isEqualTo(Note.fromName("B"));
        assertThat(fifth).isEqualTo(Note.fromName("D"));
        assertThat(seventh).isEqualTo(Note.fromName("F"));
        
        // Verify chord
        Chord g7 = Chord.of(root, ChordQuality.DOMINANT_SEVENTH);
        assertThat(g7.notes()).containsExactly(
            Note.fromName("G"),
            Note.fromName("B"),
            Note.fromName("D"),
            Note.fromName("F")
        );
    }

    @Test
    void shouldHandleCompoundIntervals() {
        // Given - intervals spanning more than an octave
        Note c = Note.fromName("C");
        
        // When - apply 9th, 11th, 13th
        Note ninth = c.transpose(Interval.MAJOR_NINTH.getSemitones());
        Note eleventh = c.transpose(Interval.PERFECT_ELEVENTH.getSemitones());
        Note thirteenth = c.transpose(Interval.MAJOR_THIRTEENTH.getSemitones());
        
        // Then - should wrap around octave correctly
        assertThat(ninth).isEqualTo(Note.fromName("D")); // 14 % 12 = 2
        assertThat(eleventh).isEqualTo(Note.fromName("F")); // 17 % 12 = 5
        assertThat(thirteenth).isEqualTo(Note.fromName("A")); // 21 % 12 = 9
    }
}
