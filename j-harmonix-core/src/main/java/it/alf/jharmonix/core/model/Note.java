package it.alf.jharmonix.core.model;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public final class Note {

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

    private static final String[] SHARP_NAMES =
        {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    private static final String[] FLAT_NAMES =
        {"C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"};

    private final int value;

    public Note(int value) {
        this.value = Math.floorMod(value, 12);
    }

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

    public int getValue() { return value; }

    public Note transpose(int semitones) {
        return new Note(value + semitones);
    }

    public int intervalTo(Note other) {
        Objects.requireNonNull(other, "Target note must not be null");
        return Math.floorMod(other.value - this.value, 12);
    }

    @Override
    public String toString() {
        return SHARP_NAMES[value];
    }

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
