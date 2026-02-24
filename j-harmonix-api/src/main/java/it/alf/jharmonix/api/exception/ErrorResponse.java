package it.alf.jharmonix.api.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Standard error response DTO.
 */
@Schema(description = "Error response")
public record ErrorResponse(
    
    @Schema(description = "HTTP status code", example = "400")
    int status,
    
    @Schema(description = "Error message", example = "Invalid parameter")
    String message,
    
    @Schema(description = "Request path", example = "/api/v1/progressions/generate")
    String path,
    
    @Schema(description = "Error timestamp")
    LocalDateTime timestamp
) {
    
    public static ErrorResponse of(int status, String message, String path) {
        return new ErrorResponse(status, message, path, LocalDateTime.now());
    }
}
