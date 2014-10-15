package org.darkmentat.GuitarScalesBoxes.Model;

import android.media.MediaPlayer;
import android.os.Handler;
import org.darkmentat.GuitarScalesBoxes.Activities.Main;
import org.darkmentat.GuitarScalesBoxes.R;

public class PreRecordedSoundPlayer implements SoundPlayer
{
    private MediaPlayer mPlayer;

    @Override public void init() {
        mPlayer = MediaPlayer.create(Main.CurrentInstance, R.raw.g_7th_string_a1);
        mPlayer.setVolume(1f, 1f);
    }
    @Override public void play(NoteModel note, int duration) {
        final int noteDuration = 2000;
        final int timerRightCrop = 200;

        NoteModel a1 = new NoteModel(NoteModel.NoteValue.A, 1);
        int offset = note.getWeight() - a1.getWeight();

        mPlayer.seekTo(noteDuration * offset);
        new Handler(){{ postDelayed(new Runnable() {
            @Override public void run(){
                mPlayer.pause();
            }
        }, noteDuration - timerRightCrop); }};
        mPlayer.start();
    }
}