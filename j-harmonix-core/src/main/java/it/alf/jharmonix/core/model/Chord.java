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

    /**
     * Returns chord inversions. For a chord with N notes:
     * - Index 0: root position (original chord)
     * - Index 1: first inversion (third in bass)
     * - Index 2: second inversion (fifth in bass)
     * - Index 3+: subsequent inversions for extended chords
     * 
     * Each inversion is represented as a new list of notes with the lowest
     * note moved up an octave.
     * 
     * @return list of note lists, one for each inversion (including root position)
     */
    public List<List<Note>> inversions() {
        List<Note> originalNotes = notes();
        int numNotes = originalNotes.size();
        
        List<List<Note>> allInversions = new ArrayList<>(numNotes);
        
        // Root position (original)
        allInversions.add(originalNotes);
        
        // Generate each inversion by moving the bottom note up an octave
        List<Note> currentInversion = new ArrayList<>(originalNotes);
        for (int inv = 1; inv < numNotes; inv++) {
            // Move bottom note up an octave
            Note bottomNote = currentInversion.removeFirst();
            Note raisedNote = bottomNote.transpose(12);
            currentInversion.add(raisedNote);
            
            // Store this inversion
            allInversions.add(new ArrayList<>(currentInversion));
        }
        
        return Collections.unmodifiableList(allInversions);
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
