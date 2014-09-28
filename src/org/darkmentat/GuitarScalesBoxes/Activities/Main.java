package org.darkmentat.GuitarScalesBoxes.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.GuitarView;
import org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.OnFretIntervalSelectedListener;
import org.darkmentat.GuitarScalesBoxes.Model.GuitarModel;
import org.darkmentat.GuitarScalesBoxes.Model.GuitarSetting;
import org.darkmentat.GuitarScalesBoxes.R;

public class Main extends Activity
{
    public static final GuitarModel GuitarModel = new GuitarModel(GuitarSetting.Default, 24);

    @Override public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final GuitarView g =(GuitarView) findViewById(R.id.main_gvGuitar);
        g.setFretBoard(GuitarModel);
        g.setOnFretIntervalSelectedListener(new OnFretIntervalSelectedListener() {
            @Override
            public void OnIntervalSelected(int startFret, int endFret) {
                if(GuitarModel.Scale != null)
                {
                    GuitarModel.setBox(new Point(startFret, 5), endFret - startFret + 1);
                    g.setMinFretCountOnScreen(GuitarModel.Box.EndFret - GuitarModel.Box.StartFret + 1);
                    g.setOffsetFret(startFret);
                }
            }
        });
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,0,0, "Select scale");
        menu.add(0,1,1, "Select setting");

        return super.onCreateOptionsMenu(menu);
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case 0:
                startActivity(new Intent(this, ScalesOverview.class));
                break;
            case 1:
                startActivity(new Intent(this, SelectSetting.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
