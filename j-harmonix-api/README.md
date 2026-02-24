# J-Harmonix REST API 🌐

REST API adapter for the J-Harmonix harmony generator. Built with **Spring Boot 3** and **Spring Web**, exposing the core engine via HTTP endpoints.

## Quick Start

### Run the API Server

```bash
# From project root
mvn -pl j-harmonix-api spring-boot:run

# Or run the JAR
java -jar j-harmonix-api/target/j-harmonix-api-0.1.0-SNAPSHOT.jar
```

The API will be available at `http://localhost:8080`

### Swagger UI

Open your browser to explore the interactive API documentation:

```
http://localhost:8080/swagger-ui.html
```

## API Endpoints

### 1. Generate Progression

**POST** `/api/v1/progressions/generate`

Generate a complete harmonic progression.

**Request Body:**
```json
{
  "tonicName": "C",
  "scaleType": "MAJOR",
  "songForm": "AABA",
  "style": "JAZZ_STANDARD",
  "complexity": "SEVENTH_CHORDS",
  "modulationFrequency": "MEDIUM"
}
```

**Response:**
```json
{
  "key": "C MAJOR",
  "form": "AABA",
  "style": "JAZZ_STANDARD",
  "sections": [
    {
      "label": "Verse A",
      "chords": [
        {"symbol": "Cmaj7", "root": "C", "quality": "MAJOR_SEVENTH"},
        {"symbol": "Am7", "root": "A", "quality": "MINOR_SEVENTH"},
        {"symbol": "Dm7", "root": "D", "quality": "MINOR_SEVENTH"},
        {"symbol": "G7", "root": "G", "quality": "DOMINANT_SEVENTH"}
      ],
      "bars": 8
    }
  ],
  "totalChords": 32,
  "timestamp": "2026-02-23T14:30:00"
}
```

### 2. List Available Options

**GET** `/api/v1/progressions/scales` - List all scale types  
**GET** `/api/v1/progressions/styles` - List all harmony styles  
**GET** `/api/v1/progressions/complexity-levels` - List complexity levels  
**GET** `/api/v1/progressions/modulation-frequencies` - List modulation options

## Configuration

Edit `application.yml`:

```yaml
server:
  port: 8080

jharmonix:
  engine:
    seed: 0  # 0 = random, or specify a seed for reproducibility

springdoc:
  swagger-ui:
    path: /swagger-ui.html
```

## Example cURL Commands

```bash
# Generate a progression
curl -X POST http://localhost:8080/api/v1/progressions/generate \
  -H "Content-Type: application/json" \
  -d '{
    "tonicName": "F",
    "scaleType": "MAJOR",
    "songForm": "verse-chorus-bridge",
    "style": "JAZZ_MODERN",
    "complexity": "NINTHS",
    "modulationFrequency": "HIGH"
  }'

# List scales
curl http://localhost:8080/api/v1/progressions/scales

# List styles
curl http://localhost:8080/api/v1/progressions/styles
```

## CORS Configuration

CORS is configured to allow requests from:
- `http://localhost:3000` (React/Vue dev)
- `http://localhost:8080` (local testing)

Modify `ApiConfig.java` to add more origins.

## Error Handling

The API returns standardized error responses:

```json
{
  "status": 400,
  "message": "Validation failed: tonicName: must not be blank",
  "path": "/api/v1/progressions/generate",
  "timestamp": "2026-02-23T14:30:00"
}
```

## Testing

```bash
# Run integration tests
mvn test -pl j-harmonix-api

# Run with coverage
mvn test -pl j-harmonix-api jacoco:report
```

## Architecture

This module is a **Hexagonal Architecture Adapter** that:
- Implements the driving (primary) side of the application
- Translates HTTP requests to core domain calls
- Does NOT contain business logic (delegates to `j-harmonix-core`)
- Can be replaced with other adapters (GraphQL, gRPC, etc.) without changing core

---

**Next Steps:**
- Add authentication (Spring Security)
- Add rate limiting
- Add caching for common progressions
- Export to MIDI/PDF endpoints
