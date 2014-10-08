package org.darkmentat.GuitarScalesBoxes.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.GuitarView;
import org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.OnFretIntervalSelectedListener;
import org.darkmentat.GuitarScalesBoxes.Model.GuitarModel;
import org.darkmentat.GuitarScalesBoxes.Model.GuitarSetting;
import org.darkmentat.GuitarScalesBoxes.R;
import org.holoeverywhere.app.Activity;

public class Main extends Activity
{
    public static final GuitarModel GuitarModel = new GuitarModel(GuitarSetting.Default, 24);
    private GuitarView mGuitarView;

    @Override public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mGuitarView = (GuitarView) findViewById(R.id.main_gvGuitar);
        final GuitarView g = mGuitarView;
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
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.main_mSelectScale:
                startActivity(new Intent(this, ScalesOverview.class));
                break;
            case R.id.main_mSelectSetting:
                startActivity(new Intent(this, SelectSetting.class));
                break;
            case R.id.main_mCleanUpAll:
                GuitarModel.setBox(null);
                GuitarModel.setScale(null);
                mGuitarView.setMinFretCountOnScreen(0);
                mGuitarView.setOffsetFret(-1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
