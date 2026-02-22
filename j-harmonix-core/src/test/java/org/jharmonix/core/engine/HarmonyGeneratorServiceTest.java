package org.jharmonix.core.engine;

import org.jharmonix.core.model.*;
import org.jharmonix.core.port.ProgressionRequest;
import org.jharmonix.core.port.ProgressionRequest.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("HarmonyGeneratorService – integration")
class HarmonyGeneratorServiceTest {

    private final HarmonyGeneratorService service = HarmonyGeneratorService.withSeed(42L);

    @Test
    @DisplayName("AABA form produces 4 sections")
    void aabaSectionCount() {
        ProgressionRequest req = ProgressionRequest.builder()
            .tonicName("C")
            .scaleType(ScaleType.MAJOR)
            .songForm("AABA")
            .style(HarmonyStyle.JAZZ_STANDARD)
            .complexity(ComplexityLevel.SEVENTH_CHORDS)
            .modulationFrequency(ModulationFrequency.NONE)
            .build();

        List<Progression> sections = service.generate(req);
        assertThat(sections).hasSize(4);
    }

    @Test
    @DisplayName("All generated chords are non-null and have a valid root")
    void chordsAreValid() {
        ProgressionRequest req = ProgressionRequest.builder()
            .tonicName("F")
            .scaleType(ScaleType.MAJOR)
            .songForm("AABA")
            .style(HarmonyStyle.JAZZ_MODERN)
            .complexity(ComplexityLevel.NINTHS)
            .modulationFrequency(ModulationFrequency.MEDIUM)
            .build();

        List<Progression> sections = service.generate(req);
        sections.forEach(section ->
            section.getChords().forEach(chord -> {
                assertThat(chord).isNotNull();
                assertThat(chord.getRoot()).isNotNull();
                assertThat(chord.getRoot().getValue()).isBetween(0, 11);
            })
        );
    }

    @Test
    @DisplayName("Triads complexity produces only triads in simple style")
    void triadsComplexity() {
        ProgressionRequest req = ProgressionRequest.builder()
            .tonicName("C")
            .scaleType(ScaleType.MAJOR)
            .songForm("AA")
            .style(HarmonyStyle.SIMPLE)
            .complexity(ComplexityLevel.TRIADS)
            .modulationFrequency(ModulationFrequency.NONE)
            .build();

        List<Progression> sections = service.generate(req);
        sections.forEach(section ->
            section.getChords().forEach(chord -> {
                int noteCount = chord.notes().size();
                assertThat(noteCount).isEqualTo(3);
            })
        );
    }

    @Test
    @DisplayName("generate throws on null request")
    void throwsOnNullRequest() {
        assertThatNullPointerException().isThrownBy(() -> service.generate(null));
    }

    @Test
    @DisplayName("Section labels are non-null")
    void sectionLabels() {
        ProgressionRequest req = ProgressionRequest.builder()
            .tonicName("G")
            .scaleType(ScaleType.MAJOR)
            .songForm("AABA")
            .build();

        service.generate(req).forEach(p ->
            assertThat(p.getSectionLabel()).isNotNull()
        );
    }
}
