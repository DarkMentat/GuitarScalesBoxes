package org.darkmentat.GuitarScalesBoxes.Model;

import org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.FretBoard;

public final class NoteModel extends FretBoard.Note implements Comparable<NoteModel>{
    public enum NoteOctave{
        SubContra,
        Contra,
        Great,
        Small,
        OneLine,
        TwoLine,
        ThreeLine;

        @Override public String toString() {
            return String.valueOf(ordinal());
       }
    }
    public enum NoteValue{
        C, Cd, D, Dd, E, F, Fd, G, Gd, A, Ad, H;

        @Override
        public String toString() {
            return name().replace("d", "#");
        }
    }
    private NoteValue[] mNoteValues = NoteValue.values();

    public final NoteValue Value;
    public final NoteOctave Octave;

    public NoteModel(NoteValue value, int octave) {
        this(FretBoard.NoteQuality.OnBoard, value, octave);
    }
    public NoteModel(FretBoard.NoteQuality quality, NoteValue value, int octave) {
        this(quality, value, NoteOctave.values()[octave]);
    }
    public NoteModel(FretBoard.NoteQuality quality, NoteValue value, NoteOctave octave) {
        super(quality, value.toString());
        Value = value;
        Octave = octave;
    }

    public NoteModel getNext(){
        int octave = Octave.ordinal();
        NoteValue value = Value;
        if(value.ordinal() == mNoteValues.length-1)
        {
            value = NoteValue.C;
            octave++;
        }
        else
            value =  mNoteValues[value.ordinal()+1];

        return new NoteModel(value, octave);
    }

    @Override public int hashCode() {
        int result = Value.hashCode();
        result = 31 * result + Octave.ordinal();
        return result;
    }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        //if (getClass() != o.getClass()) return false;
        //Intellij IDEA bug, it compiles, but throws error
        //Next is a little ugly, but it works =)
        if (((Object)this).getClass() != o.getClass()) return false;

        NoteModel noteModel = (NoteModel) o;

        if (Octave != noteModel.Octave) return false;
        if (Value != noteModel.Value) return false;

        return true;
    }
    @Override public int compareTo(NoteModel another) {
        if( another == null) return 1;

        int octaveCmp = Octave.compareTo(another.Octave);

        if (octaveCmp != 0)
            return Value.compareTo(another.Value);
        return octaveCmp;
    }

    @Override public String toString() {
        return Value.toString()+ Octave.toString();
    }
}
