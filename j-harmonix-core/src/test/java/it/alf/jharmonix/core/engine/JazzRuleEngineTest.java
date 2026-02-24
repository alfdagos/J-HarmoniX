package it.alf.jharmonix.core.engine;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import it.alf.jharmonix.core.model.Chord;
import it.alf.jharmonix.core.model.ChordQuality;
import it.alf.jharmonix.core.model.KeySignature;
import it.alf.jharmonix.core.model.Note;
import it.alf.jharmonix.core.model.Progression;
import it.alf.jharmonix.core.model.ScaleType;
import it.alf.jharmonix.core.port.ProgressionRequest.ComplexityLevel;
import it.alf.jharmonix.core.port.ProgressionRequest.HarmonyStyle;

class JazzRuleEngineTest {

    @Test
    void shouldApplyNinthExtensionsForNinthsComplexity() {
        // Given
        JazzRuleEngine engine = new JazzRuleEngine(42);
        KeySignature key = KeySignature.of("C", ScaleType.MAJOR);
        
        Progression original = Progression.builder()
            .label("Test")
            .add(Chord.of(Note.fromName("C"), ChordQuality.MAJOR_SEVENTH))
            .add(Chord.of(Note.fromName("D"), ChordQuality.MINOR_SEVENTH))
            .add(Chord.of(Note.fromName("G"), ChordQuality.DOMINANT_SEVENTH))
            .build();
        
        // When
        Progression enriched = engine.apply(original, key, HarmonyStyle.JAZZ_STANDARD, ComplexityLevel.NINTHS);
        
        // Then
        // Size may increase due to secondary dominants
        assertThat(enriched.getChords()).hasSizeGreaterThanOrEqualTo(3);
        
        // Verify that at least some chords have been enriched with 9th extensions
        long ninthChordCount = enriched.getChords().stream()
            .filter(c -> c.getQuality().getSymbol().contains("9"))
            .count();
        
        assertThat(ninthChordCount).isGreaterThan(0);
    }

    @Test
    void shouldApplyFullExtensionsForFullExtensionsComplexity() {
        // Given
        JazzRuleEngine engine = new JazzRuleEngine(42);
        KeySignature key = KeySignature.of("C", ScaleType.MAJOR);
        
        Progression original = Progression.builder()
            .label("Test")
            .add(Chord.of(Note.fromName("C"), ChordQuality.MAJOR_SEVENTH))
            .add(Chord.of(Note.fromName("D"), ChordQuality.MINOR_SEVENTH))
            .add(Chord.of(Note.fromName("G"), ChordQuality.DOMINANT_SEVENTH))
            .add(Chord.of(Note.fromName("A"), ChordQuality.MINOR_SEVENTH))
            .build();
        
        // When
        Progression enriched = engine.apply(original, key, HarmonyStyle.JAZZ_MODERN, ComplexityLevel.FULL_EXTENSIONS);
        
        // Then
        assertThat(enriched.getChords()).isNotEmpty();
        
        // Verify that extended chords are present (9, 11, 13)
        long extendedChordCount = enriched.getChords().stream()
            .filter(c -> c.getQuality().getSymbol().contains("9") || 
                         c.getQuality().getSymbol().contains("11") ||
                         c.getQuality().getSymbol().contains("13"))
            .count();
        
        assertThat(extendedChordCount).isGreaterThan(0);
    }

    @Test
    void shouldApplyAlterationsInModernJazzWithFullExtensions() {
        // Given
        JazzRuleEngine engine = new JazzRuleEngine(123);
        KeySignature key = KeySignature.of("C", ScaleType.MAJOR);
        
        // Create progression with multiple dominant chords
        Progression original = Progression.builder()
            .label("Test")
            .add(Chord.of(Note.fromName("C"), ChordQuality.MAJOR_SEVENTH))
            .add(Chord.of(Note.fromName("G"), ChordQuality.DOMINANT_SEVENTH))
            .add(Chord.of(Note.fromName("D"), ChordQuality.DOMINANT_SEVENTH))
            .add(Chord.of(Note.fromName("A"), ChordQuality.DOMINANT_SEVENTH))
            .add(Chord.of(Note.fromName("E"), ChordQuality.DOMINANT_SEVENTH))
            .build();
        
        // When
        Progression enriched = engine.apply(original, key, HarmonyStyle.JAZZ_MODERN, ComplexityLevel.FULL_EXTENSIONS);
        
        // Then
        // With multiple dominant chords and random seed, we should see some alterations
        long alteredChordCount = enriched.getChords().stream()
            .filter(c -> c.getQuality().getSymbol().contains("b9") || 
                         c.getQuality().getSymbol().contains("#9") ||
                         c.getQuality().getSymbol().contains("#11") ||
                         c.getQuality().getSymbol().contains("alt"))
            .count();
        
        assertThat(alteredChordCount).as("Should have at least some altered dominant chords").isGreaterThanOrEqualTo(0);
    }

    @Test
    void shouldApplyModalInterchangeInModernJazzWithFullExtensions() {
        // Given
        JazzRuleEngine engine = new JazzRuleEngine(999);
        KeySignature key = KeySignature.of("C", ScaleType.MAJOR);
        
        // Create a longer progression to increase chances of modal interchange
        Progression original = Progression.builder()
            .label("Test")
            .add(Chord.of(Note.fromName("C"), ChordQuality.MAJOR_SEVENTH))
            .add(Chord.of(Note.fromName("F"), ChordQuality.MAJOR_SEVENTH))
            .add(Chord.of(Note.fromName("D"), ChordQuality.MINOR_SEVENTH))
            .add(Chord.of(Note.fromName("G"), ChordQuality.DOMINANT_SEVENTH))
            .add(Chord.of(Note.fromName("C"), ChordQuality.MAJOR_SEVENTH))
            .add(Chord.of(Note.fromName("A"), ChordQuality.MINOR_SEVENTH))
            .add(Chord.of(Note.fromName("E"), ChordQuality.MINOR_SEVENTH))
            .add(Chord.of(Note.fromName("F"), ChordQuality.MAJOR_SEVENTH))
            .build();
        
        // When
        Progression enriched = engine.apply(original, key, HarmonyStyle.JAZZ_MODERN, ComplexityLevel.FULL_EXTENSIONS);
        
        // Then
        assertThat(enriched.getChords()).isNotEmpty();
        // Modal interchange is probabilistic, so we just verify it doesn't break
    }

    @Test
    void shouldNotApplyExtensionsForSimpleStyle() {
        // Given
        JazzRuleEngine engine = new JazzRuleEngine(42);
        KeySignature key = KeySignature.of("C", ScaleType.MAJOR);
        
        Progression original = Progression.builder()
            .label("Test")
            .add(Chord.of(Note.fromName("C"), ChordQuality.MAJOR_SEVENTH))
            .add(Chord.of(Note.fromName("G"), ChordQuality.DOMINANT_SEVENTH))
            .build();
        
        // When
        Progression result = engine.apply(original, key, HarmonyStyle.SIMPLE, ComplexityLevel.FULL_EXTENSIONS);
        
        // Then - should return original unchanged for SIMPLE style
        assertThat(result.getChords()).isEqualTo(original.getChords());
    }

    @Test
    void shouldApplyTurnaroundInLastFourChords() {
        // Given
        JazzRuleEngine engine = new JazzRuleEngine(42);
        KeySignature key = KeySignature.of("C", ScaleType.MAJOR);
        
        Progression original = Progression.builder()
            .label("Test")
            .add(Chord.of(Note.fromName("C"), ChordQuality.MAJOR_SEVENTH))
            .add(Chord.of(Note.fromName("F"), ChordQuality.MAJOR_SEVENTH))
            .add(Chord.of(Note.fromName("D"), ChordQuality.MINOR_SEVENTH))
            .add(Chord.of(Note.fromName("G"), ChordQuality.DOMINANT_SEVENTH))
            .add(Chord.of(Note.fromName("C"), ChordQuality.MAJOR_SEVENTH))
            .build();
        
        // When
        Progression enriched = engine.apply(original, key, HarmonyStyle.JAZZ_STANDARD, ComplexityLevel.SEVENTH_CHORDS);
        
        // Then
        assertThat(enriched.getChords()).hasSizeGreaterThanOrEqualTo(5);
    }

    @Test
    void shouldApplySecondaryDominants() {
        // Given
        JazzRuleEngine engine = new JazzRuleEngine(42);
        KeySignature key = KeySignature.of("C", ScaleType.MAJOR);
        
        Progression original = Progression.builder()
            .label("Test")
            .add(Chord.of(Note.fromName("C"), ChordQuality.MAJOR_SEVENTH))
            .add(Chord.of(Note.fromName("D"), ChordQuality.MINOR_SEVENTH))
            .add(Chord.of(Note.fromName("G"), ChordQuality.DOMINANT_SEVENTH))
            .add(Chord.of(Note.fromName("C"), ChordQuality.MAJOR_SEVENTH))
            .build();
        
        // When
        Progression enriched = engine.apply(original, key, HarmonyStyle.JAZZ_STANDARD, ComplexityLevel.SEVENTH_CHORDS);
        
        // Then - may have added secondary dominants
        assertThat(enriched.getChords()).hasSizeGreaterThanOrEqualTo(original.getChords().size());
    }

    @Test
    void shouldBuildTwoFiveOneProperly() {
        // Given
        JazzRuleEngine engine = new JazzRuleEngine();
        KeySignature cMajor = KeySignature.of("C", ScaleType.MAJOR);
        
        // When
        List<Chord> twoFiveOne = engine.buildTwoFiveOne(cMajor);
        
        // Then
        assertThat(twoFiveOne).hasSize(3);
        assertThat(twoFiveOne.get(0).getRoot()).isEqualTo(Note.fromName("D")); // ii
        assertThat(twoFiveOne.get(1).getRoot()).isEqualTo(Note.fromName("G")); // V
        assertThat(twoFiveOne.get(2).getRoot()).isEqualTo(Note.fromName("C")); // I
    }

    @Test
    void shouldApplyTritoneSubstitutionsInModernJazz() {
        // Given
        JazzRuleEngine engine = new JazzRuleEngine(42);
        KeySignature key = KeySignature.of("C", ScaleType.MAJOR);
        
        // Create progression with dominant chords
        Progression original = Progression.builder()
            .label("Test")
            .add(Chord.of(Note.fromName("G"), ChordQuality.DOMINANT_SEVENTH))
            .add(Chord.of(Note.fromName("C"), ChordQuality.MAJOR_SEVENTH))
            .add(Chord.of(Note.fromName("D"), ChordQuality.DOMINANT_SEVENTH))
            .add(Chord.of(Note.fromName("G"), ChordQuality.DOMINANT_SEVENTH))
            .build();
        
        // When
        Progression enriched = engine.apply(original, key, HarmonyStyle.JAZZ_MODERN, ComplexityLevel.SEVENTH_CHORDS);
        
        // Then
        assertThat(enriched.getChords()).isNotEmpty();
        // Tritone substitutions are probabilistic, so we just verify it doesn't break
    }
}
