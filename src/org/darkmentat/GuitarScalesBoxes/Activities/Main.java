package org.darkmentat.GuitarScalesBoxes.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.GuitarView;
import org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.OnFretIntervalSelectedListener;
import org.darkmentat.GuitarScalesBoxes.Model.GuitarModel;
import org.darkmentat.GuitarScalesBoxes.Model.GuitarSetting;
import org.darkmentat.GuitarScalesBoxes.Model.Metronome;
import org.darkmentat.GuitarScalesBoxes.R;
import org.holoeverywhere.app.Activity;

public class Main extends Activity implements OnFretIntervalSelectedListener, ActionMode.Callback
{
    public static Main CurrentInstance;
    public static GuitarModel GuitarModel;

    public Handler Handler = new Handler();

    private GuitarView mGuitarView;
    private Metronome mMetronome;

    @Override public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        CurrentInstance = this;
        GuitarModel = new GuitarModel(GuitarSetting.Settings[0], 24);
        mGuitarView = (GuitarView) findViewById(R.id.main_gvGuitar);
        mGuitarView.setFretBoard(GuitarModel);
        mGuitarView.setOnFretIntervalSelectedListener(this);
        mMetronome = new Metronome();
    }
    @Override public void OnIntervalSelected(int startFret, int endFret) {
        if (GuitarModel.Scale != null)
        {
            GuitarModel.setBox(startFret, endFret);
            mGuitarView.setMinFretCountOnScreen(GuitarModel.Box.EndFret - GuitarModel.Box.StartFret + 1);
            mGuitarView.setOffsetFret(GuitarModel.Box.StartFret);
        }
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
            case R.id.main_mIterateBox:

                startSupportActionMode(this);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.metronome, menu);
        return true;
    }
    @Override public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }
    @Override public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.main_mMetronomePlay:
                mMetronome.play(200);
            break;
            case R.id.main_mMetronomeStop:
                mMetronome.stop();
                break;
        }

        return true;
    }
    @Override public void onDestroyActionMode(ActionMode mode) {
        mMetronome.stop();
    }
}
