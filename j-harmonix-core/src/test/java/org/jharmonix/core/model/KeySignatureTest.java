package org.jharmonix.core.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("KeySignature")
class KeySignatureTest {

    @Test
    @DisplayName("C Major relative minor is A Natural Minor")
    void relativeMinor() {
        KeySignature cMaj = KeySignature.major("C");
        KeySignature rel  = cMaj.relativeMinor();
        assertThat(rel.getTonic().toString()).isEqualTo("A");
        assertThat(rel.getScaleType()).isEqualTo(ScaleType.NATURAL_MINOR);
    }

    @Test
    @DisplayName("dominant key of C Major is G Major")
    void dominantKey() {
        KeySignature cMaj = KeySignature.major("C");
        assertThat(cMaj.dominantKey().getTonic().toString()).isEqualTo("G");
    }

    @Test
    @DisplayName("subdominant key of C Major is F Major")
    void subdominantKey() {
        KeySignature cMaj = KeySignature.major("C");
        assertThat(cMaj.subdominantKey().getTonic().toString()).isEqualTo("F");
    }

    @Test
    @DisplayName("relativeMinor throws on non-major key")
    void relativeMinorThrowsForMinor() {
        KeySignature aMin = KeySignature.naturalMinor("A");
        assertThatIllegalStateException().isThrownBy(aMin::relativeMinor);
    }

    @Test
    @DisplayName("diatonicChord(4) of C Major is G7")
    void diatonicDominant() {
        KeySignature cMaj = KeySignature.major("C");
        assertThat(cMaj.diatonicChord(4).toString()).isEqualTo("G7");
    }

    @Test
    @DisplayName("isMajor / isMinor flags are correct")
    void modeFlags() {
        assertThat(KeySignature.major("C").isMajor()).isTrue();
        assertThat(KeySignature.major("C").isMinor()).isFalse();
        assertThat(KeySignature.naturalMinor("A").isMinor()).isTrue();
        assertThat(KeySignature.naturalMinor("A").isMajor()).isFalse();
    }
}
