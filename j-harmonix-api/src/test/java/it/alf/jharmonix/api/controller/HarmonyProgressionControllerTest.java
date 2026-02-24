package it.alf.jharmonix.api.controller;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.alf.jharmonix.api.dto.ProgressionGenerationRequest;
import it.alf.jharmonix.core.port.HarmonyGeneratorPort;

/**
 * Integration tests for HarmonyProgressionController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class HarmonyProgressionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HarmonyGeneratorPort harmonyGenerator;

    @Test
    void testGenerateProgressionWithValidRequest() throws Exception {
        ProgressionGenerationRequest request = new ProgressionGenerationRequest(
            "C", "MAJOR", "AABA", "JAZZ_STANDARD", "SEVENTH_CHORDS", "MEDIUM"
        );

        mockMvc.perform(post("/api/v1/progressions/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.key").value("C MAJOR"))
            .andExpect(jsonPath("$.form").value("AABA"))
            .andExpect(jsonPath("$.style").value("JAZZ_STANDARD"))
            .andExpect(jsonPath("$.sections").isArray())
            .andExpect(jsonPath("$.sections", hasSize(greaterThan(0))))
            .andExpect(jsonPath("$.totalChords").isNumber())
            .andExpect(jsonPath("$.totalChords").value(greaterThan(0)))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testGenerateProgressionWithMinimalRequest() throws Exception {
        ProgressionGenerationRequest request = new ProgressionGenerationRequest(
            "F", "MAJOR", "verse-chorus", null, null, null
        );

        mockMvc.perform(post("/api/v1/progressions/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.key").value("F MAJOR"))
            .andExpect(jsonPath("$.sections").isArray());
    }

    @Test
    void testGenerateProgressionWithInvalidScaleType() throws Exception {
        String invalidRequest = """
            {
                "tonicName": "C",
                "scaleType": "INVALID_SCALE",
                "songForm": "AABA",
                "style": "JAZZ_STANDARD",
                "complexity": "SEVENTH_CHORDS",
                "modulationFrequency": "MEDIUM"
            }
            """;

        mockMvc.perform(post("/api/v1/progressions/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void testGenerateProgressionWithMissingRequiredFields() throws Exception {
        String invalidRequest = """
            {
                "tonicName": "",
                "scaleType": "MAJOR",
                "songForm": "AABA"
            }
            """;

        mockMvc.perform(post("/api/v1/progressions/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void testListScales() throws Exception {
        mockMvc.perform(get("/api/v1/progressions/scales"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasItem("MAJOR")))
            .andExpect(jsonPath("$", hasItem("NATURAL_MINOR")))
            .andExpect(jsonPath("$", hasItem("DORIAN")));
    }

    @Test
    void testListStyles() throws Exception {
        mockMvc.perform(get("/api/v1/progressions/styles"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasItem("SIMPLE")))
            .andExpect(jsonPath("$", hasItem("JAZZ_STANDARD")))
            .andExpect(jsonPath("$", hasItem("JAZZ_MODERN")));
    }

    @Test
    void testListComplexityLevels() throws Exception {
        mockMvc.perform(get("/api/v1/progressions/complexity-levels"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasItem("TRIADS")))
            .andExpect(jsonPath("$", hasItem("SEVENTH_CHORDS")))
            .andExpect(jsonPath("$", hasItem("FULL_EXTENSIONS")));
    }

    @Test
    void testListModulationFrequencies() throws Exception {
        mockMvc.perform(get("/api/v1/progressions/modulation-frequencies"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasItem("NONE")))
            .andExpect(jsonPath("$", hasItem("LOW")))
            .andExpect(jsonPath("$", hasItem("HIGH")));
    }

    @Test
    void testGenerateProgressionWithDifferentStyles() throws Exception {
        String[] styles = {"SIMPLE", "POP", "JAZZ_STANDARD", "JAZZ_MODERN"};
        
        for (String style : styles) {
            ProgressionGenerationRequest request = new ProgressionGenerationRequest(
                "G", "MAJOR", "AABA", style, "SEVENTH_CHORDS", "LOW"
            );

            mockMvc.perform(post("/api/v1/progressions/generate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.style").value(style));
        }
    }
}
