package it.alf.jharmonix.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        description = "Creates a complete harmonic progression based on music theory rules and jazz techniques",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Progression generated successfully",
                content = @Content(schema = @Schema(implementation = ProgressionGenerationResponse.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid request parameters",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    public ResponseEntity<ProgressionGenerationResponse> generateProgression(
            @Valid @RequestBody ProgressionGenerationRequest request) {
        
        // Parse enums from strings
        ScaleType scaleType = ScaleType.valueOf(request.scaleType().toUpperCase());
        ProgressionRequest.HarmonyStyle style = 
            ProgressionRequest.HarmonyStyle.valueOf(request.style().toUpperCase());
        ProgressionRequest.ComplexityLevel complexity = 
            ProgressionRequest.ComplexityLevel.valueOf(request.complexity().toUpperCase());
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

    @GetMapping("/scales")
    @Operation(
        summary = "List available scale types",
        description = "Returns all supported scale types"
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
        description = "Returns all supported harmony styles"
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
        description = "Returns all supported chord complexity levels"
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
        description = "Returns all supported modulation frequencies"
    )
    public ResponseEntity<List<String>> listModulationFrequencies() {
        List<String> frequencies = java.util.Arrays.stream(ProgressionRequest.ModulationFrequency.values())
            .map(Enum::name)
            .toList();
        return ResponseEntity.ok(frequencies);
    }
}
