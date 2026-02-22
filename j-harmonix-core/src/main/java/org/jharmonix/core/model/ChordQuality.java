package org.jharmonix.core.model;

/**
 * Defines the interval structure of a chord quality.
 *
 * <p>Each constant carries the semitone intervals from the root that form the
 * chord, using the compact notation common in computer-music literature
 * (pitch-class sets starting at 0). The display symbol is the standard jazz
 * lead-sheet abbreviation.
 *
 * <h3>Naming convention for symbols</h3>
 * <ul>
 *   <li>{@code maj7}  – major seventh chord</li>
 *   <li>{@code m7}    – minor seventh chord</li>
 *   <li>{@code 7}     – dominant seventh chord</li>
 *   <li>{@code m7b5}  – half-diminished (ø) chord</li>
 *   <li>{@code dim7}  – fully diminished seventh chord</li>
 *   <li>{@code 7b9}   – dominant seventh with flat nine</li>
 *   <li>{@code maj9}  – major ninth chord</li>
 *   <li>{@code m9}    – minor ninth chord</li>
 *   <li>{@code 9}     – dominant ninth chord</li>
 * </ul>
 */
public enum ChordQuality {

    // ---- Triads ----
    MAJOR_TRIAD      ("",      new int[]{0, 4, 7}),
    MINOR_TRIAD      ("m",     new int[]{0, 3, 7}),
    DIM_TRIAD        ("dim",   new int[]{0, 3, 6}),
    AUG_TRIAD        ("aug",   new int[]{0, 4, 8}),

    // ---- Seventh chords ----
    MAJOR_SEVENTH    ("maj7",  new int[]{0, 4, 7, 11}),
    MINOR_SEVENTH    ("m7",    new int[]{0, 3, 7, 10}),
    DOMINANT_SEVENTH ("7",     new int[]{0, 4, 7, 10}),
    HALF_DIMINISHED  ("m7b5",  new int[]{0, 3, 6, 10}),   // ø7
    DIMINISHED_SEVENTH("dim7", new int[]{0, 3, 6, 9}),
    MINOR_MAJOR_SEVENTH("mMaj7", new int[]{0, 3, 7, 11}), // used in minor harmony

    // ---- Altered dominant ----
    DOM_SEVENTH_FLAT_NINE    ("7b9",   new int[]{0, 4, 7, 10, 13}),
    DOM_SEVENTH_SHARP_NINE   ("7#9",   new int[]{0, 4, 7, 10, 15}),
    DOM_SEVENTH_FLAT_FIVE    ("7b5",   new int[]{0, 4, 6, 10}),
    DOM_SEVENTH_SHARP_ELEVEN ("7#11",  new int[]{0, 4, 7, 10, 18}),

    // ---- Ninth chords ----
    MAJOR_NINTH    ("maj9",  new int[]{0, 4, 7, 11, 14}),
    MINOR_NINTH    ("m9",    new int[]{0, 3, 7, 10, 14}),
    DOMINANT_NINTH ("9",     new int[]{0, 4, 7, 10, 14}),

    // ---- Eleventh / thirteenth ----
    DOMINANT_ELEVENTH  ("11",  new int[]{0, 4, 7, 10, 14, 17}),
    DOMINANT_THIRTEENTH("13",  new int[]{0, 4, 7, 10, 14, 21}),
    MINOR_ELEVENTH     ("m11", new int[]{0, 3, 7, 10, 14, 17}),

    // ---- Suspended ----
    SUS2 ("sus2", new int[]{0, 2, 7}),
    SUS4 ("sus4", new int[]{0, 5, 7}),
    DOM_SUS4_SEVENTH("7sus4", new int[]{0, 5, 7, 10});

    // -------------------------------------------------------------------------

    /** Standard lead-sheet display symbol appended after the root name. */
    private final String symbol;

    /**
     * Interval structure: semitones above the root.
     * Index 0 is always 0 (the root itself).
     */
    private final int[] intervals;

    ChordQuality(String symbol, int[] intervals) {
        this.symbol    = symbol;
        this.intervals = intervals.clone();
    }

    /** Returns the lead-sheet symbol (e.g. {@code "m7"}, {@code "maj7"}). */
    public String getSymbol() { return symbol; }

    /**
     * Returns a defensive copy of the interval array (semitones from root).
     * Index 0 is always 0.
     */
    public int[] getIntervals() { return intervals.clone(); }

    /** Returns the number of notes (chord members) in this quality. */
    public int size() { return intervals.length; }
}
