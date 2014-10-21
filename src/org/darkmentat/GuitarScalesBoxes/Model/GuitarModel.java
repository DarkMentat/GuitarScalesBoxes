package org.darkmentat.GuitarScalesBoxes.Model;

import android.graphics.Point;
import org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.FretBoard;

public class GuitarModel extends FretBoard
{
    public Scale Scale;
    public Box Box;
    public GuitarSetting Setting;

    private Point mSelectedNote;
    private NoteQuality mPreviousQualityOfSelectedNote;

    public GuitarModel(GuitarSetting setting, int fretCount) {
        super(setting.StringCount, fretCount);

        setSetting(setting);
    }

    public void setScale(Scale scale){
        if(Scale != scale)
            setChanged();
        Scale = scale;

        if(scale != null)
        {
            for (int x = 0; x < FretCount; x++)
                for (int y = 0; y < StringCount; y++)
                {
                    Tab[x][y].Quality = NoteQuality.OnBoard;

                    if (scale.isNoteOnScale(((NoteModel) Tab[x][y])))
                        Tab[x][y].Quality = NoteQuality.OnScale;

                    if (scale.isNoteScaleTonic(((NoteModel) Tab[x][y])))
                        Tab[x][y].Quality = NoteQuality.OnTonic;
                }
        }
        else
        {
            for (int x = 0; x < FretCount; x++)
                for (int y = 0; y < StringCount; y++)
                    Tab[x][y].Quality = NoteQuality.OnBoard;
        }

        notifyObservers();
    }
    public void setSetting(GuitarSetting setting){
        if(Setting != setting)
            setChanged();
        Setting = setting;

        StringCount = setting.StringCount;
        Tab = new NoteModel[FretCount][StringCount];

        System.arraycopy(setting.StartNotes, 0, Tab[0], 0, setting.StartNotes.length);

        for (int x = 1; x < FretCount; x++)
            for(int y = 0; y < setting.StartNotes.length; y++)
                Tab[x][y] = ((NoteModel)Tab[x-1][y]).getNext();

        if(Scale != null)
            setScale(Scale);

        notifyObservers();
    }
    public void setBox(Box box){
        Box = box;

        setScale(Scale);
        if(box != null)
        {
            for (Point p : box.Points)
            {
                if(Tab[p.x][p.y].Quality == NoteQuality.OnTonic)
                   Tab[p.x][p.y].Quality =  NoteQuality.OnTonicOnBox;
                else
                if(Tab[p.x][p.y].Quality == NoteQuality.OnScale)
                   Tab[p.x][p.y].Quality =  NoteQuality.OnScaleOnBox;
            }
        }

        setChanged();
        notifyObservers();
    }
    public void setBox(int startFret, int endFret){
        setBox(new Box(this, Scale, startFret, endFret));
    }

    public void selectNote(Point position){
        unSelectNote();
        mSelectedNote = position;
        mPreviousQualityOfSelectedNote = Tab[position.x][position.y].Quality;
        Tab[position.x][position.y].Quality = NoteQuality.Selected;
        setChanged();
        notifyObservers();
    }
    public void unSelectNote(){
        if(mPreviousQualityOfSelectedNote == null) return;
        Tab[mSelectedNote.x][mSelectedNote.y].Quality = mPreviousQualityOfSelectedNote;
        setChanged();
        notifyObservers();
    }
}
