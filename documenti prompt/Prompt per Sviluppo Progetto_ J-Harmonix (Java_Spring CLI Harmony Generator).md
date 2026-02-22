# Prompt per Sviluppo Progetto: J-Harmonix (Java/Spring CLI Harmony Generator)

**Istruzioni per l'IA**: Agisci come un Senior Software Architect ed Esperto di Teoria Musicale Jazz. Il tuo compito è iniziare lo sviluppo di un'applicazione CLI in Java chiamata **J-Harmonix**.

## 1. Visione del Progetto
L'obiettivo è creare un generatore di armonie jazz da riga di comando. L'applicazione deve essere progettata seguendo l'**Architettura Esagonale (Ports and Adapters)** per consentire future espansioni verso interfacce Web e Mobile (iOS/Android).

## 2. Requisiti Tecnici
- **Linguaggio**: Java 17+
- **Framework**: Spring Boot 3.x
- **Interfaccia**: Spring Shell (per la CLI interattiva)
- **Build Tool**: Maven
- **Architettura**: Modulare (Hexagonal), separando nettamente il `Core Domain` (logica musicale) dagli `Adapters` (CLI, API, Persistenza).

## 3. Core Domain (Music Theory & Logic)
Implementa un modello del dominio solido che includa:
- **Note**: Rappresentazione numerica (0-11) e nominale (C, C#, etc.).
- **Accordi**: Supporto per triadi e quadriadi (7e), con estensioni (9, 11, 13).
- **Scale**: Maggiore, Minore (Naturale, Armonica, Melodica), Modi e Scale Jazz (es. Alterata).
- **Harmony Engine**: Algoritmo per generare progressioni basate su funzioni armoniche (Tonica, Sottodominante, Dominante).
- **Jazz Logic**: Implementa cadenze ii-V-I, turnaround I-VI-ii-V e **Sostituzioni di Tritono**.
- **Modulazioni**: Gestisci passaggi tra tonalità tramite **Pivot Chords** e **ii-V secondari**.

## 4. Struttura dei Moduli Richiesta
1. `j-harmonix-core`: Contiene il `music-model` e l'`harmony-engine`. Nessuna dipendenza da framework esterni (solo Java puro).
2. `j-harmonix-cli`: Adapter Spring Shell che consuma il core.
3. `j-harmonix-api` (Opzionale/Placeholder): Predisposizione per endpoint REST.

## 5. Task Iniziale
Inizia definendo la struttura del progetto Maven multi-modulo e implementa le classi base del `music-model` (`Note`, `Chord`, `Interval`). Assicurati che il codice sia testabile tramite JUnit 5 e che la logica di trasposizione sia accurata.

---
**Note per Claude**: Mantieni un tono professionale, scrivi codice pulito (Clean Code) e commenta le parti più complesse della teoria musicale implementata.
