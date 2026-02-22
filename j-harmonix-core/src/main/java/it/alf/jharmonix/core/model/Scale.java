package it.alf.jharmonix.core.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Scale {

    private static final ChordQuality[] MAJOR_DIATONIC_7THS = {
        ChordQuality.MAJOR_SEVENTH,
        ChordQuality.MINOR_SEVENTH,
        ChordQuality.MINOR_SEVENTH,
        ChordQuality.MAJOR_SEVENTH,
        ChordQuality.DOMINANT_SEVENTH,
        ChordQuality.MINOR_SEVENTH,
        ChordQuality.HALF_DIMINISHED
    };

    private static final ChordQuality[] NATURAL_MINOR_DIATONIC_7THS = {
        ChordQuality.MINOR_SEVENTH,
        ChordQuality.HALF_DIMINISHED,
        ChordQuality.MAJOR_SEVENTH,
        ChordQuality.MINOR_SEVENTH,
        ChordQuality.MINOR_SEVENTH,
        ChordQuality.MAJOR_SEVENTH,
        ChordQuality.DOMINANT_SEVENTH
    };

    private static final ChordQuality[] HARMONIC_MINOR_DIATONIC_7THS = {
        ChordQuality.MINOR_MAJOR_SEVENTH,
        ChordQuality.HALF_DIMINISHED,
        ChordQuality.AUG_TRIAD,
        ChordQuality.MINOR_SEVENTH,
        ChordQuality.DOMINANT_SEVENTH,
        ChordQuality.MAJOR_SEVENTH,
        ChordQuality.DIMINISHED_SEVENTH
    };

    private final Note tonic;
    private final ScaleType type;
    private final List<Note> notes;

    private Scale(Note tonic, ScaleType type) {
        this.tonic = Objects.requireNonNull(tonic, "Tonic must not be null");
        this.type  = Objects.requireNonNull(type,  "ScaleType must not be null");
        this.notes = buildNotes(tonic, type);
    }

    public static Scale of(Note tonic, ScaleType type) {
        return new Scale(tonic, type);
    }

    private static List<Note> buildNotes(Note tonic, ScaleType type) {
        int[] steps = type.getSteps();
        List<Note> list = new ArrayList<>(steps.length + 1);
        list.add(tonic);
        int current = tonic.getValue();
        for (int step : steps) {
            current += step;
            list.add(new Note(current));
        }
        if (!list.isEmpty() && list.getLast().equals(tonic)) {
            list.removeLast();
        }
        return Collections.unmodifiableList(list);
    }

    public Note getTonic() { return tonic; }
    public ScaleType getType() { return type; }
    public List<Note> getNotes() { return notes; }
    public int size() { return notes.size(); }

    public boolean contains(Note note) {
        Objects.requireNonNull(note, "Note must not be null");
        return notes.contains(note);
    }

    public int degreeOf(Note note) {
        Objects.requireNonNull(note, "Note must not be null");
        return notes.indexOf(note);
    }

    public Chord diatonicChord(int degree) {
        if (degree < 0 || degree >= notes.size()) {
            throw new IllegalArgumentException(
                "Degree " + degree + " is out of range [0, " + (notes.size() - 1) + "]");
        }
        Note root = notes.get(degree);
        ChordQuality quality = resolveQuality(degree);
        return Chord.of(root, quality);
    }

    private ChordQuality resolveQuality(int degree) {
        return switch (type) {
            case MAJOR          -> MAJOR_DIATONIC_7THS[degree % 7];
            case NATURAL_MINOR  -> NATURAL_MINOR_DIATONIC_7THS[degree % 7];
            case HARMONIC_MINOR -> HARMONIC_MINOR_DIATONIC_7THS[degree % 7];
            default             -> genericQuality(degree);
        };
    }

    private ChordQuality genericQuality(int degree) {
        int size = notes.size();
        Note r   = notes.get(degree % size);
        Note t   = notes.get((degree + 2) % size);
        Note f   = notes.get((degree + 4) % size);
        int  i3  = r.intervalTo(t);
        int  i5  = r.intervalTo(f);
        if (i3 == 4 && i5 == 7)  return ChordQuality.MAJOR_SEVENTH;
        if (i3 == 3 && i5 == 7)  return ChordQuality.MINOR_SEVENTH;
        if (i3 == 3 && i5 == 6)  return ChordQuality.HALF_DIMINISHED;
        if (i3 == 4 && i5 == 10) return ChordQuality.DOMINANT_SEVENTH;
        return ChordQuality.MAJOR_SEVENTH;
    }

    @Override
    public String toString() {
        return tonic + " " + type.getDisplayName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Scale other)) return false;
        return tonic.equals(other.tonic) && type == other.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tonic, type);
    }
}
