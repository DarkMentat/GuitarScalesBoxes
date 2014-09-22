package org.darkmentat.GuitarScalesBoxes.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.GuitarView;
import org.darkmentat.GuitarScalesBoxes.Model.GuitarModel;
import org.darkmentat.GuitarScalesBoxes.Model.GuitarSetting;
import org.darkmentat.GuitarScalesBoxes.R;

public class Main extends Activity
{
    public static GuitarModel GuitarModel = new GuitarModel(GuitarSetting.Default, 24);

    @Override public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        GuitarView g =(GuitarView) findViewById(R.id.main_gvGuitar);
        g.setFretBoard(GuitarModel);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Select scale");

        return super.onCreateOptionsMenu(menu);
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this, ScalesOverview.class));

        return super.onOptionsItemSelected(item);
    }
}
