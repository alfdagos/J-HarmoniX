package it.alf.jharmonix.api.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.alf.jharmonix.api.dto.ProgressionGenerationRequest;
import it.alf.jharmonix.api.dto.ProgressionGenerationResponse;
import it.alf.jharmonix.api.exception.ErrorResponse;
import it.alf.jharmonix.core.model.Progression;
import it.alf.jharmonix.core.model.ScaleType;
import it.alf.jharmonix.core.port.HarmonyGeneratorPort;
import it.alf.jharmonix.core.port.ProgressionRequest;
import jakarta.validation.Valid;

/**
 * REST controller for harmony progression generation.
 */
@RestController
@RequestMapping("/api/v1/progressions")
@Tag(name = "Harmony Progressions", description = "Generate and manage harmonic progressions")
public class HarmonyProgressionController {

    private final HarmonyGeneratorPort harmonyGenerator;

    public HarmonyProgressionController(HarmonyGeneratorPort harmonyGenerator) {
        this.harmonyGenerator = harmonyGenerator;
    }

    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Generate a harmonic progression",
        description = """
            Creates a complete harmonic progression based on advanced jazz music theory and composition techniques.
            
            ## Features
            - **16 Scale Types**: MAJOR, NATURAL_MINOR, HARMONIC_MINOR, MELODIC_MINOR, DORIAN, PHRYGIAN, LYDIAN,
              MIXOLYDIAN, AEOLIAN, LOCRIAN, LYDIAN_DOMINANT, ALTERED, HALF_WHOLE_DIMINISHED, WHOLE_TONE, 
              CHROMATIC, BLUES
            - **4 Harmony Styles**: TRADITIONAL_JAZZ (II-V-I progressions), BEBOP (chromatic approaches),
              MODAL (pedal points, static harmony), CONTEMPORARY_JAZZ (quartal voicings, extended chords)
            - **3 Complexity Levels**: BASIC (triads), INTERMEDIATE (7th chords), ADVANCED (9th, 11th, 13th, alterations)
            - **4 Modulation Frequencies**: NONE, LOW (rare modulations), MEDIUM (occasional), HIGH (frequent)
            - **Multiple Song Forms**: AABA (32-bar), ABAC, AAB (12-bar blues), THROUGH_COMPOSED
            
            ## Advanced Techniques Applied
            - Modal interchange (borrowing chords from parallel scales)
            - Secondary dominants and diminished passing chords
            - Tritone substitutions
            - Chord extensions (9th, 11th, 13th)
            - Alterations (b9, #9, #11, b13)
            - Voice leading optimization
            
            ## Examples
            See the request body examples below for common use cases.
            """,
        requestBody = @RequestBody(
            description = "Progression generation parameters with musical context",
            required = true,
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ProgressionGenerationRequest.class),
                examples = {
                    @ExampleObject(
                        name = "Traditional Jazz Standard",
                        summary = "Classic AABA format with II-V-I progressions",
                        description = "Creates a 32-bar standard with traditional jazz harmony in C Major",
                        value = """
                            {
                              "tonicName": "C",
                              "scaleType": "MAJOR",
                              "songForm": "AABA",
                              "style": "TRADITIONAL_JAZZ",
                              "complexity": "INTERMEDIATE",
                              "modulationFrequency": "LOW"
                            }
                            """
                    ),
                    @ExampleObject(
                        name = "Bebop in Bb",
                        summary = "Fast bebop with chromatic approaches",
                        description = "Generates bebop-style harmony with chromatic passing chords and alterations",
                        value = """
                            {
                              "tonicName": "Bb",
                              "scaleType": "MAJOR",
                              "songForm": "AABA",
                              "style": "BEBOP",
                              "complexity": "ADVANCED",
                              "modulationFrequency": "MEDIUM"
                            }
                            """
                    ),
                    @ExampleObject(
                        name = "Modal Jazz - Dorian",
                        summary = "Modal harmony with static sections",
                        description = "Creates modal jazz with pedal points and limited harmonic movement",
                        value = """
                            {
                              "tonicName": "D",
                              "scaleType": "DORIAN",
                              "songForm": "ABAC",
                              "style": "MODAL",
                              "complexity": "INTERMEDIATE",
                              "modulationFrequency": "NONE"
                            }
                            """
                    ),
                    @ExampleObject(
                        name = "12-Bar Blues in F",
                        summary = "Traditional blues progression",
                        description = "Generates a 12-bar blues with jazz extensions",
                        value = """
                            {
                              "tonicName": "F",
                              "scaleType": "BLUES",
                              "songForm": "AAB",
                              "style": "TRADITIONAL_JAZZ",
                              "complexity": "INTERMEDIATE",
                              "modulationFrequency": "NONE"
                            }
                            """
                    ),
                    @ExampleObject(
                        name = "Contemporary Jazz - Lydian",
                        summary = "Modern jazz with quartal harmony",
                        description = "Uses contemporary techniques with Lydian mode and frequent modulations",
                        value = """
                            {
                              "tonicName": "F",
                              "scaleType": "LYDIAN",
                              "songForm": "THROUGH_COMPOSED",
                              "style": "CONTEMPORARY_JAZZ",
                              "complexity": "ADVANCED",
                              "modulationFrequency": "HIGH"
                            }
                            """
                    ),
                    @ExampleObject(
                        name = "Altered Dominant",
                        summary = "Altered scale for outside playing",
                        description = "Generates progression using the altered scale (7th mode of melodic minor)",
                        value = """
                            {
                              "tonicName": "G",
                              "scaleType": "ALTERED",
                              "songForm": "AABA",
                              "style": "CONTEMPORARY_JAZZ",
                              "complexity": "ADVANCED",
                              "modulationFrequency": "LOW"
                            }
                            """
                    )
                }
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Progression generated successfully",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProgressionGenerationResponse.class),
                    examples = @ExampleObject(
                        name = "Generated Progression Example",
                        description = "A typical response with sections containing chords",
                        value = """
                            {
                              "key": "C MAJOR",
                              "songForm": "AABA",
                              "style": "TRADITIONAL_JAZZ",
                              "sections": [
                                {
                                  "label": "A Section 1",
                                  "chords": [
                                    {"symbol": "Cmaj7", "root": "C", "quality": "MAJOR_SEVENTH"},
                                    {"symbol": "Dm7", "root": "D", "quality": "MINOR_SEVENTH"},
                                    {"symbol": "G7", "root": "G", "quality": "DOMINANT_SEVENTH"},
                                    {"symbol": "Cmaj7", "root": "C", "quality": "MAJOR_SEVENTH"}
                                  ],
                                  "bars": 4
                                },
                                {
                                  "label": "A Section 2",
                                  "chords": [
                                    {"symbol": "Cmaj7", "root": "C", "quality": "MAJOR_SEVENTH"},
                                    {"symbol": "Am7", "root": "A", "quality": "MINOR_SEVENTH"},
                                    {"symbol": "Dm7", "root": "D", "quality": "MINOR_SEVENTH"},
                                    {"symbol": "G7", "root": "G", "quality": "DOMINANT_SEVENTH"}
                                  ],
                                  "bars": 4
                                }
                              ],
                              "timestamp": "2024-01-15T10:30:00Z"
                            }
                            """
                    )
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid request parameters",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                        name = "Validation Error",
                        description = "Error when invalid parameters are provided",
                        value = """
                            {
                              "timestamp": "2024-01-15T10:30:00Z",
                              "status": 400,
                              "error": "Bad Request",
                              "message": "Invalid scale type: INVALID_SCALE",
                              "path": "/api/v1/progressions/generate"
                            }
                            """
                    )
                )
            )
        }
    )
    public ResponseEntity<ProgressionGenerationResponse> generateProgression(
            @Valid @org.springframework.web.bind.annotation.RequestBody ProgressionGenerationRequest request) {
        
        // Parse enums from strings
        ScaleType scaleType = ScaleType.valueOf(request.scaleType().toUpperCase());
        ProgressionRequest.HarmonyStyle style = mapHarmonyStyle(request.style());
        ProgressionRequest.ComplexityLevel complexity = mapComplexityLevel(request.complexity());
        ProgressionRequest.ModulationFrequency modulation = 
            ProgressionRequest.ModulationFrequency.valueOf(request.modulationFrequency().toUpperCase());
        
        // Build request
        ProgressionRequest progressionRequest = ProgressionRequest.builder()
            .tonicName(request.tonicName())
            .scaleType(scaleType)
            .songForm(request.songForm())
            .style(style)
            .complexity(complexity)
            .modulationFrequency(modulation)
            .build();
        
        // Generate
        List<Progression> progressions = harmonyGenerator.generate(progressionRequest);
        
        // Build response
        String keyStr = request.tonicName() + " " + scaleType.name();
        ProgressionGenerationResponse response = ProgressionGenerationResponse.fromProgressions(
            progressions,
            keyStr,
            request.songForm(),
            style.name()
        );
        
        return ResponseEntity.ok(response);
    }

    private ProgressionRequest.HarmonyStyle mapHarmonyStyle(String rawStyle) {
        String normalizedStyle = rawStyle.toUpperCase();
        return switch (normalizedStyle) {
            case "TRADITIONAL_JAZZ" -> ProgressionRequest.HarmonyStyle.JAZZ_STANDARD;
            case "BEBOP", "CONTEMPORARY_JAZZ" -> ProgressionRequest.HarmonyStyle.JAZZ_MODERN;
            case "MODAL" -> ProgressionRequest.HarmonyStyle.SIMPLE;
            case "SIMPLE", "POP", "JAZZ_STANDARD", "JAZZ_MODERN" ->
                ProgressionRequest.HarmonyStyle.valueOf(normalizedStyle);
            default -> throw new IllegalArgumentException("Invalid harmony style: " + rawStyle);
        };
    }

    private ProgressionRequest.ComplexityLevel mapComplexityLevel(String rawComplexity) {
        String normalizedComplexity = rawComplexity.toUpperCase();
        return switch (normalizedComplexity) {
            case "BASIC" -> ProgressionRequest.ComplexityLevel.TRIADS;
            case "INTERMEDIATE" -> ProgressionRequest.ComplexityLevel.SEVENTH_CHORDS;
            case "ADVANCED" -> ProgressionRequest.ComplexityLevel.FULL_EXTENSIONS;
            case "TRIADS", "SEVENTH_CHORDS", "NINTHS", "FULL_EXTENSIONS" ->
                ProgressionRequest.ComplexityLevel.valueOf(normalizedComplexity);
            default -> throw new IllegalArgumentException("Invalid complexity level: " + rawComplexity);
        };
    }

    @GetMapping("/scales")
    @Operation(
        summary = "List available scale types",
        description = """
            Returns all supported scale types that can be used for progression generation.
            
            ## Available Scales
            - **MAJOR**: Ionian mode, standard major scale
            - **NATURAL_MINOR**: Aeolian mode, natural minor scale
            - **HARMONIC_MINOR**: Minor with raised 7th degree
            - **MELODIC_MINOR**: Minor with raised 6th and 7th degrees
            - **DORIAN**: Minor mode with raised 6th
            - **PHRYGIAN**: Minor mode with lowered 2nd
            - **LYDIAN**: Major mode with raised 4th
            - **MIXOLYDIAN**: Major mode with lowered 7th
            - **AEOLIAN**: Natural minor (same as NATURAL_MINOR)
            - **LOCRIAN**: Diminished mode with lowered 2nd and 5th
            - **LYDIAN_DOMINANT**: Lydian with lowered 7th
            - **ALTERED**: Super Locrian, 7th mode of melodic minor
            - **HALF_WHOLE_DIMINISHED**: Symmetrical diminished scale
            - **WHOLE_TONE**: All whole steps, augmented sound
            - **CHROMATIC**: All 12 chromatic notes
            - **BLUES**: Minor pentatonic with added b5
            """,
        responses = @ApiResponse(
            responseCode = "200",
            description = "List of scale type names",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = @ExampleObject(
                    value = """
                        [
                          "MAJOR",
                          "NATURAL_MINOR",
                          "HARMONIC_MINOR",
                          "MELODIC_MINOR",
                          "DORIAN",
                          "PHRYGIAN",
                          "LYDIAN",
                          "MIXOLYDIAN",
                          "AEOLIAN",
                          "LOCRIAN",
                          "LYDIAN_DOMINANT",
                          "ALTERED",
                          "HALF_WHOLE_DIMINISHED",
                          "WHOLE_TONE",
                          "CHROMATIC",
                          "BLUES"
                        ]
                        """
                )
            )
        )
    )
    public ResponseEntity<List<String>> listScales() {
        List<String> scales = java.util.Arrays.stream(ScaleType.values())
            .map(Enum::name)
            .toList();
        return ResponseEntity.ok(scales);
    }

    @GetMapping("/styles")
    @Operation(
        summary = "List available harmony styles",
        description = """
            Returns all supported harmony styles with their characteristics.
            
            ## Harmony Styles
            - **TRADITIONAL_JAZZ**: Classic jazz harmony with II-V-I progressions, functional harmony,
              diatonic chord movement. Best for standards and swing era compositions.
            - **BEBOP**: Fast-moving harmony with chromatic passing chords, secondary dominants,
              tritone substitutions. Frequent chord changes and voice leading.
            - **MODAL**: Static harmony focused on single modes, pedal points, limited chord movement.
              Emphasis on melodic development over harmonic motion.  
            - **CONTEMPORARY_JAZZ**: Modern jazz with quartal voicings, complex extensions (9th, 11th, 13th),
              altered dominants, sus chords, and unconventional progressions.
            """,
        responses = @ApiResponse(
            responseCode = "200",
            description = "List of harmony style names",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = @ExampleObject(
                    value = """
                        [
                          "TRADITIONAL_JAZZ",
                          "BEBOP",
                          "MODAL",
                          "CONTEMPORARY_JAZZ"
                        ]
                        """
                )
            )
        )
    )
    public ResponseEntity<List<String>> listStyles() {
        List<String> styles = java.util.Arrays.stream(ProgressionRequest.HarmonyStyle.values())
            .map(Enum::name)
            .toList();
        return ResponseEntity.ok(styles);
    }

    @GetMapping("/complexity-levels")
    @Operation(
        summary = "List available complexity levels",
        description = """
            Returns all supported chord complexity levels.
            
            ## Complexity Levels
            - **BASIC**: Simple triads (major, minor, diminished, augmented). Suitable for beginners
              or sparse harmonic textures.
            - **INTERMEDIATE**: Seventh chords (maj7, m7, 7, m7b5, dim7). Standard for most jazz compositions,
              provides rich harmony without overwhelming complexity.
            - **ADVANCED**: Extended chords with 9th, 11th, 13th, alterations (b9, #9, #11, b13),
              sus chords, and complex voicings. For advanced players and complex harmonic structures.
            """,
        responses = @ApiResponse(
            responseCode = "200",
            description = "List of complexity level names",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = @ExampleObject(
                    value = """
                        [
                          "BASIC",
                          "INTERMEDIATE",
                          "ADVANCED"
                        ]
                        """
                )
            )
        )
    )
    public ResponseEntity<List<String>> listComplexityLevels() {
        List<String> levels = java.util.Arrays.stream(ProgressionRequest.ComplexityLevel.values())
            .map(Enum::name)
            .toList();
        return ResponseEntity.ok(levels);
    }

    @GetMapping("/modulation-frequencies")
    @Operation(
        summary = "List available modulation frequencies",
        description = """
            Returns all supported modulation frequency settings.
            
            ## Modulation Frequencies
            - **NONE**: No modulations, stays in the original key throughout the entire piece.
              Suitable for modal jazz or simple compositions.
            - **LOW**: Rare modulations, typically only in bridge sections or at major structural points.
              Maintains key center stability.
            - **MEDIUM**: Occasional modulations to related keys (relative minor/major, dominant, subdominant).
              Balanced approach for most jazz standards.
            - **HIGH**: Frequent modulations exploring distant keys, chromatic mediants, and circle of fifths movement.
              Creates harmonic variety and interest, best for through-composed forms.
            """,
        responses = @ApiResponse(
            responseCode = "200",
            description = "List of modulation frequency names",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = @ExampleObject(
                    value = """
                        [
                          "NONE",
                          "LOW",
                          "MEDIUM",
                          "HIGH"
                        ]
                        """
                )
            )
        )
    )
    public ResponseEntity<List<String>> listModulationFrequencies() {
        List<String> frequencies = java.util.Arrays.stream(ProgressionRequest.ModulationFrequency.values())
            .map(Enum::name)
            .toList();
        return ResponseEntity.ok(frequencies);
    }
}
