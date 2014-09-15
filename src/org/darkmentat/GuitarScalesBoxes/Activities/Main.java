package org.darkmentat.GuitarScalesBoxes.Activities;

import android.app.Activity;
import android.os.Bundle;
import org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.FretBoard;
import org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.GuitarView;
import org.darkmentat.GuitarScalesBoxes.Model.GuitarModel;
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
        FretBoard fretBoard = new GuitarModel(6, 24);
        g.setFretBoard(fretBoard);
    }
}
