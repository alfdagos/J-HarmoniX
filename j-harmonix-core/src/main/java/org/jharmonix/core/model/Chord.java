package org.jharmonix.core.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Immutable representation of a chord as a root {@link Note} and a {@link ChordQuality}.
 *
 * <p>Rather than storing a fixed list of notes, the chord is defined by its
 * <em>interval structure</em> so that transposition and analysis remain efficient
 * regardless of voicing. The {@link #notes()} method materialises the pitch-class
 * set on demand.
 *
 * <h3>Examples</h3>
 * <pre>
 *   Chord c = Chord.of(Note.fromName("C"), ChordQuality.MAJOR_SEVENTH);   // Cmaj7
 *   Chord g7 = Chord.of(Note.fromName("G"), ChordQuality.DOMINANT_SEVENTH); // G7
 *
 *   // Tritone substitution: dominant a tritone away resolves the same way
 *   Chord sub = g7.tritoneSubstitution(); // Db7
 * </pre>
 */
public final class Chord {

    private final Note root;
    private final ChordQuality quality;

    private Chord(Note root, ChordQuality quality) {
        this.root    = Objects.requireNonNull(root,    "Root note must not be null");
        this.quality = Objects.requireNonNull(quality, "Chord quality must not be null");
    }

    // -------------------------------------------------------------------------
    // Factory methods
    // -------------------------------------------------------------------------

    /**
     * Creates a chord from a root note and a quality.
     *
     * @param root    the fundamental note
     * @param quality the interval structure
     * @return a new {@code Chord}
     */
    public static Chord of(Note root, ChordQuality quality) {
        return new Chord(root, quality);
    }

    // -------------------------------------------------------------------------
    // Accessors
    // -------------------------------------------------------------------------

    /** Returns the root (fundamental) note. */
    public Note getRoot() { return root; }

    /** Returns the chord quality. */
    public ChordQuality getQuality() { return quality; }

    // -------------------------------------------------------------------------
    // Derived data
    // -------------------------------------------------------------------------

    /**
     * Materialises the pitch-class set by applying the quality's interval
     * structure to the root.
     *
     * @return unmodifiable list of notes (first element is always the root)
     */
    public List<Note> notes() {
        int[] intervals = quality.getIntervals();
        List<Note> out = new ArrayList<>(intervals.length);
        for (int i : intervals) out.add(root.transpose(i));
        return Collections.unmodifiableList(out);
    }

    // -------------------------------------------------------------------------
    // Transformations
    // -------------------------------------------------------------------------

    /**
     * Returns the tritone substitution of this chord.
     *
     * <p>A tritone substitute shares the guide tones (3rd and 7th) with the
     * original dominant chord, but the root is displaced by 6 semitones.
     * This technique is central to modern jazz reharmonisation.
     *
     * <p>The quality is preserved (usually {@link ChordQuality#DOMINANT_SEVENTH}).
     *
     * @return a new {@code Chord} with root transposed by 6 semitones
     */
    public Chord tritoneSubstitution() {
        return new Chord(root.transpose(6), quality);
    }

    /**
     * Transposes the entire chord by the given number of semitones.
     *
     * @param semitones interval size (positive = up, negative = down)
     * @return transposed chord with the same quality
     */
    public Chord transpose(int semitones) {
        return new Chord(root.transpose(semitones), quality);
    }

    // -------------------------------------------------------------------------
    // Equality & display
    // -------------------------------------------------------------------------

    /**
     * Returns the standard lead-sheet notation, e.g. {@code "Cmaj7"}, {@code "Dm7b5"}.
     */
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
