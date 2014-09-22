package org.darkmentat.GuitarScalesBoxes.Model;

import org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.FretBoard;

public class GuitarModel extends FretBoard
{
    private Scale mScale;

    public GuitarModel(GuitarSetting setting, int fretCount) {
        super(setting.StringCount, fretCount);

        Tab = new NoteModel[fretCount][setting.StringCount];

        System.arraycopy(setting.StartNotes, 0, Tab[0], 0, setting.StartNotes.length);

        for (int x = 1; x < fretCount; x++)
            for(int y = 0; y < setting.StartNotes.length; y++)
                Tab[x][y] = ((NoteModel)Tab[x-1][y]).getNext();
    }

    public void setScale(Scale scale){
        mScale = scale;

        for (int x = 0; x < FretCount; x++)
            for(int y = 0; y < StringCount; y++)
            {
                if(scale.isNoteOnScale(((NoteModel) Tab[x][y])))
                    Tab[x][y].Quality = NoteQuality.OnScale;

                if(scale.isNoteScaleTonic(((NoteModel) Tab[x][y])))
                    Tab[x][y].Quality = NoteQuality.OnTonic;
            }
        setChanged();
        notifyObservers();
    }
}
