package it.alf.jharmonix.core.model;

import java.util.Objects;

public final class Interval {

    private final int semitones;
    private final String name;

    private Interval(int semitones, String name) {
        if (semitones < 0) throw new IllegalArgumentException("Semitones must be >= 0; got " + semitones);
        this.semitones = semitones;
        this.name = Objects.requireNonNull(name, "Interval name must not be null");
    }

    public static Interval of(int semitones, String name) {
        return new Interval(semitones, name);
    }

    public int getSemitones() { return semitones; }
    public String getName() { return name; }

    @Override
    public String toString() { return name + "(" + semitones + "st)"; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Interval other)) return false;
        return semitones == other.semitones && name.equals(other.name);
    }

    @Override
    public int hashCode() { return Objects.hash(semitones, name); }

    public static final Interval UNISON         = new Interval(0,  "P1");
    public static final Interval OCTAVE         = new Interval(12, "P8");
    public static final Interval MINOR_SECOND   = new Interval(1,  "m2");
    public static final Interval MAJOR_SECOND   = new Interval(2,  "M2");
    public static final Interval MINOR_THIRD    = new Interval(3,  "m3");
    public static final Interval MAJOR_THIRD    = new Interval(4,  "M3");
    public static final Interval PERFECT_FOURTH = new Interval(5,  "P4");
    public static final Interval AUG_FOURTH     = new Interval(6,  "A4");
    public static final Interval DIM_FIFTH      = new Interval(6,  "d5");
    public static final Interval PERFECT_FIFTH  = new Interval(7,  "P5");
    public static final Interval AUG_FIFTH      = new Interval(8,  "A5");
    public static final Interval MINOR_SIXTH    = new Interval(8,  "m6");
    public static final Interval MAJOR_SIXTH    = new Interval(9,  "M6");
    public static final Interval DIM_SEVENTH    = new Interval(9,  "d7");
    public static final Interval MINOR_SEVENTH  = new Interval(10, "m7");
    public static final Interval MAJOR_SEVENTH  = new Interval(11, "M7");
    public static final Interval MINOR_NINTH    = new Interval(13, "m9");
    public static final Interval MAJOR_NINTH    = new Interval(14, "M9");
    public static final Interval AUG_NINTH      = new Interval(15, "A9");
    public static final Interval PERFECT_ELEVENTH = new Interval(17, "P11");
    public static final Interval AUG_ELEVENTH   = new Interval(18, "A11");
    public static final Interval MINOR_THIRTEENTH = new Interval(20, "m13");
    public static final Interval MAJOR_THIRTEENTH = new Interval(21, "M13");
}
