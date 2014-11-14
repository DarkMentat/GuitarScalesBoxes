package org.darkmentat.GuitarScalesBoxes.Model;

import android.graphics.Point;
import org.darkmentat.GuitarScalesBoxes.Activities.Main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Metronome implements Runnable
{
    public enum PlayStyle {
        Sound, Tick, TickWithAccents
    }

    public static final String MetronomeTick = "m1";
    public static final String MetronomeAccentedTick = "m2";

    public PlayStyle Style = PlayStyle.Sound;
    public boolean Looped = true;
    public int TempoBPM = 120;

    private ScheduledExecutorService mExecutor;
    private ScheduledFuture<?> mScheduledFuture;
    private SoundPlayer mSoundPlayer;

    private int mCurrent = 0;
    private int mPlayDirection = +1;
    public Metronome() {
        mExecutor = Executors.newSingleThreadScheduledExecutor();

        mSoundPlayer = new PreRecordedSoundPlayer();
        new Thread(new Runnable() {
            @Override public void run() {
                mSoundPlayer.init();
            }
        }).start();

    }

    public void play(){
        play(bpmToRate(TempoBPM));
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
    private int bpmToRate(int bpm) {
        return (int) (1000f * 60f / (float)bpm);
    }

    @Override
    public void run() {
        try
        {
            if (mCurrent >= Main.GuitarModel.Box.Points.size() || mCurrent < 0)
            {
                if(Looped)
                {
                    mPlayDirection *= -1;
                    mCurrent += mPlayDirection;
                    mCurrent += mPlayDirection;
                }
                else
                {
                    stop();
                    return;
                }
            }
            final Point p = Main.GuitarModel.Box.Points.get(mCurrent);

            switch (Style)
            {
                case Sound:
                    mSoundPlayer.play((NoteModel) Main.GuitarModel.Tab[p.x][p.y]);
                    break;
                case Tick:
                    mSoundPlayer.play(MetronomeTick);
                    break;
            }
            Main.CurrentInstance.Handler.post(new Runnable()
            {
                @Override public void run() { Main.GuitarModel.selectNote(p); }
            });

            mCurrent += mPlayDirection;
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
