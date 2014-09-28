package org.darkmentat.GuitarScalesBoxes.Model;

import android.graphics.Point;
import org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.FretBoard;

public class GuitarModel extends FretBoard
{
    public Scale Scale;
    public Box Box;
    public GuitarSetting Setting;

    public GuitarModel(GuitarSetting setting, int fretCount) {
        super(setting.StringCount, fretCount);

        setSetting(setting);
    }

    public void setScale(Scale scale){
        Scale = scale;

        for (int x = 0; x < FretCount; x++)
            for(int y = 0; y < StringCount; y++)
            {
                Tab[x][y].Quality = NoteQuality.OnBoard;

                if(scale.isNoteOnScale(((NoteModel) Tab[x][y])))
                    Tab[x][y].Quality = NoteQuality.OnScale;

                if(scale.isNoteScaleTonic(((NoteModel) Tab[x][y])))
                    Tab[x][y].Quality = NoteQuality.OnTonic;
            }
        setChanged();
        notifyObservers();
    }
    public void setSetting(GuitarSetting setting){
        Setting = setting;

        StringCount = setting.StringCount;
        Tab = new NoteModel[FretCount][StringCount];

        System.arraycopy(setting.StartNotes, 0, Tab[0], 0, setting.StartNotes.length);

        for (int x = 1; x < FretCount; x++)
            for(int y = 0; y < setting.StartNotes.length; y++)
                Tab[x][y] = ((NoteModel)Tab[x-1][y]).getNext();

        if(Scale != null)
            setScale(Scale);

        setChanged();
        notifyObservers();
    }
    public void setBox(Box box){
        Box = box;

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

            setChanged();
        }
        else
            setScale(Scale);

        notifyObservers();
    }
    public void setBox(Point startPoint, int maxWidth){
        setBox(new Box(this, Scale, startPoint, maxWidth));
    }
}
