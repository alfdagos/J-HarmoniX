package it.alf.jharmonix.core.model;

import java.util.Objects;

public final class KeySignature {

    private final Scale scale;

    private KeySignature(Scale scale) {
        this.scale = Objects.requireNonNull(scale, "Scale must not be null");
    }

    public static KeySignature of(Note tonic, ScaleType type) {
        return new KeySignature(Scale.of(tonic, type));
    }

    public static KeySignature of(String tonicName, ScaleType type) {
        return of(Note.fromName(tonicName), type);
    }

    public static KeySignature major(String tonicName) {
        return of(tonicName, ScaleType.MAJOR);
    }

    public static KeySignature naturalMinor(String tonicName) {
        return of(tonicName, ScaleType.NATURAL_MINOR);
    }

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

    public Chord diatonicChord(int degree) {
        return scale.diatonicChord(degree);
    }

    public KeySignature relativeMinor() {
        if (!isMajor()) throw new IllegalStateException("relativeMinor() is only valid for major keys");
        Note relativeTonic = scale.getNotes().get(5);
        return KeySignature.of(relativeTonic, ScaleType.NATURAL_MINOR);
    }

    public KeySignature parallelMinor() {
        return KeySignature.of(scale.getTonic(), ScaleType.NATURAL_MINOR);
    }

    public KeySignature dominantKey() {
        Note dominantTonic = scale.getNotes().get(4);
        return KeySignature.of(dominantTonic, scale.getType());
    }

    public KeySignature subdominantKey() {
        Note subdominantTonic = scale.getNotes().get(3);
        return KeySignature.of(subdominantTonic, scale.getType());
    }

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
