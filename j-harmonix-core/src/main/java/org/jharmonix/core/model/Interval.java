package org.jharmonix.core.model;

import java.util.Objects;

/**
 * Immutable musical interval expressed in semitones with a human-readable name.
 *
 * <p>The class ships a full set of standard constants covering unisons through
 * major/minor thirteenths – the extensions (9, 11, 13) used in jazz voicings are
 * deliberately included. Augmented and diminished intervals used for jazz
 * alterations (e.g. b5, #11) are also provided.
 */
public final class Interval {

    private final int semitones;
    private final String name;

    private Interval(int semitones, String name) {
        if (semitones < 0) throw new IllegalArgumentException("Semitones must be >= 0; got " + semitones);
        this.semitones = semitones;
        this.name = Objects.requireNonNull(name, "Interval name must not be null");
    }

    /** Factory method for custom intervals not covered by the constants. */
    public static Interval of(int semitones, String name) {
        return new Interval(semitones, name);
    }

    // -------------------------------------------------------------------------
    // Accessors
    // -------------------------------------------------------------------------

    /** Number of semitones spanning this interval. */
    public int getSemitones() { return semitones; }

    /** Standard abbreviated name (e.g. {@code "M3"}, {@code "m7"}, {@code "P5"}). */
    public String getName() { return name; }

    // -------------------------------------------------------------------------
    // Equality & display
    // -------------------------------------------------------------------------

    @Override
    public String toString() { return name + "(" + semitones + "st)"; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Interval other)) return false;
        return semitones == other.semitones && name.equals(other.name);
    }

    @Override
    public int hashCode() { return Objects.hash(semitones, name); }

    // =========================================================================
    // Standard constants
    // =========================================================================

    // --- Unisons / octaves ---
    public static final Interval UNISON         = new Interval(0,  "P1");
    public static final Interval OCTAVE         = new Interval(12, "P8");

    // --- Seconds ---
    public static final Interval MINOR_SECOND   = new Interval(1,  "m2");
    public static final Interval MAJOR_SECOND   = new Interval(2,  "M2");

    // --- Thirds ---
    public static final Interval MINOR_THIRD    = new Interval(3,  "m3");
    public static final Interval MAJOR_THIRD    = new Interval(4,  "M3");

    // --- Fourths ---
    public static final Interval PERFECT_FOURTH = new Interval(5,  "P4");
    public static final Interval AUG_FOURTH     = new Interval(6,  "A4");   // tritone / #11

    // --- Fifths ---
    public static final Interval DIM_FIFTH      = new Interval(6,  "d5");   // tritone / b5
    public static final Interval PERFECT_FIFTH  = new Interval(7,  "P5");
    public static final Interval AUG_FIFTH      = new Interval(8,  "A5");   // #5

    // --- Sixths ---
    public static final Interval MINOR_SIXTH    = new Interval(8,  "m6");   // enharmonic #5
    public static final Interval MAJOR_SIXTH    = new Interval(9,  "M6");

    // --- Sevenths ---
    public static final Interval DIM_SEVENTH    = new Interval(9,  "d7");   // enharmonic M6
    public static final Interval MINOR_SEVENTH  = new Interval(10, "m7");
    public static final Interval MAJOR_SEVENTH  = new Interval(11, "M7");

    // --- Extended / jazz intervals (compound) ---
    public static final Interval MINOR_NINTH    = new Interval(13, "m9");
    public static final Interval MAJOR_NINTH    = new Interval(14, "M9");
    public static final Interval AUG_NINTH      = new Interval(15, "A9");   // #9
    public static final Interval PERFECT_ELEVENTH = new Interval(17, "P11");
    public static final Interval AUG_ELEVENTH   = new Interval(18, "A11");  // #11 / Lydian
    public static final Interval MINOR_THIRTEENTH = new Interval(20, "m13");
    public static final Interval MAJOR_THIRTEENTH = new Interval(21, "M13");
}
