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
        });
        HalfStepDown = new GuitarSetting(new NoteModel[]{
                new NoteModel(NoteValue.Dd, 4),
                new NoteModel(NoteValue.Ad, 3),
                new NoteModel(NoteValue.Fd, 3),
                new NoteModel(NoteValue.Cd, 3),
                new NoteModel(NoteValue.Gd, 2),
                new NoteModel(NoteValue.Dd, 2),
        });
        OneStepDown = new GuitarSetting(new NoteModel[]{
                new NoteModel(NoteValue.D, 4),
                new NoteModel(NoteValue.A, 3),
                new NoteModel(NoteValue.F, 3),
                new NoteModel(NoteValue.C, 3),
                new NoteModel(NoteValue.G, 2),
                new NoteModel(NoteValue.D, 2),
        });
        DropD = new GuitarSetting(new NoteModel[]{
                new NoteModel(NoteValue.E, 4),
                new NoteModel(NoteValue.H, 3),
                new NoteModel(NoteValue.G, 3),
                new NoteModel(NoteValue.D, 3),
                new NoteModel(NoteValue.A, 2),
                new NoteModel(NoteValue.D, 2),
        });
        DropC = new GuitarSetting(new NoteModel[]{
                new NoteModel(NoteValue.D, 4),
                new NoteModel(NoteValue.A, 3),
                new NoteModel(NoteValue.F, 3),
                new NoteModel(NoteValue.C, 3),
                new NoteModel(NoteValue.G, 2),
                new NoteModel(NoteValue.C, 2),
        });
    }

    public static final GuitarSetting Default;
    public static final GuitarSetting HalfStepDown;
    public static final GuitarSetting OneStepDown;
    public static final GuitarSetting DropD;
    public static final GuitarSetting DropC;

    public final int StringCount;
    public final NoteModel[] StartNotes;
    private GuitarSetting(NoteModel[] notes) {
        StringCount = notes.length;
        StartNotes = notes;
    }
}
