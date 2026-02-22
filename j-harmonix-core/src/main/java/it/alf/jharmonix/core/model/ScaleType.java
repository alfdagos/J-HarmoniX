package it.alf.jharmonix.core.model;

public enum ScaleType {

    MAJOR           ("Major",            new int[]{2, 2, 1, 2, 2, 2, 1}),
    NATURAL_MINOR   ("Natural Minor",    new int[]{2, 1, 2, 2, 1, 2, 2}),
    HARMONIC_MINOR  ("Harmonic Minor",   new int[]{2, 1, 2, 2, 1, 3, 1}),
    MELODIC_MINOR   ("Melodic Minor",    new int[]{2, 1, 2, 2, 2, 2, 1}),

    DORIAN          ("Dorian",           new int[]{2, 1, 2, 2, 2, 1, 2}),
    PHRYGIAN        ("Phrygian",         new int[]{1, 2, 2, 2, 1, 2, 2}),
    LYDIAN          ("Lydian",           new int[]{2, 2, 2, 1, 2, 2, 1}),
    MIXOLYDIAN      ("Mixolydian",       new int[]{2, 2, 1, 2, 2, 1, 2}),
    LOCRIAN         ("Locrian",          new int[]{1, 2, 2, 1, 2, 2, 2}),

    WHOLE_TONE      ("Whole Tone",       new int[]{2, 2, 2, 2, 2, 2}),
    DIMINISHED_WH   ("Diminished (W-H)", new int[]{2, 1, 2, 1, 2, 1, 2, 1}),
    DIMINISHED_HW   ("Diminished (H-W)", new int[]{1, 2, 1, 2, 1, 2, 1, 2}),

    BLUES           ("Blues",            new int[]{3, 2, 1, 1, 3, 2}),
    PENTATONIC_MAJOR("Major Pentatonic", new int[]{2, 2, 3, 2, 3}),
    PENTATONIC_MINOR("Minor Pentatonic", new int[]{3, 2, 2, 3, 2});

    private final String displayName;
    private final int[] steps;

    ScaleType(String displayName, int[] steps) {
        this.displayName = displayName;
        this.steps = steps.clone();
    }

    public String getDisplayName() { return displayName; }
    public int[] getSteps() { return steps.clone(); }
}
