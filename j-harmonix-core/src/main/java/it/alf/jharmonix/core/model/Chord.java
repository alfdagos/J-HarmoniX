package it.alf.jharmonix.core.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Chord {

    private final Note root;
    private final ChordQuality quality;

    private Chord(Note root, ChordQuality quality) {
        this.root    = Objects.requireNonNull(root,    "Root note must not be null");
        this.quality = Objects.requireNonNull(quality, "Chord quality must not be null");
    }

    public static Chord of(Note root, ChordQuality quality) {
        return new Chord(root, quality);
    }

    public Note getRoot() { return root; }

    public ChordQuality getQuality() { return quality; }

    public List<Note> notes() {
        int[] intervals = quality.getIntervals();
        List<Note> out = new ArrayList<>(intervals.length);
        for (int i : intervals) out.add(root.transpose(i));
        return Collections.unmodifiableList(out);
    }

    public Chord tritoneSubstitution() {
        return new Chord(root.transpose(6), quality);
    }

    public Chord transpose(int semitones) {
        return new Chord(root.transpose(semitones), quality);
    }

    @Override
    public String toString() {
        return root.toString() + quality.getSymbol();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chord other)) return false;
        return root.equals(other.root) && quality == other.quality;
    }

    @Override
    public int hashCode() {
        return Objects.hash(root, quality);
    }
}
