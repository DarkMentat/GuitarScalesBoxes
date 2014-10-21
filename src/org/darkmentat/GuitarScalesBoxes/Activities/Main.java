package org.darkmentat.GuitarScalesBoxes.Activities;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.GuitarView;
import org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.OnFretIntervalSelectedListener;
import org.darkmentat.GuitarScalesBoxes.Model.*;
import org.darkmentat.GuitarScalesBoxes.R;
import org.holoeverywhere.app.Activity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends Activity
{
    public static Main CurrentInstance;
    public static GuitarModel GuitarModel;

    public Handler Handler = new Handler();

    private GuitarView mGuitarView;
    private SoundPlayer mSoundPlayer;

    @Override public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        CurrentInstance = this;
        GuitarModel = new GuitarModel(GuitarSetting.Settings[0], 24);
        mGuitarView = (GuitarView) findViewById(R.id.main_gvGuitar);
        final GuitarView g = mGuitarView;
        g.setFretBoard(GuitarModel);
        g.setOnFretIntervalSelectedListener(new OnFretIntervalSelectedListener()
        {
            @Override
            public void OnIntervalSelected(int startFret, int endFret) {
                if (GuitarModel.Scale != null)
                {
                    GuitarModel.setBox(startFret, endFret);
                    g.setMinFretCountOnScreen(GuitarModel.Box.EndFret - GuitarModel.Box.StartFret + 1);
                    g.setOffsetFret(GuitarModel.Box.StartFret);
                }
            }
        });

        mSoundPlayer = new PreRecordedSoundPlayer();
        mSoundPlayer.init();
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
                final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                executor.scheduleAtFixedRate(new Runnable() {
                    private int mCurrent = 0;
                    @Override
                    public void run() {
                        try
                        {
                            if (mCurrent >= GuitarModel.Box.Points.size())
                            {
                                Main.CurrentInstance.Handler.post(new Runnable() {
                                    @Override public void run() { GuitarModel.unSelectNote();
                                    }});
                                executor.shutdown();
                                return;
                            }
                            final Point p = GuitarModel.Box.Points.get(mCurrent);

                            mSoundPlayer.play((NoteModel) GuitarModel.Tab[p.x][p.y]);
                            Main.CurrentInstance.Handler.post(new Runnable() {
                                @Override public void run() { GuitarModel.selectNote(p); }});

                            mCurrent++;
                        }catch (final Exception e)
                        {
                            Main.CurrentInstance.Handler.post(new Runnable() {
                                @Override public void run() { throw e;
                                }});
                        }
                    }
                }, 0, 500, TimeUnit.MILLISECONDS);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
