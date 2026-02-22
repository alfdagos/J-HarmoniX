# Analisi Tecnica: Generatore di Armonie CLI (Java/Spring)

Questo documento delinea la struttura e la logica del generatore di armonie richiesto, focalizzandosi sull'integrazione tra teoria musicale jazz e architettura software moderna.

## 1. Visione del Progetto
L'obiettivo è creare uno strumento da riga di comando che permetta a un musicista o compositore di generare rapidamente griglie armoniche coerenti. Il sistema non si limiterà a scegliere accordi casuali, ma seguirà regole di **conduzione delle voci** e **funzioni armoniche** (Tonica, Sottodominante, Dominante).

## 2. Architettura del Sistema
Il progetto sarà strutturato utilizzando **Spring Boot** per gestire la configurazione, l'iniezione delle dipendenze e potenzialmente un'interfaccia shell interattiva (Spring Shell).

| Componente | Responsabilità |
| :--- | :--- |
| **CLI Controller** | Gestisce l'input dell'utente (tonalità, schema, parametri jazz). |
| **Harmony Engine** | Il "cuore" algoritmico che genera le sequenze di accordi. |
| **Music Model** | Classi che rappresentano Note, Accordi, Scale e Gradi. |
| **Exporter** | Formatta la griglia in testo leggibile o file (es. PDF/MIDI). |

## 3. Modello del Dominio Musicale
Per garantire flessibilità, il sistema utilizzerà un modello basato sulla teoria degli insiemi musicali:
- **Note**: Rappresentate numericamente (0-11) per facilitare i calcoli traspositivi.
- **Accordi**: Definiti come strutture di intervalli sopra una fondamentale (es. 1-3-5-7 per un accordo di settima).
- **Scale**: Insiemi di note che definiscono il "pool" armonico disponibile.

## 4. Logica di Generazione e Algoritmi
Il generatore utilizzerà una combinazione di catene di Markov e regole euristiche:

> "La progressione armonica nel jazz non è solo una successione di accordi, ma un movimento verso punti di risoluzione, tipicamente rappresentati dalla cadenza ii-V-I."

### Funzionalità Jazz Avanzate
- **Sostituzione di Tritono**: Possibilità di sostituire un accordo di dominante con uno a distanza di tritono (es. G7 -> Db7).
- **Modulazioni**: Passaggi a tonalità vicine (circolo delle quinte) o lontane per sezioni specifiche come il Bridge.
- **Estensioni**: Aggiunta automatica di 9e, 11e e 13e in base al contesto armonico per ottenere il tipico "sound" jazz.

## 5. Esempio di Workflow Utente
1. **Input**: Tonalità: Do Maggiore | Schema: AABA | Stile: Jazz Standard.
2. **Elaborazione**: L'engine genera una sezione A con un turnaround I-VI-ii-V e una sezione B che modula alla sottodominante (Fa).
3. **Output**: Una griglia testuale chiara:
   `| Cmaj7 | Am7 | Dm7 | G7 |`

## 6. Prossimi Passi Suggeriti
Dopo l'approvazione di questa analisi, procederemo con:
1. Configurazione del progetto Maven/Gradle con Spring Boot.
2. Implementazione del modello delle classi musicali.
3. Sviluppo dell'algoritmo di generazione per la sezione "Strofa".
