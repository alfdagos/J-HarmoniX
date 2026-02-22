package org.jharmonix.core.model;

/**
 * Enumerates the scale types supported by J-Harmonix, each defined by its
 * ascending interval pattern (consecutive semitone steps).
 *
 * <p>The step pattern starts from the tonic and sums to 12 (one octave).
 *
 * <h3>Jazz relevance</h3>
 * <ul>
 *   <li>{@link #MAJOR} and {@link #NATURAL_MINOR} cover most classic progressions.</li>
 *   <li>{@link #HARMONIC_MINOR} provides the raised 7th that generates the V7b9
 *       chord typical of minor cadences.</li>
 *   <li>{@link #MELODIC_MINOR} (ascending form) is the basis for many modern
 *       jazz scales (Lydian Dominant = Melodic Minor from 4th degree).</li>
 *   <li>{@link #BLUES} is included for modal / blues-jazz contexts.</li>
 * </ul>
 */
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
    private final int[] steps; // consecutive semitone steps

    ScaleType(String displayName, int[] steps) {
        this.displayName = displayName;
        this.steps = steps.clone();
    }

    public String getDisplayName() { return displayName; }

    /** Returns the ascending step pattern (consecutive semitone intervals). */
    public int[] getSteps() { return steps.clone(); }
}
