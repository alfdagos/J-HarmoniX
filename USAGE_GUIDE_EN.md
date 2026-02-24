# 🎵 J-Harmonix – User Guide

Complete guide to using J-Harmonix, the jazz harmonic progression generator.

---

## 📋 Table of Contents

- [Starting the Application](#starting-the-application)
- [Available Commands](#available-commands)
- [Generate Command Parameters](#generate-command-parameters)
- [Basic Examples](#basic-examples)
- [Scale Examples](#scale-examples)
- [Harmony Style Examples](#harmony-style-examples)
- [Complexity Examples](#complexity-examples)
- [Modulation Examples](#modulation-examples)
- [Advanced Examples](#advanced-examples)

---

## 🚀 Starting the Application

### Method 1: Direct JAR Execution (recommended)

```bash
java -jar j-harmonix-cli/target/j-harmonix-cli-0.1.0-SNAPSHOT.jar
```

### Method 2: Via Maven

```bash
mvn -pl j-harmonix-cli spring-boot:run
```

### Note

If you see the warning `"Unable to create a system terminal, creating a dumb terminal"`, this is normal and does not prevent the application from functioning.

---

## 📦 Available Commands

Once the shell is started (`shell:>`), the following commands are available:

| Command      | Description                                    |
|--------------|------------------------------------------------|
| `help`       | Display the complete list of commands          |
| `scales`     | List all available scales                      |
| `styles`     | List all available harmony styles              |
| `generate`   | Generate a harmonic progression                |
| `exit`       | Exit the application                           |
| `quit`       | Exit the application                           |
| `clear`      | Clear the screen                               |
| `history`    | Show command history                           |

---

## ⚙️ Generate Command Parameters

```bash
generate --tonic <note> --scale <scale_type> --form <form> --style <style> --complexity <level> --modulation <frequency>
```

### --tonic (Key)
The starting note of the progression.

**Values:** `C`, `C#`, `Db`, `D`, `D#`, `Eb`, `E`, `F`, `F#`, `Gb`, `G`, `G#`, `Ab`, `A`, `A#`, `Bb`, `B`

**Default:** `C`

---

### --scale (Scale Type)

| Scale                  | Value                 | Description                          |
|------------------------|-----------------------|--------------------------------------|
| Major                  | `MAJOR`               | Traditional major scale              |
| Natural Minor          | `NATURAL_MINOR`       | Natural minor scale (aeolian)        |
| Harmonic Minor         | `HARMONIC_MINOR`      | Harmonic minor scale                 |
| Melodic Minor          | `MELODIC_MINOR`       | Melodic minor scale                  |
| Dorian                 | `DORIAN`              | Dorian mode                          |
| Phrygian               | `PHRYGIAN`            | Phrygian mode                        |
| Lydian                 | `LYDIAN`              | Lydian mode                          |
| Mixolydian             | `MIXOLYDIAN`          | Mixolydian mode                      |
| Locrian                | `LOCRIAN`             | Locrian mode                         |
| Whole Tone             | `WHOLE_TONE`          | Whole tone scale                     |
| Diminished (W-H)       | `DIMINISHED_WH`       | Diminished scale whole-half          |
| Diminished (H-W)       | `DIMINISHED_HW`       | Diminished scale half-whole          |
| Blues                  | `BLUES`               | Blues scale                          |
| Major Pentatonic       | `PENTATONIC_MAJOR`    | Major pentatonic scale               |
| Minor Pentatonic       | `PENTATONIC_MINOR`    | Minor pentatonic scale               |

**Default:** `MAJOR`

---

### --form (Song Form)

Defines the song structure.

**Values:** Any string (e.g., `AABA`, `verse-chorus-bridge`, `intro-verse-chorus-outro`)

**Default:** `AABA`

---

### --style (Harmony Style)

| Style           | Value             | Characteristics                                          |
|-----------------|-------------------|----------------------------------------------------------|
| Simple          | `SIMPLE`          | Triads, only diatonic chords                             |
| Pop             | `POP`             | Some seventh chords, no jazz substitutions               |
| Jazz Standard   | `JAZZ_STANDARD`   | ii-V-I, turnarounds, basic extensions                    |
| Jazz Modern     | `JAZZ_MODERN`     | Altered dominants, tritone substitutions, extensions     |

**Default:** `JAZZ_STANDARD`

---

### --complexity (Complexity Level)

| Level               | Value              | Chord Types                          |
|---------------------|--------------------|--------------------------------------|
| Triads              | `TRIADS`           | 3-note chords                        |
| Seventh Chords      | `SEVENTH_CHORDS`   | Seventh chords                       |
| Ninths              | `NINTHS`           | Ninth chords                         |
| Full Extensions     | `FULL_EXTENSIONS`  | Chords with 9ths, 11ths, 13ths       |

**Default:** `SEVENTH_CHORDS`

---

### --modulation (Modulation Frequency)

| Frequency | Value    | Behavior                                      |
|-----------|----------|-----------------------------------------------|
| None      | `NONE`   | No modulation                                 |
| Low       | `LOW`    | Rare modulations with pivot chords            |
| Medium    | `MEDIUM` | Modulations with secondary ii-V progressions  |
| High      | `HIGH`   | Frequent modulations, advanced substitutions  |

**Default:** `MEDIUM`

---

## 🎹 Basic Examples

### Simple Jazz Standard Progression

```bash
generate --tonic C --scale MAJOR --form AABA --style JAZZ_STANDARD
```

### Progression in Sharp Key

```bash
generate --tonic F# --scale MAJOR --form verse-chorus-bridge --style JAZZ_MODERN
```

### Progression in Flat Key

```bash
generate --tonic Bb --scale MAJOR --form AABA --style JAZZ_STANDARD
```

---

## 🎼 Scale Examples

### Major and Minor Scales

```bash
# Major scale
generate --tonic C --scale MAJOR --form AABA --style JAZZ_STANDARD

# Natural minor
generate --tonic A --scale NATURAL_MINOR --form AABA --style JAZZ_STANDARD

# Harmonic minor (typical for jazz ballads)
generate --tonic D --scale HARMONIC_MINOR --form AABA --style JAZZ_MODERN

# Melodic minor (for jazz improvisation)
generate --tonic G --scale MELODIC_MINOR --form verse-chorus --style JAZZ_MODERN
```

---

### Modes

```bash
# Dorian (great for modal jazz)
generate --tonic D --scale DORIAN --form verse-chorus --style JAZZ_MODERN

# Phrygian (Spanish/flamenco sound)
generate --tonic E --scale PHRYGIAN --form AABA --style JAZZ_MODERN

# Lydian (dreamy sound, #11)
generate --tonic F --scale LYDIAN --form verse-chorus-bridge --style JAZZ_MODERN

# Mixolydian (blues/rock sound)
generate --tonic G --scale MIXOLYDIAN --form verse-chorus --style POP

# Locrian (unstable, for passing sections)
generate --tonic B --scale LOCRIAN --form AABA --style JAZZ_MODERN
```

---

### Symmetrical Scales

```bash
# Whole tone scale (Debussy style)
generate --tonic C --scale WHOLE_TONE --form verse-chorus --style JAZZ_MODERN

# Diminished whole-half (for dominant chords)
generate --tonic C --scale DIMINISHED_WH --form AABA --style JAZZ_MODERN

# Diminished half-whole (for diminished chords)
generate --tonic C --scale DIMINISHED_HW --form verse-chorus --style JAZZ_MODERN
```

---

### Pentatonic and Blues Scales

```bash
# Major pentatonic (for pop/country)
generate --tonic G --scale PENTATONIC_MAJOR --form verse-chorus-bridge --style POP

# Minor pentatonic (for rock/blues)
generate --tonic A --scale PENTATONIC_MINOR --form verse-chorus --style POP

# Blues (for classic blues progressions)
generate --tonic E --scale BLUES --form verse-chorus --style POP
```

---

## 🎨 Harmony Style Examples

### SIMPLE – For Simple Pop

```bash
# Only diatonic triads
generate --tonic C --scale MAJOR --form verse-chorus-bridge --style SIMPLE

generate --tonic G --scale MAJOR --form verse-chorus --style SIMPLE

generate --tonic Am --scale NATURAL_MINOR --form AABA --style SIMPLE
```

---

### POP – For Pop/Rock Compositions

```bash
# Seventh chords without jazz substitutions
generate --tonic D --scale MAJOR --form verse-chorus-bridge --style POP

generate --tonic A --scale MAJOR --form intro-verse-chorus-bridge-outro --style POP

generate --tonic E --scale MIXOLYDIAN --form verse-chorus --style POP
```

---

### JAZZ_STANDARD – Classic Jazz

```bash
# ii-V-I progressions and turnarounds
generate --tonic F --scale MAJOR --form AABA --style JAZZ_STANDARD

generate --tonic Bb --scale MAJOR --form AABA --style JAZZ_STANDARD

generate --tonic Eb --scale MAJOR --form verse-bridge-verse --style JAZZ_STANDARD
```

---

### JAZZ_MODERN – Contemporary Jazz

```bash
# Tritone substitutions, altered dominants
generate --tonic C --scale MAJOR --form AABA --style JAZZ_MODERN

generate --tonic G --scale MELODIC_MINOR --form verse-chorus --style JAZZ_MODERN

generate --tonic Db --scale MAJOR --form AABA --style JAZZ_MODERN
```

---

## 🎯 Complexity Examples

### TRIADS – Basic Triads

```bash
generate --tonic C --scale MAJOR --form AABA --style SIMPLE --complexity TRIADS

generate --tonic G --scale MAJOR --form verse-chorus --style POP --complexity TRIADS
```

---

### SEVENTH_CHORDS – Seventh Chords

```bash
generate --tonic F --scale MAJOR --form AABA --style JAZZ_STANDARD --complexity SEVENTH_CHORDS

generate --tonic Bb --scale DORIAN --form verse-chorus --style JAZZ_STANDARD --complexity SEVENTH_CHORDS
```

---

### NINTHS – Ninth Chords

```bash
generate --tonic D --scale MAJOR --form AABA --style JAZZ_MODERN --complexity NINTHS

generate --tonic A --scale MELODIC_MINOR --form verse-chorus --style JAZZ_MODERN --complexity NINTHS
```

---

### FULL_EXTENSIONS – Full Extensions

```bash
# Ninth, eleventh, thirteenth
generate --tonic C --scale MAJOR --form AABA --style JAZZ_MODERN --complexity FULL_EXTENSIONS

generate --tonic Eb --scale MAJOR --form verse-chorus-bridge --style JAZZ_MODERN --complexity FULL_EXTENSIONS

generate --tonic F# --scale LYDIAN --form AABA --style JAZZ_MODERN --complexity FULL_EXTENSIONS
```

---

## 🌀 Modulation Examples

### NONE – No Modulation

```bash
# Stays in the original key
generate --tonic C --scale MAJOR --form AABA --style JAZZ_STANDARD --modulation NONE

generate --tonic G --scale MAJOR --form verse-chorus --style POP --modulation NONE
```

---

### LOW – Rare Modulations

```bash
# Occasional modulations with pivot chords
generate --tonic F --scale MAJOR --form AABA --style JAZZ_STANDARD --modulation LOW

generate --tonic Bb --scale MAJOR --form verse-chorus-bridge --style JAZZ_STANDARD --modulation LOW
```

---

### MEDIUM – Moderate Modulations

```bash
# Secondary ii-V progressions
generate --tonic C --scale MAJOR --form AABA --style JAZZ_STANDARD --modulation MEDIUM

generate --tonic D --scale MAJOR --form verse-bridge-verse --style JAZZ_MODERN --modulation MEDIUM
```

---

### HIGH – Frequent Modulations

```bash
# Tritone substitutions, chromatic progressions
generate --tonic G --scale MAJOR --form AABA --style JAZZ_MODERN --modulation HIGH

generate --tonic Eb --scale MAJOR --form verse-chorus-bridge --style JAZZ_MODERN --modulation HIGH

generate --tonic C --scale MELODIC_MINOR --form AABA --style JAZZ_MODERN --modulation HIGH
```

---

## 🚀 Advanced Examples

### Complex Jazz Ballad

```bash
generate --tonic Eb --scale MAJOR --form intro-AABA-outro --style JAZZ_MODERN --complexity FULL_EXTENSIONS --modulation HIGH
```

---

### Modal Jazz (Miles Davis Style)

```bash
generate --tonic D --scale DORIAN --form verse-chorus --style JAZZ_MODERN --complexity NINTHS --modulation LOW
```

---

### Jazz Fusion (Herbie Hancock Style)

```bash
generate --tonic C --scale MELODIC_MINOR --form verse-solo-chorus --style JAZZ_MODERN --complexity FULL_EXTENSIONS --modulation HIGH
```

---

### Jazz Blues Progression

```bash
generate --tonic Bb --scale BLUES --form 12-bar-blues --style JAZZ_STANDARD --complexity SEVENTH_CHORDS --modulation LOW
```

---

### Avant-Garde Sound

```bash
generate --tonic C --scale WHOLE_TONE --form free-form --style JAZZ_MODERN --complexity FULL_EXTENSIONS --modulation HIGH

generate --tonic F# --scale DIMINISHED_WH --form AABA --style JAZZ_MODERN --complexity FULL_EXTENSIONS --modulation HIGH
```

---

### Modern Pop Progression

```bash
generate --tonic G --scale MAJOR --form intro-verse-prechorus-chorus-bridge-chorus-outro --style POP --complexity SEVENTH_CHORDS --modulation LOW
```

---

### Bossa Nova Style

```bash
generate --tonic F --scale MAJOR --form verse-chorus-verse-chorus --style JAZZ_STANDARD --complexity SEVENTH_CHORDS --modulation MEDIUM
```

---

### Hard Bop (Art Blakey Style)

```bash
generate --tonic F --scale MAJOR --form AABA --style JAZZ_STANDARD --complexity SEVENTH_CHORDS --modulation MEDIUM
```

---

### Post-Bop (Wayne Shorter Style)

```bash
generate --tonic C --scale MELODIC_MINOR --form ABAC --style JAZZ_MODERN --complexity FULL_EXTENSIONS --modulation HIGH
```

---

## 🎓 Suggested Combinations by Genre

### Traditional Jazz (1920s-1940s)
```bash
generate --tonic Bb --scale MAJOR --form AABA --style JAZZ_STANDARD --complexity SEVENTH_CHORDS --modulation LOW
```

---

### Bebop (1940s-1950s)
```bash
generate --tonic F --scale MAJOR --form AABA --style JAZZ_STANDARD --complexity SEVENTH_CHORDS --modulation MEDIUM
```

---

### Modal Jazz (1950s-1960s)
```bash
generate --tonic D --scale DORIAN --form ABCD --style JAZZ_MODERN --complexity NINTHS --modulation NONE
```

---

### Contemporary Jazz (2000s+)
```bash
generate --tonic Eb --scale MELODIC_MINOR --form verse-pre-chorus-chorus-bridge --style JAZZ_MODERN --complexity FULL_EXTENSIONS --modulation HIGH
```

---

## 💡 Tips & Tricks

### Explore All Scales
```bash
# First display available scales
scales

# Then try each one with the same settings
generate --tonic C --scale MAJOR --form AABA --style JAZZ_MODERN
generate --tonic C --scale DORIAN --form AABA --style JAZZ_MODERN
generate --tonic C --scale LYDIAN --form AABA --style JAZZ_MODERN
# ... and so on
```

---

### Explore All Styles
```bash
# Display available styles
styles

# Compare the same setup with different styles
generate --tonic C --scale MAJOR --form verse-chorus --style SIMPLE
generate --tonic C --scale MAJOR --form verse-chorus --style POP
generate --tonic C --scale MAJOR --form verse-chorus --style JAZZ_STANDARD
generate --tonic C --scale MAJOR --form verse-chorus --style JAZZ_MODERN
```

---

### Experiment with Complexity
```bash
# Same progression with increasing complexity
generate --tonic G --scale MAJOR --form AABA --style JAZZ_STANDARD --complexity TRIADS
generate --tonic G --scale MAJOR --form AABA --style JAZZ_STANDARD --complexity SEVENTH_CHORDS
generate --tonic G --scale MAJOR --form AABA --style JAZZ_STANDARD --complexity NINTHS
generate --tonic G --scale MAJOR --form AABA --style JAZZ_STANDARD --complexity FULL_EXTENSIONS
```

---

### Test Modulations
```bash
# Same setup with different modulations
generate --tonic C --scale MAJOR --form AABA --style JAZZ_MODERN --modulation NONE
generate --tonic C --scale MAJOR --form AABA --style JAZZ_MODERN --modulation LOW
generate --tonic C --scale MAJOR --form AABA --style JAZZ_MODERN --modulation MEDIUM
generate --tonic C --scale MAJOR --form AABA --style JAZZ_MODERN --modulation HIGH
```

---

## 🛠️ Utilities

### History
```bash
# Display all previous commands
history

# Repeat the last command by pressing up arrow ↑
```

---

### Clear Screen
```bash
clear
```

---

### Inline Help
```bash
# General help
help

# Help for a specific command
help generate
```

---

## 📝 Important Notes

- Parameters can be specified in any order
- Values with spaces must be quoted: `--form "verse chorus bridge"`
- Use `Ctrl+C` to interrupt a generation in progress
- Use `exit` or `quit` to close the application
- Note names support both sharps (#) and flats (b)

---

## 🔧 Troubleshooting

### Application Won't Start
```bash
# Rebuild the project
mvn clean install -DskipTests

# Try running the JAR again
java -jar j-harmonix-cli/target/j-harmonix-cli-0.1.0-SNAPSHOT.jar
```

---

### Error "Unknown scale type"
```bash
# Check available scales
scales

# Use the exact value from the list (case-insensitive)
```

---

### Error "Unknown harmony style"
```bash
# Check available styles
styles

# Use: SIMPLE, POP, JAZZ_STANDARD, or JAZZ_MODERN
```

---

## 🎉 Have Fun!

Experiment freely with all combinations to discover unique and interesting progressions!

For feedback or bug reports: [GitHub Issues](https://github.com/alfdagos/J-Harmonix/issues)

---

**J-Harmonix** – *Bringing jazz harmony to your fingertips* 🎵
