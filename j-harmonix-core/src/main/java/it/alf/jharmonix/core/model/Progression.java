package it.alf.jharmonix.core.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Progression {

    private final List<Chord> chords;
    private final String sectionLabel;

    private Progression(List<Chord> chords, String sectionLabel) {
        this.chords       = Collections.unmodifiableList(new ArrayList<>(chords));
        this.sectionLabel = sectionLabel == null ? "" : sectionLabel;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private final List<Chord> chords = new ArrayList<>();
        private String label = "";

        public Builder add(Chord chord) {
            Objects.requireNonNull(chord, "Chord must not be null");
            chords.add(chord);
            return this;
        }

        public Builder addAll(List<Chord> chords) {
            Objects.requireNonNull(chords, "Chord list must not be null");
            chords.forEach(this::add);
            return this;
        }

        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public Progression build() {
            if (chords.isEmpty()) throw new IllegalStateException("A progression must contain at least one chord");
            return new Progression(chords, label);
        }
    }

    public List<Chord> getChords() { return chords; }
    public int size() { return chords.size(); }
    public String getSectionLabel() { return sectionLabel; }

    public Chord get(int index) { return chords.get(index); }

    public Progression transpose(int semitones) {
        Builder b = builder().label(sectionLabel);
        for (Chord c : chords) b.add(c.transpose(semitones));
        return b.build();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!sectionLabel.isBlank()) sb.append("[").append(sectionLabel).append("]\n");
        sb.append("| ");
        for (Chord c : chords) sb.append(c).append(" | ");
        return sb.toString().stripTrailing();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Progression other)) return false;
        return chords.equals(other.chords) && sectionLabel.equals(other.sectionLabel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chords, sectionLabel);
    }
}
