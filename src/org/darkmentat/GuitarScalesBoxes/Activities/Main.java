package org.darkmentat.GuitarScalesBoxes.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.GuitarView;
import org.darkmentat.GuitarScalesBoxes.Model.GuitarModel;
import org.darkmentat.GuitarScalesBoxes.Model.GuitarSetting;
import org.darkmentat.GuitarScalesBoxes.Model.NoteModel;
import org.darkmentat.GuitarScalesBoxes.Model.Scale;
import org.darkmentat.GuitarScalesBoxes.R;

public class Main extends Activity
{
    @Override public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        GuitarView g =(GuitarView) findViewById(R.id.main_gvGuitar);
        GuitarModel fretBoard = new GuitarModel(GuitarSetting.Default, 24);
        fretBoard.setScale(new Scale(NoteModel.NoteValue.A, "212212"));
        g.setFretBoard(fretBoard);

        startActivity(new Intent(this, ScalesOverview.class));
    }
}
