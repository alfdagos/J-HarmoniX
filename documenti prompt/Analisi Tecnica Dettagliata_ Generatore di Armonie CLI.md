# Analisi Tecnica Dettagliata: Generatore di Armonie CLI

**Autore**: Manus AI
**Data**: 14 Febbraio 2026
**Versione**: 1.0

## 1. Introduzione e Obiettivi

Questo documento fornisce un'analisi tecnica completa per lo sviluppo di un'applicazione a riga di comando (CLI) in Java, finalizzata alla generazione di progressioni armoniche. L'obiettivo primario è creare uno strumento che assista musicisti e compositori nella stesura di griglie di accordi, basandosi su parametri definiti dall'utente e incorporando regole della teoria musicale, con un'enfasi particolare sull'armonia jazz.

Il sistema dovrà essere in grado di interpretare input come la tonalità, la struttura del brano (es. strofa, ritornello) e lo stile desiderato, per poi produrre una sequenza di accordi coerente e musicalmente valida. La logica non si baserà su una selezione casuale, ma su un modello che simula il processo compositivo, rispettando le funzioni armoniche di tonica, sottodominante e dominante.

## 2. Architettura del Sistema

Per garantire manutenibilità, scalabilità e un'organizzazione pulita del codice, il progetto verrà implementato utilizzando il framework **Spring Boot**. Questa scelta facilita la gestione delle dipendenze, la configurazione e offre la possibilità di integrare facilmente moduli aggiuntivi, come **Spring Shell** per un'interfaccia utente interattiva.

L'architettura sarà suddivisa in componenti logici con responsabilità ben definite, seguendo un approccio a strati.

| Componente | Tecnologia/Libreria | Responsabilità Principali |
| :--- | :--- | :--- |
| **CLI Controller** | Spring Shell | Gestire l'interazione con l'utente, raccogliere i parametri di input e validarli. |
| **Harmony Engine** | Logica custom Java | Contiene il nucleo algoritmico del sistema. È responsabile della generazione delle progressioni armoniche basandosi sulle regole definite. |
| **Music Model** | Classi POJO Java | Definisce le strutture dati che rappresentano i concetti musicali: `Note`, `Chord`, `Scale`, `KeySignature`. |
| **Exporter** | Java I/O | Formatta la griglia di accordi generata in un formato leggibile (testo semplice) e, in futuro, in altri formati come PDF o MIDI. |

## 3. Modello del Dominio Musicale

Un modello del dominio robusto e flessibile è cruciale per la correttezza delle operazioni armoniche. Le entità musicali fondamentali saranno modellate come oggetti immutabili per garantire la sicurezza in un contesto multithread.

- **Note**: Saranno rappresentate internamente da un valore intero (0-11, dove 0 è Do), un approccio comune in computer music che semplifica enormemente le operazioni di trasposizione e calcolo degli intervalli.
- **Accordi (Chord)**: Un accordo sarà definito non come un insieme statico di note, ma come una `root` (fondamentale) e una `ChordQuality` (es. maggiore, minore, dominante settima, semidiminuito). La qualità stessa è una struttura di intervalli (es. [0, 4, 7] per una triade maggiore). Questo permette di trasporre e analizzare gli accordi in modo efficiente.
- **Scale**: Rappresentate come un insieme ordinato di intervalli a partire da una tonica. La scala definisce il contesto diatonico e il pool di note e accordi disponibili per una data sezione.

## 4. Algoritmi di Generazione Armonica

Il cuore del sistema, l'**Harmony Engine**, utilizzerà un approccio ibrido che combina modelli probabilistici (come le catene di Markov) con un sistema di regole euristiche derivate dalla teoria musicale.

> La progressione armonica nel jazz non è solo una successione di accordi, ma un movimento verso punti di risoluzione, tipicamente rappresentati dalla cadenza ii-V-I. [1]

Il processo di generazione seguirà questi passi:
1.  **Definizione del Contesto**: In base alla tonalità e alla sezione del brano, viene determinata la scala di riferimento.
2.  **Assegnazione Funzionale**: Ad ogni battuta o segmento viene assegnata una funzione armonica (Tonica, Sottodominante, Dominante) basandosi su modelli strutturali comuni.
3.  **Selezione dell'Accordo**: Per ogni funzione, viene scelto un accordo diatonico appropriato. Ad esempio, per una funzione di Tonica in Do Maggiore, la scelta primaria è `Cmaj7`, con `Am7` (tonica relativa) come alternativa.
4.  **Applicazione Regole Jazz**: Se richiesto, vengono applicate trasformazioni specifiche per lo stile jazz, come l'estensione degli accordi (aggiunta di 7e, 9e) e le sostituzioni.

### 4.1. Gestione Avanzata delle Modulazioni Jazz

Le modulazioni, ovvero i cambi di centro tonale, verranno gestite con particolare attenzione per riflettere la fluidità dell'armonia jazz. L'utente potrà specificare un livello di 
frequenza delle modulazioni (es. "bassa", "media", "alta") che influenzerà la probabilità e il tipo di modulazione applicata. [2]

Le tecniche implementate includeranno:

#### 4.1.1. Modulazione per Accordo Comune (Pivot Chord)
Questa tecnica consente una transizione armonica fluida tra tonalità correlate. Il sistema identificherà un accordo che è diatonico in entrambe le tonalità (quella di partenza e quella di destinazione) e lo utilizzerà come "ponte" armonico. Ad esempio, in un passaggio da Do Maggiore a Sol Maggiore, l'accordo di La minore (vi grado in Do Maggiore) può essere reinterpretato come il ii grado in Sol Maggiore, facilitando una cadenza ii-V-I nella nuova tonalità. [3]

#### 4.1.2. Inserimento di Cadenze ii-V Secondarie
Per modulazioni più decise, spesso utilizzate in sezioni come il Bridge, il sistema genererà una cadenza ii-V che risolve sulla nuova tonica. Questo meccanismo permette di raggiungere tonalità anche più distanti in modo musicalmente logico. Ad esempio, per modulare da Do Maggiore a Mib Maggiore, l'algoritmo inserirà la sequenza `Fm7 - Bb7` (il ii-V di Mib) prima di risolvere su `Ebmaj7`. [4]

#### 4.1.3. Modulazione per Sostituzione di Tritono e Struttura Costante
Nel jazz moderno, le modulazioni possono avvenire anche attraverso tecniche cromatiche avanzate. La **sostituzione di tritono** è un esempio chiave: un accordo di dominante può essere sostituito da un altro accordo di dominante la cui fondamentale si trova a distanza di tritono (es. `G7` può essere sostituito da `Db7`). Questa sostituzione può essere utilizzata per creare modulazioni inaspettate ma armonicamente valide, spesso risolvendo mezzo tono sotto. [5]

| Tipo di Modulazione | Utilizzo Tipico | Complessità Armonica | Descrizione |
| :--- | :--- | :--- | :--- |
| **Diatonica (Pivot)** | Transizioni fluide tra sezioni con tonalità correlate. | Bassa | Identificazione di un accordo comune a due tonalità per facilitare il passaggio. |
| **ii-V Secondario** | Cambi di tonalità più marcati, tipici di Bridge o sezioni contrastanti. | Media | Inserimento di una cadenza ii-V che risolve sulla nuova tonica. |
| **Cromatica/Tritono** | Jazz avanzato, modulazioni repentine e inaspettate. | Alta | Sostituzione di un accordo di dominante con uno a distanza di tritono per creare movimento cromatico. |

## 5. Esempio di Workflow Utente

L'interazione con l'applicazione CLI sarà intuitiva e guidata. L'utente fornirà i parametri iniziali e il sistema restituirà la griglia armonica.

1.  **Input Utente**: L'utente avvia l'applicazione e inserisce i parametri richiesti, ad esempio:
    -   `tonalità`: Do Maggiore
    -   `schema`: AABA (Strofa-Strofa-Bridge-Strofa)
    -   `stile`: Jazz Standard
    -   `complessità`: 7e e estensioni
    -   `modulazioni`: Media

2.  **Elaborazione Harmony Engine**: Il motore armonico elabora i parametri, genera la struttura del brano e popola le sezioni con accordi coerenti, applicando le regole armoniche e le tecniche di modulazione jazz appropriate. Ad esempio, la sezione 'A' potrebbe utilizzare progressioni ii-V-I nella tonalità di Do Maggiore, mentre la sezione 'B' (Bridge) potrebbe modulare alla sottodominante (Fa Maggiore) tramite un ii-V secondario.

3.  **Output**: Il sistema presenta la griglia armonica in formato testuale leggibile:

    ```
    [Sezione A - Do Maggiore]
    | Cmaj7 | Am7 | Dm7 | G7 |
    | Cmaj7 | Fmaj7 | Dm7 | G7 |

    [Sezione A - Do Maggiore]
    | Cmaj7 | Am7 | Dm7 | G7 |
    | Cmaj7 | Fmaj7 | Dm7 | G7 |

    [Sezione B - Fa Maggiore (Modulazione via ii-V secondario)]
    | Gm7 | C7 | Fmaj7 | Bbmaj7 |
    | Em7b5 | A7b9 | Dm7 | G7 |

    [Sezione A - Do Maggiore]
    | Cmaj7 | Am7 | Dm7 | G7 |
    | Cmaj7 | Fmaj7 | Dm7 | G7 |
    ```

## 6. Prossimi Passi

Una volta approvata questa analisi dettagliata, i prossimi passi includeranno:

1.  **Configurazione del Progetto**: Inizializzazione di un progetto Maven o Gradle con Spring Boot.
2.  **Implementazione del Music Model**: Sviluppo delle classi Java per `Note`, `Chord`, `Scale`, `KeySignature`.
3.  **Sviluppo Harmony Engine**: Implementazione degli algoritmi di generazione armonica e delle regole di progressione, inclusa la logica per le modulazioni.
4.  **Interfaccia CLI**: Creazione dell'interfaccia utente a riga di comando con Spring Shell.
5.  **Test e Validazione**: Test approfonditi per garantire la correttezza musicale e la robustezza del sistema.

## 7. Riferimenti

[1] The Ultimate No Nonsense Guide to Jazz Harmony. *Jazz Guitar Lessons*. Disponibile su: [https://www.jazzguitarlessons.net/blog/the-ultimate-no-nonsense-guide-to-jazz-harmony](https://www.jazzguitarlessons.net/blog/the-ultimate-no-nonsense-guide-to-jazz-harmony)
[2] 12 essential jazz chord progressions to master jazz harmony. *Learn Jazz Standards*. Disponibile su: [https://www.learnjazzstandards.com/blog/jazz-chord-progressions/](https://www.learnjazzstandards.com/blog/jazz-chord-progressions/)
[3] A Generative Grammar for Jazz Chord Sequences (1984). *Hacker News*. Disponibile su: [https://news.ycombinator.com/item?id=25569056](https://news.ycombinator.com/item?id=25569056)
[4] How to Write a Jazz Composition's Chord Progression. *Digital Collections, Lipscomb University*. Disponibile su: [https://digitalcollections.lipscomb.edu/cgi/viewcontent.cgi?article=1254&context=jmtp](https://digitalcollections.lipscomb.edu/cgi/viewcontent.cgi?article=1254&context=jmtp)
[5] Armonia jazz. *Wikipedia*. Disponibile su: [https://it.wikipedia.org/wiki/Armonia_jazz](https://it.wikipedia.org/wiki/Armonia_jazz)
