# 🎵 J-Harmonix – Guida all'Uso

Guida completa per utilizzare J-Harmonix, il generatore di progressioni armoniche jazz.

---

## 📋 Indice

- [Avvio dell'Applicazione](#avvio-dellapplicazione)
- [Comandi Disponibili](#comandi-disponibili)
- [Parametri del Comando Generate](#parametri-del-comando-generate)
- [Esempi Base](#esempi-base)
- [Esempi per Scala](#esempi-per-scala)
- [Esempi per Stile Armonico](#esempi-per-stile-armonico)
- [Esempi per Complessità](#esempi-per-complessità)
- [Esempi per Modulazione](#esempi-per-modulazione)
- [Esempi Avanzati](#esempi-avanzati)

---

## 🚀 Avvio dell'Applicazione

### Metodo 1: Esecuzione diretta del JAR (consigliato)

```bash
java -jar j-harmonix-cli/target/j-harmonix-cli-0.1.0-SNAPSHOT.jar
```

### Metodo 2: Via Maven

```bash
mvn -pl j-harmonix-cli spring-boot:run
```

### Nota

Se vedi il warning `"Unable to create a system terminal, creating a dumb terminal"`, è normale e non impedisce il funzionamento dell'applicazione.

---

## 📦 Comandi Disponibili

Una volta avviata la shell (`J-HarmoniX:>`), sono disponibili i seguenti comandi:

| Comando      | Descrizione                                    |
|--------------|------------------------------------------------|
| `help`       | Mostra l'elenco completo dei comandi           |
| `scales`     | Lista tutte le scale disponibili               |
| `styles`     | Lista tutti gli stili armonici disponibili     |
| `generate`   | Genera una progressione armonica               |
| `exit`       | Esce dall'applicazione                         |
| `quit`       | Esce dall'applicazione                         |
| `clear`      | Pulisce lo schermo                             |
| `history`    | Mostra la cronologia dei comandi               |

---

## ⚙️ Parametri del Comando Generate

```bash
generate --tonic <nota> --scale <tipo_scala> --form <forma> --style <stile> --complexity <livello> --modulation <frequenza>
```

### --tonic (Tonica)
La nota di partenza della progressione.

**Valori:** `C`, `C#`, `Db`, `D`, `D#`, `Eb`, `E`, `F`, `F#`, `Gb`, `G`, `G#`, `Ab`, `A`, `A#`, `Bb`, `B`

**Default:** `C`

---

### --scale (Tipo di Scala)

| Scala                  | Valore                | Descrizione                          |
|------------------------|-----------------------|--------------------------------------|
| Maggiore               | `MAJOR`               | Scala maggiore tradizionale          |
| Minore Naturale        | `NATURAL_MINOR`       | Scala minore naturale (eolica)       |
| Minore Armonica        | `HARMONIC_MINOR`      | Scala minore armonica                |
| Minore Melodica        | `MELODIC_MINOR`       | Scala minore melodica                |
| Dorica                 | `DORIAN`              | Modo dorico                          |
| Frigia                 | `PHRYGIAN`            | Modo frigio                          |
| Lidia                  | `LYDIAN`              | Modo lidio                           |
| Misolidia              | `MIXOLYDIAN`          | Modo misolidio                       |
| Locria                 | `LOCRIAN`             | Modo locrio                          |
| Toni Interi            | `WHOLE_TONE`          | Scala a toni interi                  |
| Diminuita (T-ST)       | `DIMINISHED_WH`       | Scala diminuita tono-semitono        |
| Diminuita (ST-T)       | `DIMINISHED_HW`       | Scala diminuita semitono-tono        |
| Blues                  | `BLUES`               | Scala blues                          |
| Pentatonica Maggiore   | `PENTATONIC_MAJOR`    | Pentatonica maggiore                 |
| Pentatonica Minore     | `PENTATONIC_MINOR`    | Pentatonica minore                   |

**Default:** `MAJOR`

---

### --form (Forma)

Definisce la struttura della canzone.

**Valori:** Qualsiasi stringa (es. `AABA`, `verse-chorus-bridge`, `intro-verse-chorus-outro`)

**Default:** `AABA`

---

### --style (Stile Armonico)

| Stile           | Valore            | Caratteristiche                                           |
|-----------------|-------------------|-----------------------------------------------------------|
| Semplice        | `SIMPLE`          | Triadi, solo accordi diatonici                            |
| Pop             | `POP`             | Alcuni accordi di settima, no sostituzioni jazz           |
| Jazz Standard   | `JAZZ_STANDARD`   | ii-V-I, turnaround, estensioni base                       |
| Jazz Moderno    | `JAZZ_MODERN`     | Dominanti alterate, sostituzioni di tritono, estensioni   |

**Default:** `JAZZ_STANDARD`

---

### --complexity (Livello di Complessità)

| Livello            | Valore             | Tipo di Accordi                      |
|--------------------|--------------------|--------------------------------------|
| Triadi             | `TRIADS`           | Accordi a 3 note                     |
| Settime            | `SEVENTH_CHORDS`   | Accordi di settima                   |
| None               | `NINTHS`           | Accordi di nona                      |
| Estensioni Complete| `FULL_EXTENSIONS`  | Accordi con 9e, 11e, 13e             |

**Default:** `SEVENTH_CHORDS`

---

### --modulation (Frequenza di Modulazione)

| Frequenza | Valore   | Comportamento                                |
|-----------|----------|----------------------------------------------|
| Nessuna   | `NONE`   | Nessuna modulazione                          |
| Bassa     | `LOW`    | Modulazioni rare con accordi pivot           |
| Media     | `MEDIUM` | Modulazioni con progressioni ii-V secondarie |
| Alta      | `HIGH`   | Modulazioni frequenti, sostituzioni avanzate |

**Default:** `MEDIUM`

---

## 🎹 Esempi Base

### Progressione Jazz Standard Semplice

```bash
generate --tonic C --scale MAJOR --form AABA --style JAZZ_STANDARD
```

### Progressione in Tonalità con Diesis

```bash
generate --tonic F# --scale MAJOR --form verse-chorus-bridge --style JAZZ_MODERN
```

### Progressione in Tonalità con Bemolle

```bash
generate --tonic Bb --scale MAJOR --form AABA --style JAZZ_STANDARD
```

---

## 🎼 Esempi per Scala

### Scale Maggiori e Minori

```bash
# Scala maggiore
generate --tonic C --scale MAJOR --form AABA --style JAZZ_STANDARD

# Minore naturale
generate --tonic A --scale NATURAL_MINOR --form AABA --style JAZZ_STANDARD

# Minore armonica (tipica per ballad jazz)
generate --tonic D --scale HARMONIC_MINOR --form AABA --style JAZZ_MODERN

# Minore melodica (per improvvisazione jazz)
generate --tonic G --scale MELODIC_MINOR --form verse-chorus --style JAZZ_MODERN
```

---

### Modi

```bash
# Dorico (ottimo per jazz modale)
generate --tonic D --scale DORIAN --form verse-chorus --style JAZZ_MODERN

# Frigio (sound spagnolo/flamenco)
generate --tonic E --scale PHRYGIAN --form AABA --style JAZZ_MODERN

# Lidio (sound sognante, #11)
generate --tonic F --scale LYDIAN --form verse-chorus-bridge --style JAZZ_MODERN

# Misolidio (sound blues/rock)
generate --tonic G --scale MIXOLYDIAN --form verse-chorus --style POP

# Locrio (instabile, per passaggi)
generate --tonic B --scale LOCRIAN --form AABA --style JAZZ_MODERN
```

---

### Scale Simmetriche

```bash
# Scala a toni interi (Debussy style)
generate --tonic C --scale WHOLE_TONE --form verse-chorus --style JAZZ_MODERN

# Diminuita tono-semitono (per dominanti)
generate --tonic C --scale DIMINISHED_WH --form AABA --style JAZZ_MODERN

# Diminuita semitono-tono (per accordi diminuiti)
generate --tonic C --scale DIMINISHED_HW --form verse-chorus --style JAZZ_MODERN
```

---

### Scale Pentatoniche e Blues

```bash
# Pentatonica maggiore (per pop/country)
generate --tonic G --scale PENTATONIC_MAJOR --form verse-chorus-bridge --style POP

# Pentatonica minore (per rock/blues)
generate --tonic A --scale PENTATONIC_MINOR --form verse-chorus --style POP

# Blues (per progressioni blues classiche)
generate --tonic E --scale BLUES --form verse-chorus --style POP
```

---

## 🎨 Esempi per Stile Armonico

### SIMPLE – Per Pop Semplice

```bash
# Solo triadi diatoniche
generate --tonic C --scale MAJOR --form verse-chorus-bridge --style SIMPLE

generate --tonic G --scale MAJOR --form verse-chorus --style SIMPLE

generate --tonic Am --scale NATURAL_MINOR --form AABA --style SIMPLE
```

---

### POP – Per Composizioni Pop/Rock

```bash
# Accordi di settima senza sostituzioni jazz
generate --tonic D --scale MAJOR --form verse-chorus-bridge --style POP

generate --tonic A --scale MAJOR --form intro-verse-chorus-bridge-outro --style POP

generate --tonic E --scale MIXOLYDIAN --form verse-chorus --style POP
```

---

### JAZZ_STANDARD – Jazz Classico

```bash
# Progressioni ii-V-I e turnaround
generate --tonic F --scale MAJOR --form AABA --style JAZZ_STANDARD

generate --tonic Bb --scale MAJOR --form AABA --style JAZZ_STANDARD

generate --tonic Eb --scale MAJOR --form verse-bridge-verse --style JAZZ_STANDARD
```

---

### JAZZ_MODERN – Jazz Contemporaneo

```bash
# Sostituzioni di tritono, dominanti alterate
generate --tonic C --scale MAJOR --form AABA --style JAZZ_MODERN

generate --tonic G --scale MELODIC_MINOR --form verse-chorus --style JAZZ_MODERN

generate --tonic Db --scale MAJOR --form AABA --style JAZZ_MODERN
```

---

## 🎯 Esempi per Complessità

### TRIADS – Triadi Base

```bash
generate --tonic C --scale MAJOR --form AABA --style SIMPLE --complexity TRIADS

generate --tonic G --scale MAJOR --form verse-chorus --style POP --complexity TRIADS
```

---

### SEVENTH_CHORDS – Accordi di Settima

```bash
generate --tonic F --scale MAJOR --form AABA --style JAZZ_STANDARD --complexity SEVENTH_CHORDS

generate --tonic Bb --scale DORIAN --form verse-chorus --style JAZZ_STANDARD --complexity SEVENTH_CHORDS
```

---

### NINTHS – Accordi di Nona

```bash
generate --tonic D --scale MAJOR --form AABA --style JAZZ_MODERN --complexity NINTHS

generate --tonic A --scale MELODIC_MINOR --form verse-chorus --style JAZZ_MODERN --complexity NINTHS
```

---

### FULL_EXTENSIONS – Estensioni Complete

```bash
# Nona, undicesima, tredicesima
generate --tonic C --scale MAJOR --form AABA --style JAZZ_MODERN --complexity FULL_EXTENSIONS

generate --tonic Eb --scale MAJOR --form verse-chorus-bridge --style JAZZ_MODERN --complexity FULL_EXTENSIONS

generate --tonic F# --scale LYDIAN --form AABA --style JAZZ_MODERN --complexity FULL_EXTENSIONS
```

---

## 🌀 Esempi per Modulazione

### NONE – Nessuna Modulazione

```bash
# Rimane nella tonalità originale
generate --tonic C --scale MAJOR --form AABA --style JAZZ_STANDARD --modulation NONE

generate --tonic G --scale MAJOR --form verse-chorus --style POP --modulation NONE
```

---

### LOW – Modulazioni Rare

```bash
# Modulazioni occasionali con accordi pivot
generate --tonic F --scale MAJOR --form AABA --style JAZZ_STANDARD --modulation LOW

generate --tonic Bb --scale MAJOR --form verse-chorus-bridge --style JAZZ_STANDARD --modulation LOW
```

---

### MEDIUM – Modulazioni Moderate

```bash
# Progressioni ii-V secondarie
generate --tonic C --scale MAJOR --form AABA --style JAZZ_STANDARD --modulation MEDIUM

generate --tonic D --scale MAJOR --form verse-bridge-verse --style JAZZ_MODERN --modulation MEDIUM
```

---

### HIGH – Modulazioni Frequenti

```bash
# Sostituzioni di tritono, progressioni cromatiche
generate --tonic G --scale MAJOR --form AABA --style JAZZ_MODERN --modulation HIGH

generate --tonic Eb --scale MAJOR --form verse-chorus-bridge --style JAZZ_MODERN --modulation HIGH

generate --tonic C --scale MELODIC_MINOR --form AABA --style JAZZ_MODERN --modulation HIGH
```

---

## 🚀 Esempi Avanzati

### Ballad Jazz Complessa

```bash
generate --tonic Eb --scale MAJOR --form intro-AABA-outro --style JAZZ_MODERN --complexity FULL_EXTENSIONS --modulation HIGH
```

---

### Jazz Modale (Miles Davis Style)

```bash
generate --tonic D --scale DORIAN --form verse-chorus --style JAZZ_MODERN --complexity NINTHS --modulation LOW
```

---

### Fusion Jazz (Herbie Hancock Style)

```bash
generate --tonic C --scale MELODIC_MINOR --form verse-solo-chorus --style JAZZ_MODERN --complexity FULL_EXTENSIONS --modulation HIGH
```

---

### Progressione Blues Jazz

```bash
generate --tonic Bb --scale BLUES --form 12-bar-blues --style JAZZ_STANDARD --complexity SEVENTH_CHORDS --modulation LOW
```

---

### Sound Avant-Garde

```bash
generate --tonic C --scale WHOLE_TONE --form free-form --style JAZZ_MODERN --complexity FULL_EXTENSIONS --modulation HIGH

generate --tonic F# --scale DIMINISHED_WH --form AABA --style JAZZ_MODERN --complexity FULL_EXTENSIONS --modulation HIGH
```

---

### Progressione Pop Moderna

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

## 🎓 Combinazioni Suggerite per Genere

### Jazz Tradizionale (1920s-1940s)
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

### Esplorare Tutte le Scale
```bash
# Prima visualizza le scale disponibili
scales

# Poi prova ciascuna con le stesse impostazioni
generate --tonic C --scale MAJOR --form AABA --style JAZZ_MODERN
generate --tonic C --scale DORIAN --form AABA --style JAZZ_MODERN
generate --tonic C --scale LYDIAN --form AABA --style JAZZ_MODERN
# ... e così via
```

---

### Esplorare Tutti gli Stili
```bash
# Visualizza gli stili disponibili
styles

# Confronta lo stesso setup con stili diversi
generate --tonic C --scale MAJOR --form verse-chorus --style SIMPLE
generate --tonic C --scale MAJOR --form verse-chorus --style POP
generate --tonic C --scale MAJOR --form verse-chorus --style JAZZ_STANDARD
generate --tonic C --scale MAJOR --form verse-chorus --style JAZZ_MODERN
```

---

### Sperimentare con la Complessità
```bash
# Stessa progressione con complessità crescente
generate --tonic G --scale MAJOR --form AABA --style JAZZ_STANDARD --complexity TRIADS
generate --tonic G --scale MAJOR --form AABA --style JAZZ_STANDARD --complexity SEVENTH_CHORDS
generate --tonic G --scale MAJOR --form AABA --style JAZZ_STANDARD --complexity NINTHS
generate --tonic G --scale MAJOR --form AABA --style JAZZ_STANDARD --complexity FULL_EXTENSIONS
```

---

### Testare Modulazioni
```bash
# Stesso setup con modulazioni diverse
generate --tonic C --scale MAJOR --form AABA --style JAZZ_MODERN --modulation NONE
generate --tonic C --scale MAJOR --form AABA --style JAZZ_MODERN --modulation LOW
generate --tonic C --scale MAJOR --form AABA --style JAZZ_MODERN --modulation MEDIUM
generate --tonic C --scale MAJOR --form AABA --style JAZZ_MODERN --modulation HIGH
```

---

## 🛠️ Utilità

### Cronologia
```bash
# Visualizza tutti i comandi precedenti
history

# Ripeti l'ultimo comando premendo freccia su ↑
```

---

### Pulisci Schermo
```bash
clear
```

---

### Aiuto Inline
```bash
# Help generale
help

# Help per un comando specifico
help generate
```

---

## 📝 Note Importali

- I parametri possono essere specificati in qualsiasi ordine
- I valori con spazi vanno messi tra virgolette: `--form "verse chorus bridge"`
- Usa `Ctrl+C` per interrompere una generazione in corso
- Usa `exit` o `quit` per chiudere l'applicazione
- I nomi delle note supportano sia diesis (#) che bemolle (b)

---

## 🔧 Troubleshooting

### L'applicazione non si avvia
```bash
# Ricompila il progetto
mvn clean install -DskipTests

# Riprova a eseguire il JAR
java -jar j-harmonix-cli/target/j-harmonix-cli-0.1.0-SNAPSHOT.jar
```

---

### Errore "Unknown scale type"
```bash
# Controlla le scale disponibili
scales

# Usa il valore esatto dall'elenco (case-insensitive)
```

---

### Errore "Unknown harmony style"
```bash
# Controlla gli stili disponibili
styles

# Usa: SIMPLE, POP, JAZZ_STANDARD, o JAZZ_MODERN
```

---

## 🎉 Buon Divertimento!

Sperimenta liberamente con tutte le combinazioni per scoprire progressioni uniche e interessanti!

Per feedback o segnalazioni: [GitHub Issues](https://github.com/alfdagos/J-Harmonix/issues)

---

**J-Harmonix** – *Bringing jazz harmony to your fingertips* 🎵
