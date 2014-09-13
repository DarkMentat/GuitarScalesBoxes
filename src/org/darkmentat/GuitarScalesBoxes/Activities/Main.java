package org.darkmentat.GuitarScalesBoxes.Activities;

import android.app.Activity;
import android.os.Bundle;
import org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.FretBoard;
import org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.GuitarView;
import org.darkmentat.GuitarScalesBoxes.R;

public class Main extends Activity
{
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        GuitarView g =(GuitarView) findViewById(R.id.main_gvGuitar);
        FretBoard fretBoard = new FretBoard(4, 3)
        {{
            Tab = new Note[FretCount][StringCount];
            for(int x = 0; x < FretCount; x++)
                for(int y = 0; y < StringCount; y++)
                    Tab[x][y] = new Note(NoteQuality.OnBoard, "T");
        }};
        g.setFretBoard(fretBoard);
    }
}
