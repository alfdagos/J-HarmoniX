# J-Harmonix 🎵

> **Jazz Harmony Generator** – a modular CLI application that generates coherent chord progressions using music-theory rules and jazz-specific techniques.

Built with **Java 17**, **Spring Boot 3**, and **Spring Shell**. Designed with **Hexagonal Architecture** (Ports & Adapters) so the core logic is fully decoupled from the CLI – ready to be extended to REST or mobile adapters.

---

## Project structure

```
j-harmonix/                      ← Parent Maven project
├── j-harmonix-core/             ← Domain model + Harmony Engine (pure Java, no framework)
│   └── src/
│       ├── main/java/it/alf/jharmonix/core/
│       │   ├── model/           ← Note, Interval, Chord, ChordQuality, Scale, ScaleType,
│       │   │                       KeySignature, Progression
│       │   ├── engine/          ← ChordSelector, JazzRuleEngine, ModulationStrategy,
│       │   │                       StructureComposer, HarmonyGeneratorService
│       │   └── port/            ← HarmonyGeneratorPort (primary port), ProgressionRequest
│       └── test/java/           ← JUnit 5 + AssertJ tests (NoteTest, ChordTest, ScaleTest …)
│
├── j-harmonix-cli/              ← Spring Boot + Spring Shell adapter
│   └── src/main/java/it/alf/jharmonix/cli/
│       ├── JHarmonixApplication.java
│       ├── config/HarmonyEngineConfig.java
│       └── command/HarmonyCliCommands.java
│
└── j-harmonix-api/              ← REST API adapter with Spring MVC + OpenAPI/Swagger
    └── src/main/java/it/alf/jharmonix/api/
        ├── JHarmonixApiApplication.java
        ├── controller/HarmonyProgressionController.java
        ├── dto/                 ← Request/Response DTOs with validation
        └── exception/           ← Global exception handling
```

---

## Prerequisites

| Tool       | Minimum version |
|------------|----------------|
| Java (JDK) | 21             |
| Maven      | 3.9            |

---

## Build

```bash
# Build all modules and run unit tests
mvn clean verify

# Build and skip tests (faster for dev iterations)
mvn clean package -DskipTests
```

---

## Run the CLI

```bash
# Option A – via Maven Spring Boot plugin
mvn -pl j-harmonix-cli spring-boot:run

# Option B – run the fat JAR
java -jar j-harmonix-cli/target/j-harmonix-cli-0.1.0-SNAPSHOT.jar
```

Once the shell prompt appears:

```
jharmonix:> generate --tonic C --scale MAJOR --form AABA --style JAZZ_STANDARD
jharmonix:> generate --tonic F --scale MAJOR --form verse-chorus-bridge --style JAZZ_MODERN --complexity FULL_EXTENSIONS --modulation HIGH
jharmonix:> scales       # list available scale types
jharmonix:> styles       # list harmony styles
jharmonix:> help         # full command reference
```

---

## Run the REST API

```bash
# Start the REST API server
mvn -pl j-harmonix-api spring-boot:run
```

The API will be available at `http://localhost:8080/api/v1/progressions`.

**Swagger UI:** `http://localhost:8080/swagger-ui/index.html`

**Example request:**
```bash
curl -X POST http://localhost:8080/api/v1/progressions/generate \
  -H "Content-Type: application/json" \
  -d '{
    "tonicName": "C",
    "scaleType": "MAJOR",
    "songForm": "AABA",
    "style": "JAZZ_STANDARD",
    "complexity": "SEVENTH_CHORDS",
    "modulationFrequency": "MEDIUM"
  }'
```

---

## Example output

```
============================================================
  J-Harmonix  |  Key: C MAJOR  |  Form: AABA
============================================================

[Verse A]
| Cmaj7 | Am7 | Dm7 | G7 | Cmaj7 | Fmaj7 | Dm7 | G7 |

[Verse A]
| Cmaj7 | Am7 | Dm7 | G7 | Cmaj7 | Am7 | Dm7 | G7 |

[Bridge B]
| Gm7 | C7 | Fmaj7 | Bbmaj7 | Em7b5 | A7 | Dm7 | G7 |

[Verse A]
| Cmaj7 | Am7 | Dm7 | G7 | Cmaj7 | Am7 | Dm7 | G7 |

============================================================
```

---

## Architecture overview

```
┌─────────────────────────────────────────────────────────┐
│                 j-harmonix-core (Domain)                │
│                                                         │
│  ┌──────────────┐    ┌────────────────────────────┐     │
│  │  music-model │ ←→ │      harmony-engine        │     │
│  │  Note        │    │  ChordSelector             │     │
│  │  Chord       │    │  JazzRuleEngine            │     │
│  │  Scale       │    │  ModulationStrategy        │     │
│  │  Progression │    │  StructureComposer         │     │
│  └──────────────┘    │  HarmonyGeneratorService   │     │
│                      └────────────────────────────┘     │
│                           ↕ HarmonyGeneratorPort        │
└─────────────────────────────────────────────────────────┘
          ↕ (implements port)       ↕ (implements port)
   ┌──────────────────┐       ┌───────────────────────┐
   │  j-harmonix-cli  │       │   j-harmonix-api      │
   │  Spring Shell    │       │   Spring MVC + REST   │
   └──────────────────┘       └───────────────────────┘
```

---

## Development roadmap

| Phase | Scope | Status |
|-------|-------|--------|
| 1 | Parent POM, dependency management, Java 21 baseline | ✅ Done |
| 2 | Core model: `Note`, `Interval`, `ChordQuality`, `Chord`, `Scale`, `KeySignature`, `Progression` | ✅ Done |
| 3 | Port interfaces: `HarmonyGeneratorPort`, `ProgressionRequest` | ✅ Done |
| 4 | Harmony engine: `ChordSelector`, `JazzRuleEngine`, `ModulationStrategy`, `StructureComposer`, `HarmonyGeneratorService` | ✅ Done |
| 5 | JUnit 5 + AssertJ unit tests (82 tests for core + engine, 9 for API) | ✅ Done |
| 6 | CLI adapter: Spring Boot + Spring Shell, complete command set | ✅ Done |
| 7 | REST API: `j-harmonix-api` with Spring MVC, OpenAPI/Swagger docs, validation | ✅ Done |
| 8 | Jazz extensions: Chord tensions (9th, 11th, 13th), alterations (b9, #9, #11, b13) | ✅ Done |
| 9 | Jazz patterns: Modal interchange, turnarounds | ✅ Done |
| 10 | Advanced jazz: Coltrane changes, reharmonization patterns, pedal points | 🚧 In Progress |
| 11 | Advanced modulations: Pivot chord, common tone, sequential, parallel key | 📋 Planned |
| 12 | Rhythmic structures: Variable chord durations, subdivisions | 📋 Planned |
| 13 | Export formats: Band-in-a-Box, MusicXML, MIDI | 📋 Planned |
| 14 | Chord voicing / voice-leading optimizer | 📋 Future |

> See [ROADMAP.md](ROADMAP.md) for detailed implementation status and technical notes.

---

## Documentation

- 📘 **[USAGE_GUIDE.md](USAGE_GUIDE.md)** (🇮🇹 Italian) – Complete usage guide with examples for all features
- 📗 **[USAGE_GUIDE_EN.md](USAGE_GUIDE_EN.md)** (🇬🇧 English) – Complete usage guide with examples for all features
- 🗺️ **[ROADMAP.md](ROADMAP.md)** – Detailed development roadmap with technical implementation notes

---

## Contributing

1. Fork the repository and create a feature branch (`git checkout -b feat/my-feature`).
2. Write tests alongside your code.
3. Run `mvn clean verify` before opening a pull request.

---

## License

MIT – see [LICENSE](LICENSE).

