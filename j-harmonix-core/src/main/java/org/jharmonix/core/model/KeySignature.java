package org.jharmonix.core.model;

import java.util.Objects;

/**
 * Represents a musical key signature (tonic + mode).
 *
 * <p>{@code KeySignature} is a thin wrapper around a {@link Scale} that makes
 * intent explicit and provides convenience helpers used by the harmony engine
 * (e.g. relative key, parallel minor, dominant key).
 *
 * <h3>Examples</h3>
 * <pre>
 *   KeySignature cMajor = KeySignature.of("C", ScaleType.MAJOR);
 *   KeySignature aMinor = cMajor.relativeMinor();   // A Natural Minor
 *   KeySignature gMajor = cMajor.dominantKey();     // G Major
 *
 *   Chord ii = cMajor.diatonicChord(1); // Dm7
 *   Chord V  = cMajor.diatonicChord(4); // G7
 *   Chord I  = cMajor.diatonicChord(0); // Cmaj7
 * </pre>
 */
public final class KeySignature {

    private final Scale scale;

    private KeySignature(Scale scale) {
        this.scale = Objects.requireNonNull(scale, "Scale must not be null");
    }

    // -------------------------------------------------------------------------
    // Factory methods
    // -------------------------------------------------------------------------

    public static KeySignature of(Note tonic, ScaleType type) {
        return new KeySignature(Scale.of(tonic, type));
    }

    public static KeySignature of(String tonicName, ScaleType type) {
        return of(Note.fromName(tonicName), type);
    }

    /** Shorthand for a major key. */
    public static KeySignature major(String tonicName) {
        return of(tonicName, ScaleType.MAJOR);
    }

    /** Shorthand for a natural minor key. */
    public static KeySignature naturalMinor(String tonicName) {
        return of(tonicName, ScaleType.NATURAL_MINOR);
    }

    // -------------------------------------------------------------------------
    // Accessors
    // -------------------------------------------------------------------------

    public Note getTonic() { return scale.getTonic(); }
    public ScaleType getScaleType() { return scale.getType(); }
    public Scale getScale() { return scale; }

    public boolean isMajor() { return scale.getType() == ScaleType.MAJOR; }
    public boolean isMinor() {
        ScaleType t = scale.getType();
        return t == ScaleType.NATURAL_MINOR
            || t == ScaleType.HARMONIC_MINOR
            || t == ScaleType.MELODIC_MINOR;
    }

    // -------------------------------------------------------------------------
    // Diatonic chord convenience
    // -------------------------------------------------------------------------

    /**
     * Returns the diatonic seventh chord for the given 0-based degree.
     * Degree 0 = I, 1 = II, …, 6 = VII.
     */
    public Chord diatonicChord(int degree) {
        return scale.diatonicChord(degree);
    }

    // -------------------------------------------------------------------------
    // Related keys
    // -------------------------------------------------------------------------

    /**
     * Returns the relative minor (for a major key) as a new {@code KeySignature}.
     * The relative minor starts on the 6th degree (index 5) of the major scale.
     *
     * @throws IllegalStateException if this key is not major
     */
    public KeySignature relativeMinor() {
        if (!isMajor()) throw new IllegalStateException("relativeMinor() is only valid for major keys");
        Note relativeTonic = scale.getNotes().get(5); // 6th degree = index 5
        return KeySignature.of(relativeTonic, ScaleType.NATURAL_MINOR);
    }

    /**
     * Returns the parallel (same-tonic) minor of this key.
     * E.g. C Major → C Natural Minor.
     */
    public KeySignature parallelMinor() {
        return KeySignature.of(scale.getTonic(), ScaleType.NATURAL_MINOR);
    }

    /**
     * Returns the key of the dominant (V) of this key.
     * The dominant tonic is the 5th degree (index 4) of the current scale.
     */
    public KeySignature dominantKey() {
        Note dominantTonic = scale.getNotes().get(4); // 5th degree = index 4
        return KeySignature.of(dominantTonic, scale.getType());
    }

    /**
     * Returns the key of the subdominant (IV) of this key.
     * The subdominant tonic is the 4th degree (index 3) of the current scale.
     */
    public KeySignature subdominantKey() {
        Note subdominantTonic = scale.getNotes().get(3); // 4th degree = index 3
        return KeySignature.of(subdominantTonic, scale.getType());
    }

    // -------------------------------------------------------------------------
    // Equality & display
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return scale.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeySignature other)) return false;
        return scale.equals(other.scale);
    }

    @Override
    public int hashCode() {
        return scale.hashCode();
    }
}
