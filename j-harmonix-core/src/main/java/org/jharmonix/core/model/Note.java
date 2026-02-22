package org.jharmonix.core.model;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.EnumMap;

/**
 * Immutable representation of a pitch class (0 = C … 11 = B).
 *
 * <p>Internally stored as a value in [0, 11] so transposition and interval
 * arithmetic are simple modular operations. Two enharmonic equivalents that
 * share the same pitch class (e.g. C# and Db) are considered equal.
 */
public final class Note {

    /** All accepted note names mapped to their pitch-class value. */
    private static final Map<String, Integer> NAME_TO_VALUE;

    static {
        Map<String, Integer> m = new java.util.HashMap<>();
        m.put("C",   0);
        m.put("B#",  0);
        m.put("C#",  1);
        m.put("DB",  1);
        m.put("D",   2);
        m.put("D#",  3);
        m.put("EB",  3);
        m.put("E",   4);
        m.put("FB",  4);
        m.put("E#",  5);
        m.put("F",   5);
        m.put("F#",  6);
        m.put("GB",  6);
        m.put("G",   7);
        m.put("G#",  8);
        m.put("AB",  8);
        m.put("A",   9);
        m.put("A#",  10);
        m.put("BB",  10);
        m.put("B",   11);
        m.put("CB",  11);
        NAME_TO_VALUE = Map.copyOf(m);
    }

    /** Sharp names used when displaying notes (index = pitch class value). */
    private static final String[] SHARP_NAMES =
        {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    /** Flat names used as alternate display when {@code preferFlat} is true. */
    private static final String[] FLAT_NAMES =
        {"C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"};

    private final int value; // pitch class 0-11

    // -------------------------------------------------------------------------
    // Construction
    // -------------------------------------------------------------------------

    /**
     * Creates a Note from a raw pitch-class value.
     * The value is normalised to [0, 11] automatically.
     *
     * @param value any integer; will be reduced mod 12
     */
    public Note(int value) {
        this.value = Math.floorMod(value, 12);
    }

    /**
     * Parses a note from its common name (case-insensitive).
     * Accepts sharps ({@code C#}), flats ({@code Db / DB}), and naturals ({@code C}).
     *
     * @param name the note name, e.g. {@code "F#"}, {@code "Bb"}, {@code "G"}
     * @return the corresponding {@code Note}
     * @throws IllegalArgumentException when the name is unrecognised
     */
    public static Note fromName(String name) {
        Objects.requireNonNull(name, "Note name must not be null");
        Integer v = NAME_TO_VALUE.get(name.trim().toUpperCase(Locale.ROOT));
        if (v == null) {
            throw new IllegalArgumentException(
                "Unrecognised note name: \"" + name + "\". " +
                "Accepted values: C, C#/Db, D, D#/Eb, E, F, F#/Gb, G, G#/Ab, A, A#/Bb, B");
        }
        return new Note(v);
    }

    // -------------------------------------------------------------------------
    // Accessors
    // -------------------------------------------------------------------------

    /** Returns the pitch-class value in [0, 11], where 0 = C and 11 = B. */
    public int getValue() { return value; }

    // -------------------------------------------------------------------------
    // Operations
    // -------------------------------------------------------------------------

    /**
     * Returns a new Note transposed by the given number of semitones.
     * Negative values transpose downward; wrapping is handled automatically.
     *
     * @param semitones interval in semitones (can be negative)
     * @return transposed note
     */
    public Note transpose(int semitones) {
        return new Note(value + semitones);
    }

    /**
     * Calculates the ascending interval in semitones from this note to {@code other}.
     *
     * @param other the target note
     * @return integer in [0, 11]
     */
    public int intervalTo(Note other) {
        Objects.requireNonNull(other, "Target note must not be null");
        return Math.floorMod(other.value - this.value, 12);
    }

    // -------------------------------------------------------------------------
    // Equality & display
    // -------------------------------------------------------------------------

    /**
     * Returns the sharp-notation name (e.g. {@code "C#"}, {@code "D"}).
     * Use {@link #toFlatString()} for the flat-notation equivalent.
     */
    @Override
    public String toString() {
        return SHARP_NAMES[value];
    }

    /** Returns the flat-notation name (e.g. {@code "Db"}, {@code "Eb"}). */
    public String toFlatString() {
        return FLAT_NAMES[value];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note other)) return false;
        return value == other.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
