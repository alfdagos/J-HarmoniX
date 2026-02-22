package org.jharmonix.core.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Immutable representation of a musical scale: a tonic {@link Note} combined
 * with a {@link ScaleType} that defines the interval pattern.
 *
 * <p>The scale provides the foundation for harmonic analysis:
 * <ul>
 *   <li>It resolves the ordered list of pitch classes in the scale.</li>
 *   <li>It derives diatonic chord qualities for each scale degree, enabling
 *       the harmony engine to build correct ii-V-I and other progressions.</li>
 *   <li>It checks whether a given note or chord belongs to the scale.</li>
 * </ul>
 *
 * <h3>Diatonic chords</h3>
 * The seven diatonic chords are built by stacking thirds (every other scale
 * degree). For simplicity J-Harmonix uses <em>seventh chords</em> as the
 * default voicing, consistent with jazz practice.
 */
public final class Scale {

    // Diatonic seventh-chord qualities for a major scale (degrees I-VII)
    private static final ChordQuality[] MAJOR_DIATONIC_7THS = {
        ChordQuality.MAJOR_SEVENTH,      // I   – Imaj7
        ChordQuality.MINOR_SEVENTH,      // II  – IIm7
        ChordQuality.MINOR_SEVENTH,      // III – IIIm7
        ChordQuality.MAJOR_SEVENTH,      // IV  – IVmaj7
        ChordQuality.DOMINANT_SEVENTH,   // V   – V7
        ChordQuality.MINOR_SEVENTH,      // VI  – VIm7
        ChordQuality.HALF_DIMINISHED     // VII – VIIm7b5
    };

    // Diatonic seventh-chord qualities for a natural minor scale
    private static final ChordQuality[] NATURAL_MINOR_DIATONIC_7THS = {
        ChordQuality.MINOR_SEVENTH,      // I   – Im7
        ChordQuality.HALF_DIMINISHED,    // II  – IIm7b5
        ChordQuality.MAJOR_SEVENTH,      // III – bIIImaj7
        ChordQuality.MINOR_SEVENTH,      // IV  – IVm7
        ChordQuality.MINOR_SEVENTH,      // V   – Vm7
        ChordQuality.MAJOR_SEVENTH,      // VI  – bVImaj7
        ChordQuality.DOMINANT_SEVENTH    // VII – bVII7
    };

    // Diatonic seventh-chord qualities for a harmonic minor scale
    private static final ChordQuality[] HARMONIC_MINOR_DIATONIC_7THS = {
        ChordQuality.MINOR_MAJOR_SEVENTH, // I   – ImMaj7
        ChordQuality.HALF_DIMINISHED,     // II  – IIm7b5
        ChordQuality.AUG_TRIAD,           // III – bIIIaug  (triad; no standard 7th)
        ChordQuality.MINOR_SEVENTH,       // IV  – IVm7
        ChordQuality.DOMINANT_SEVENTH,    // V   – V7  (raised 7th produces leading tone)
        ChordQuality.MAJOR_SEVENTH,       // VI  – bVImaj7
        ChordQuality.DIMINISHED_SEVENTH   // VII – VIIdim7
    };

    private final Note tonic;
    private final ScaleType type;
    private final List<Note> notes; // ordered pitch classes of one octave

    // -------------------------------------------------------------------------
    // Construction
    // -------------------------------------------------------------------------

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
        // Remove the octave duplicate if the steps sum to 12
        if (!list.isEmpty() && list.getLast().equals(tonic)) {
            list.removeLast();
        }
        return Collections.unmodifiableList(list);
    }

    // -------------------------------------------------------------------------
    // Accessors
    // -------------------------------------------------------------------------

    public Note getTonic() { return tonic; }
    public ScaleType getType() { return type; }

    /**
     * Returns the ordered list of pitch classes spanning one octave (no duplicate
     * at the octave boundary).
     */
    public List<Note> getNotes() { return notes; }

    /** Returns the number of notes in the scale (e.g. 7 for heptatonic scales). */
    public int size() { return notes.size(); }

    // -------------------------------------------------------------------------
    // Harmonic queries
    // -------------------------------------------------------------------------

    /**
     * Returns {@code true} if the given note is a member of this scale.
     *
     * @param note the note to test
     */
    public boolean contains(Note note) {
        Objects.requireNonNull(note, "Note must not be null");
        return notes.contains(note);
    }

    /**
     * Returns the 0-based degree index of the note in this scale, or {@code -1}
     * if the note is not diatonic.
     *
     * @param note the note to locate
     */
    public int degreeOf(Note note) {
        Objects.requireNonNull(note, "Note must not be null");
        return notes.indexOf(note);
    }

    /**
     * Builds the diatonic seventh chord rooted on the given 0-based scale degree.
     *
     * <p>Degree 0 = tonic, degree 1 = supertonic, …, degree 6 = leading tone.
     * Only {@link ScaleType#MAJOR}, {@link ScaleType#NATURAL_MINOR}, and
     * {@link ScaleType#HARMONIC_MINOR} have full diatonic-chord tables; for other
     * types a best-effort generic stacking is used.
     *
     * @param degree 0-based scale degree
     * @return the diatonic seventh chord
     * @throws IllegalArgumentException if degree is out of range
     */
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
            // For other scale types fall back to a generic interval stacking
            default             -> genericQuality(degree);
        };
    }

    /**
     * Generic fallback: stacks thirds from the scale to determine major/minor/dim quality.
     */
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
        return ChordQuality.MAJOR_SEVENTH; // safe fallback
    }

    // -------------------------------------------------------------------------
    // Equality & display
    // -------------------------------------------------------------------------

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
