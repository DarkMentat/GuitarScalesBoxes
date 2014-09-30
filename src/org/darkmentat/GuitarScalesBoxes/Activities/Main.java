package org.darkmentat.GuitarScalesBoxes.Activities;

import android.app.Activity;
import android.content.Intent;
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
                    GuitarModel.setBox(startFret, endFret);
                    g.setMinFretCountOnScreen(GuitarModel.Box.EndFret - GuitarModel.Box.StartFret + 1);
                    g.setOffsetFret(GuitarModel.Box.StartFret);
                }
            }
        });
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,0,0, "Select scale");
        menu.add(0,1,1, "Select setting");
        menu.add(0,2,2, "Cleanup all");
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
            case 2:
                GuitarModel.setBox(null);
                GuitarModel.setScale(null);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
