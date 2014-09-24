package org.darkmentat.GuitarScalesBoxes.Model;

import static org.darkmentat.GuitarScalesBoxes.Model.NoteModel.*;

public final class GuitarSetting
{
    static{
        Default = new GuitarSetting(new NoteModel[]{
                new NoteModel(NoteValue.E, 4),
                new NoteModel(NoteValue.H, 3),
                new NoteModel(NoteValue.G, 3),
                new NoteModel(NoteValue.D, 3),
                new NoteModel(NoteValue.A, 2),
                new NoteModel(NoteValue.E, 2),
        }, "Default");
        HalfStepDown = new GuitarSetting(new NoteModel[]{
                new NoteModel(NoteValue.Dd, 4),
                new NoteModel(NoteValue.Ad, 3),
                new NoteModel(NoteValue.Fd, 3),
                new NoteModel(NoteValue.Cd, 3),
                new NoteModel(NoteValue.Gd, 2),
                new NoteModel(NoteValue.Dd, 2),
        }, "Half Step Down");
        OneStepDown = new GuitarSetting(new NoteModel[]{
                new NoteModel(NoteValue.D, 4),
                new NoteModel(NoteValue.A, 3),
                new NoteModel(NoteValue.F, 3),
                new NoteModel(NoteValue.C, 3),
                new NoteModel(NoteValue.G, 2),
                new NoteModel(NoteValue.D, 2),
        }, "One Step Down");
        DropD = new GuitarSetting(new NoteModel[]{
                new NoteModel(NoteValue.E, 4),
                new NoteModel(NoteValue.H, 3),
                new NoteModel(NoteValue.G, 3),
                new NoteModel(NoteValue.D, 3),
                new NoteModel(NoteValue.A, 2),
                new NoteModel(NoteValue.D, 2),
        }, "Drop D");
        DropC = new GuitarSetting(new NoteModel[]{
                new NoteModel(NoteValue.D, 4),
                new NoteModel(NoteValue.A, 3),
                new NoteModel(NoteValue.F, 3),
                new NoteModel(NoteValue.C, 3),
                new NoteModel(NoteValue.G, 2),
                new NoteModel(NoteValue.C, 2),
        }, "Drop C");
    }

    public static final GuitarSetting Default;
    public static final GuitarSetting HalfStepDown;
    public static final GuitarSetting OneStepDown;
    public static final GuitarSetting DropD;
    public static final GuitarSetting DropC;

    public static GuitarSetting[] Settings = new GuitarSetting[]{Default, HalfStepDown, OneStepDown, DropD, DropC};

    public final int StringCount;
    public final NoteModel[] StartNotes;
    private final String mName;

    public GuitarSetting(NoteModel[] notes) {
        this(notes, "");
    }
    public GuitarSetting(NoteModel[] notes, String name) {
        mName = name;
        StringCount = notes.length;
        StartNotes = notes;
    }

    @Override public String toString() {
        return mName;
    }
}
