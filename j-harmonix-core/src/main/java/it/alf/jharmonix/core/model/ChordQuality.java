package it.alf.jharmonix.core.model;

public enum ChordQuality {

    MAJOR_TRIAD      ("",      new int[]{0, 4, 7}),
    MINOR_TRIAD      ("m",     new int[]{0, 3, 7}),
    DIM_TRIAD        ("dim",   new int[]{0, 3, 6}),
    AUG_TRIAD        ("aug",   new int[]{0, 4, 8}),

    MAJOR_SEVENTH    ("maj7",  new int[]{0, 4, 7, 11}),
    MINOR_SEVENTH    ("m7",    new int[]{0, 3, 7, 10}),
    DOMINANT_SEVENTH ("7",     new int[]{0, 4, 7, 10}),
    HALF_DIMINISHED  ("m7b5",  new int[]{0, 3, 6, 10}),
    DIMINISHED_SEVENTH("dim7", new int[]{0, 3, 6, 9}),
    MINOR_MAJOR_SEVENTH("mMaj7", new int[]{0, 3, 7, 11}),

    DOM_SEVENTH_FLAT_NINE    ("7b9",   new int[]{0, 4, 7, 10, 13}),
    DOM_SEVENTH_SHARP_NINE   ("7#9",   new int[]{0, 4, 7, 10, 15}),
    DOM_SEVENTH_FLAT_FIVE    ("7b5",   new int[]{0, 4, 6, 10}),
    DOM_SEVENTH_SHARP_ELEVEN ("7#11",  new int[]{0, 4, 7, 10, 18}),
    DOM_SEVENTH_FLAT_THIRTEEN("7b13",  new int[]{0, 4, 7, 10, 20}),
    
    // Advanced altered dominants
    DOM_SEVEN_ALTERED        ("7alt",  new int[]{0, 4, 6, 10, 13, 15}), // b5, b9, #9
    DOM_NINTH_SHARP_ELEVEN   ("9#11",  new int[]{0, 4, 7, 10, 14, 18}),
    DOM_THIRTEENTH_FLAT_NINE ("13b9",  new int[]{0, 4, 7, 10, 13, 21}),
    DOM_THIRTEENTH_SHARP_ELEVEN("13#11", new int[]{0, 4, 7, 10, 14, 18, 21}),

    MAJOR_NINTH    ("maj9",  new int[]{0, 4, 7, 11, 14}),
    MINOR_NINTH    ("m9",    new int[]{0, 3, 7, 10, 14}),
    DOMINANT_NINTH ("9",     new int[]{0, 4, 7, 10, 14}),
    
    // Extended major chords
    MAJOR_ELEVENTH       ("maj11",  new int[]{0, 4, 7, 11, 14, 17}),
    MAJOR_THIRTEENTH     ("maj13",  new int[]{0, 4, 7, 11, 14, 21}),
    MAJOR_NINTH_SHARP_ELEVEN("maj9#11", new int[]{0, 4, 7, 11, 14, 18}),
    
    // Extended minor chords
    MINOR_ELEVENTH     ("m11", new int[]{0, 3, 7, 10, 14, 17}),
    MINOR_THIRTEENTH   ("m13", new int[]{0, 3, 7, 10, 14, 21}),
    MINOR_MAJOR_NINTH  ("mMaj9", new int[]{0, 3, 7, 11, 14}),

    DOMINANT_ELEVENTH  ("11",  new int[]{0, 4, 7, 10, 14, 17}),
    DOMINANT_THIRTEENTH("13",  new int[]{0, 4, 7, 10, 14, 21}),

    SUS2 ("sus2", new int[]{0, 2, 7}),
    SUS4 ("sus4", new int[]{0, 5, 7}),
    DOM_SUS4_SEVENTH("7sus4", new int[]{0, 5, 7, 10});

    private final String symbol;
    private final int[] intervals;

    ChordQuality(String symbol, int[] intervals) {
        this.symbol    = symbol;
        this.intervals = intervals.clone();
    }

    public String getSymbol() { return symbol; }
    public int[] getIntervals() { return intervals.clone(); }
    public int size() { return intervals.length; }
}
