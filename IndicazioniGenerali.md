# Piano di Sviluppo: Generatore di Armonie CLI (Java/Spring)

**Autore**: Manus AI
**Data**: 14 Febbraio 2026
**Versione**: 1.0

## 1. Introduzione e Obiettivi del Piano

Questo documento delinea il piano di sviluppo per il generatore di armonie, con un focus sulla creazione di un'architettura modulare e 
flessibile che possa essere estesa in futuro a piattaforme web e mobile. L'obiettivo è garantire che il cuore logico dell'applicazione rimanga indipendente dalle interfacce utente, facilitando la riusabilità e riducendo i costi di manutenzione per le future evoluzioni.

## 2. Architettura Generale: Hexagonal Architecture (Ports and Adapters)

Per raggiungere gli obiettivi di flessibilità e scalabilità, adotteremo un'architettura **Hexagonal** (anche nota come Ports and Adapters) [1]. Questa architettura isola la logica di business (il dominio musicale e gli algoritmi di generazione armonica) da tutti i dettagli esterni, come l'interfaccia utente (CLI, Web, Mobile), i database o i servizi esterni. Ciò significa che il "cuore" dell'applicazione non sa nulla di come viene utilizzato o di dove i suoi dati vengono memorizzati.

### Vantaggi Chiave:
-   **Indipendenza dall'Interfaccia**: La logica di business non è legata a una specifica interfaccia utente (CLI, Web, Mobile), rendendo facile aggiungere nuove interfacce senza modificare il core.
-   **Testabilità Migliorata**: Il core dell'applicazione può essere testato in modo isolato, senza la necessità di avviare l'intera applicazione o simulare interazioni utente.
-   **Flessibilità Tecnologica**: È possibile cambiare facilmente le tecnologie esterne (es. da CLI a Web, da un database all'altro) senza impattare la logica principale.

## 3. Moduli Core (Dominio Musicale e Logica Armonica)

Questi moduli rappresentano il **Core Domain** dell'applicazione. Sono agnostici rispetto a qualsiasi tecnologia di interfaccia o persistenza e contengono la logica di business pura.

### 3.1. `music-model` (Dominio Musicale)

Questo modulo conterrà le definizioni di tutte le entità musicali fondamentali. Sarà la base per l'intero sistema, fornendo un linguaggio comune per descrivere i concetti musicali.

| Classe/Interfaccia | Descrizione | Responsabilità |
| :--- | :--- | :--- |
| `Note` | Rappresentazione di una singola nota musicale (es. C, C#, D). | Gestione del pitch (valore MIDI), nome, ottava. Operazioni di trasposizione. |
| `Interval` | Rappresentazione di un intervallo musicale (es. terza maggiore, quinta giusta). | Calcolo della distanza tra note, costruzione di accordi e scale. |
| `Scale` | Definizione di una scala musicale (es. Maggiore, Minore Armonica, Blues). | Contiene le note di una scala, calcolo dei gradi, generazione di accordi diatonici. |
| `Chord` | Rappresentazione di un accordo (es. Cmaj7, Dm7b5). | Fondamentale, qualità dell'accordo (es. Major7, Minor7, Dominant7). Generazione delle note che compongono l'accordo. |
| `KeySignature` | Rappresentazione di una tonalità (es. Do Maggiore, Sol Minore). | Gestione delle alterazioni, relazione con le scale diatonica. |
| `Progression` | Sequenza di accordi. | Mantenere l'ordine degli accordi, applicare regole di conduzione delle voci. |

### 3.2. `harmony-engine` (Logica Armonica)

Questo è il modulo che incapsula gli algoritmi di generazione delle armonie. Interagisce con il `music-model` per costruire e manipolare le strutture musicali.

| Classe/Interfaccia | Descrizione | Responsabilità |
| :--- | :--- | :--- |
| `HarmonyGeneratorService` | Interfaccia principale per la generazione di armonie. | Orchestrazione del processo di generazione, riceve i parametri utente. |
| `ChordSelector` | Algoritmo per la selezione degli accordi. | Sceglie gli accordi appropriati in base alla funzione armonica e al contesto. |
| `ModulationStrategy` | Implementazione delle diverse tecniche di modulazione. | Gestisce i cambi di tonalità (Pivot Chord, ii-V secondario, Tritone Sub). |
| `JazzRuleEngine` | Motore di regole per l'applicazione di specificità jazz. | Aggiunta di estensioni (9e, 11e, 13e), sostituzioni (Tritone Sub), turnarounds. |
| `StructureComposer` | Gestisce la struttura del brano (AABA, Strofa-Ritornello). | Assembla le sezioni armoniche in base allo schema definito. |

## 4. Moduli Adattatori (Interfacce Esterne)

Questi moduli fungono da "adattatori" tra il Core Domain e il mondo esterno. Implementano le "porte" definite dal Core Domain per interagire con esso.

### 4.1. `cli-adapter` (Interfaccia a Riga di Comando - Attuale)

Questo modulo fornirà l'interfaccia utente a riga di comando, utilizzando **Spring Shell** per un'esperienza interattiva.

| Classe/Interfaccia | Descrizione | Responsabilità |
| :--- | :--- | :--- |
| `HarmonyCliCommands` | Componente Spring Shell. | Mappa i comandi CLI agli `HarmonyGeneratorService` del core. Gestisce l'input e l'output testuale. |
| `InputParser` | Utility per il parsing. | Converte l'input testuale dell'utente in oggetti del `music-model` o parametri per l'engine. |
| `OutputFormatter` | Utility per la formattazione. | Converte le `Progression` generate in un formato testuale leggibile per la CLI. |

### 4.2. `web-adapter` (Interfaccia Web - Futura)

Questo modulo, da sviluppare in futuro, esporrà la funzionalità del `harmony-engine` tramite un'API RESTful, utilizzando **Spring WebFlux** o **Spring MVC**.

| Classe/Interfaccia | Descrizione | Responsabilità |
| :--- | :--- | :--- |
| `HarmonyRestController` | Controller Spring REST. | Espone endpoint HTTP per la generazione di armonie. |
| `DTOs` | Data Transfer Objects. | Mappatura tra i dati JSON/XML e gli oggetti del `music-model`. |

### 4.3. `mobile-adapter` (Interfaccia Mobile - Futura)

Questo modulo, anch'esso futuro, potrebbe essere un client nativo o ibrido che interagisce con il `web-adapter` o, in scenari specifici, direttamente con il `harmony-engine` se l'applicazione mobile fosse autonoma (meno probabile con l'approccio di generazione automatica da codice web).

### 4.4. `persistence-adapter` (Persistenza - Futura/Opzionale)

Questo modulo gestirà il salvataggio e il caricamento di configurazioni utente, modelli di progressione o armonie generate. Inizialmente non sarà necessario per la CLI, ma diventerà rilevante per le versioni Web/Mobile.

| Classe/Interfaccia | Descrizione | Responsabilità |
| :--- | :--- | :--- |
| `HarmonyRepository` | Interfaccia per la persistenza. | Definisce i metodi per salvare e recuperare le armonie. |
| `JpaHarmonyRepository` | Implementazione JPA/Hibernate. | Adattatore per database relazionali. |
| `JsonFileHarmonyRepository` | Implementazione per file JSON. | Adattatore per la persistenza su file (utile per la CLI). |

## 5. Fasi di Sviluppo (Roadmap)

Il progetto sarà sviluppato in fasi incrementali, partendo dalla funzionalità CLI e predisponendo per le future estensioni.

### Fase 1: Setup del Progetto e Modulo `music-model`
-   Inizializzazione del progetto Spring Boot (Maven/Gradle).
-   Definizione delle classi `Note`, `Interval`, `Scale`, `Chord`, `KeySignature` nel modulo `music-model`.
-   Implementazione dei test unitari per il `music-model`.

### Fase 2: Modulo `harmony-engine` (Logica Base)
-   Implementazione di `HarmonyGeneratorService` con logica di base per progressioni diatoniche semplici (es. ii-V-I in tonalità maggiore).
-   Sviluppo di `ChordSelector` e `JazzRuleEngine` (solo estensioni di base, es. 7e).
-   Implementazione dei test unitari per il `harmony-engine`.

### Fase 3: Modulo `cli-adapter` (Interfaccia CLI Iniziale)
-   Integrazione di Spring Shell.
-   Implementazione di `HarmonyCliCommands` per accettare tonalità e schema base.
-   `InputParser` e `OutputFormatter` per la visualizzazione testuale.
-   Test di integrazione della CLI.

### Fase 4: Estensioni Jazz Avanzate nel `harmony-engine`
-   Implementazione completa di `ModulationStrategy` (Pivot Chord, ii-V Secondario, Tritone Sub).
-   Estensione di `JazzRuleEngine` per gestire sostituzioni complesse e turnarounds.
-   Aggiunta di parametri utente per controllare la complessità jazz e la frequenza delle modulazioni.
-   Test unitari e di integrazione per le nuove funzionalità.

### Fase 5: Refactoring e Preparazione per il Web (Opzionale, ma consigliato)
-   Revisione del codice per garantire la conformità all'architettura esagonale.
-   Creazione di interfacce `Port` nel `harmony-engine` che verranno implementate dagli `Adapter`.

### Fase 6: Sviluppo `web-adapter` (Futuro)
-   Creazione del modulo `web-adapter`.
-   Implementazione di `HarmonyRestController` con endpoint RESTful.
-   Sviluppo di DTO per l'interazione JSON.
-   Test di integrazione dell'API Web.

### Fase 7: Sviluppo `mobile-adapter` (Futuro)
-   Utilizzo di strumenti di generazione automatica (es. React Native, Flutter) per creare l'interfaccia mobile che consuma l'API Web.

## 6. Tecnologie e Strumenti

-   **Linguaggio**: Java 17+ (o versione LTS più recente)
-   **Framework**: Spring Boot 3.x
-   **CLI**: Spring Shell
-   **Build Tool**: Maven o Gradle
-   **Test**: JUnit 5, Mockito
-   **IDE**: IntelliJ IDEA o VS Code
-   **Controllo Versione**: Git

## 7. Riferimenti

[1] Alistair Cockburn. *Hexagonal Architecture*. Disponibile su: [https://alistair.cockburn.us/hexagonal-architecture/](https://alistair.cockburn.us/hexagonal-architecture/)


[2] Spring Boot. *Spring.io*. Disponibile su: [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)
[3] Spring Shell. *Spring.io*. Disponibile su: [https://spring.io/projects/spring-shell](https://spring.io/projects/spring-shell)
