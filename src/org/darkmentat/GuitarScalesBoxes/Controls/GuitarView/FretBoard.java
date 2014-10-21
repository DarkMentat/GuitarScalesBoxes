package org.darkmentat.GuitarScalesBoxes.Controls.GuitarView;

import java.util.Observable;

public abstract class FretBoard extends Observable
{
    public static enum NoteQuality
    {
        OnBoard, OnScale, OnTonic, OnScaleOnBox, OnTonicOnBox, Selected
    }
    public static class Note
    {
        public NoteQuality Quality;
        public String DisplayName;

        public Note(NoteQuality quality, String displayName) {
            Quality = quality;
            DisplayName = displayName;
        }
    }

    public int StringCount;
    public int FretCount;
    public Note[][] Tab;

    protected FretBoard(int stringCount, int fretCount) {
        StringCount = stringCount;
        FretCount = fretCount;
    }
}
