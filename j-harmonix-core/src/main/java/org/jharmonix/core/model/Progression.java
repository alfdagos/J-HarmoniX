package org.jharmonix.core.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * An ordered, immutable sequence of {@link Chord} objects representing a
 * harmonic progression.
 *
 * <p>A {@code Progression} may represent anything from a two-chord ii-V to an
 * entire song form. It is the primary output produced by the harmony engine.
 *
 * <h3>Usage</h3>
 * <pre>
 *   Progression iivi = Progression.builder()
 *       .add(Chord.of(Note.fromName("D"), ChordQuality.MINOR_SEVENTH))   // Dm7
 *       .add(Chord.of(Note.fromName("G"), ChordQuality.DOMINANT_SEVENTH)) // G7
 *       .add(Chord.of(Note.fromName("C"), ChordQuality.MAJOR_SEVENTH))   // Cmaj7
 *       .build();
 *
 *   System.out.println(iivi); // | Dm7 | G7 | Cmaj7 |
 * </pre>
 */
public final class Progression {

    private final List<Chord> chords;
    private final String sectionLabel; // e.g. "Verse A", "Bridge", "" for unnamed

    private Progression(List<Chord> chords, String sectionLabel) {
        this.chords       = Collections.unmodifiableList(new ArrayList<>(chords));
        this.sectionLabel = sectionLabel == null ? "" : sectionLabel;
    }

    // -------------------------------------------------------------------------
    // Factory / Builder
    // -------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------
    // Accessors
    // -------------------------------------------------------------------------

    public List<Chord> getChords() { return chords; }
    public int size() { return chords.size(); }
    public String getSectionLabel() { return sectionLabel; }

    /**
     * Returns the chord at the specified 0-based index.
     *
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public Chord get(int index) { return chords.get(index); }

    // -------------------------------------------------------------------------
    // Transformations
    // -------------------------------------------------------------------------

    /**
     * Transposes every chord in this progression by the given number of semitones.
     *
     * @param semitones number of semitones (positive = up, negative = down)
     * @return a new transposed {@code Progression} with the same label
     */
    public Progression transpose(int semitones) {
        Builder b = builder().label(sectionLabel);
        for (Chord c : chords) b.add(c.transpose(semitones));
        return b.build();
    }

    // -------------------------------------------------------------------------
    // Display
    // -------------------------------------------------------------------------

    /**
     * Returns a lead-sheet bar-line representation, e.g.:
     * <pre>| Dm7 | G7 | Cmaj7 |</pre>
     */
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
