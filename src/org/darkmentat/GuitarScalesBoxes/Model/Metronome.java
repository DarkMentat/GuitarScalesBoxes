package org.darkmentat.GuitarScalesBoxes.Model;

import android.graphics.Point;
import org.darkmentat.GuitarScalesBoxes.Activities.Main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Metronome implements Runnable
{
    public boolean PlayScale = true;
    public boolean Tick;

    private ScheduledExecutorService mExecutor;
    private ScheduledFuture<?> mScheduledFuture;
    private SoundPlayer mSoundPlayer;

    private int mCurrent = 0;

    public Metronome() {
        mExecutor = Executors.newSingleThreadScheduledExecutor();

        mSoundPlayer = new PreRecordedSoundPlayer();
        mSoundPlayer.init();
    }

    public void play(int rate) {
        stop();
        mScheduledFuture = mExecutor.scheduleAtFixedRate(this, 0, rate, TimeUnit.MILLISECONDS);
    }
    public void stop() {
        if(mScheduledFuture != null)
            mScheduledFuture.cancel(true);
        Main.CurrentInstance.Handler.post(new Runnable()
        {
            @Override public void run() { Main.GuitarModel.unSelectNote(); }
        });
        mCurrent = 0;
    }

    @Override
    public void run() {
        try
        {
            if (mCurrent >= Main.GuitarModel.Box.Points.size())
            {
                stop();
                return;
            }
            final Point p = Main.GuitarModel.Box.Points.get(mCurrent);

            if(PlayScale)
                mSoundPlayer.play((NoteModel) Main.GuitarModel.Tab[p.x][p.y]);

            Main.CurrentInstance.Handler.post(new Runnable()
            {
                @Override public void run() { Main.GuitarModel.selectNote(p); }
            });

            mCurrent++;
        }
        catch (final Exception e)
        {
            Main.CurrentInstance.Handler.post(new Runnable()
            {
                @Override public void run() { throw e; }
            });
        }
    }
}
