package org.darkmentat.GuitarScalesBoxes.Model;

import android.media.AudioManager;
import android.media.SoundPool;
import org.darkmentat.GuitarScalesBoxes.Activities.Main;
import org.darkmentat.GuitarScalesBoxes.R;

import java.util.HashMap;

public class PreRecordedSoundPlayer implements SoundPlayer
{
    private SoundPool mSoundPool;
    private HashMap<String, Integer> mSoundPoolMap;

    @Override public void init() {
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
        mSoundPoolMap = new HashMap<>(65);

        //region Forming SoundPoolMap
        mSoundPoolMap.put("m1" , mSoundPool.load(Main.CurrentInstance, R.raw.m1,  1) );
        mSoundPoolMap.put("m2" , mSoundPool.load(Main.CurrentInstance, R.raw.m2,  1) );

        mSoundPoolMap.put("A1" , mSoundPool.load(Main.CurrentInstance, R.raw.a1,  1) );
        mSoundPoolMap.put("A#1", mSoundPool.load(Main.CurrentInstance, R.raw.ad1, 1) );
        mSoundPoolMap.put("H1" , mSoundPool.load(Main.CurrentInstance, R.raw.h1,  1) );

        mSoundPoolMap.put("C2" , mSoundPool.load(Main.CurrentInstance, R.raw.c2,  1) );
        mSoundPoolMap.put("C#2", mSoundPool.load(Main.CurrentInstance, R.raw.cd2, 1) );
        mSoundPoolMap.put("D2" , mSoundPool.load(Main.CurrentInstance, R.raw.d2,  1) );
        mSoundPoolMap.put("D#2", mSoundPool.load(Main.CurrentInstance, R.raw.dd2, 1) );
        mSoundPoolMap.put("E2" , mSoundPool.load(Main.CurrentInstance, R.raw.e2,  1) );
        mSoundPoolMap.put("F2" , mSoundPool.load(Main.CurrentInstance, R.raw.f2,  1) );
        mSoundPoolMap.put("F#2", mSoundPool.load(Main.CurrentInstance, R.raw.fd2, 1) );
        mSoundPoolMap.put("G2" , mSoundPool.load(Main.CurrentInstance, R.raw.g2,  1) );
        mSoundPoolMap.put("G#2", mSoundPool.load(Main.CurrentInstance, R.raw.gd2, 1) );
        mSoundPoolMap.put("A2" , mSoundPool.load(Main.CurrentInstance, R.raw.a2,  1) );
        mSoundPoolMap.put("A#2", mSoundPool.load(Main.CurrentInstance, R.raw.ad2, 1) );
        mSoundPoolMap.put("H2" , mSoundPool.load(Main.CurrentInstance, R.raw.h2,  1) );

        mSoundPoolMap.put("C3" , mSoundPool.load(Main.CurrentInstance, R.raw.c3,  1) );
        mSoundPoolMap.put("C#3", mSoundPool.load(Main.CurrentInstance, R.raw.cd3, 1) );
        mSoundPoolMap.put("D3" , mSoundPool.load(Main.CurrentInstance, R.raw.d3,  1) );
        mSoundPoolMap.put("D#3", mSoundPool.load(Main.CurrentInstance, R.raw.dd3, 1) );
        mSoundPoolMap.put("E3" , mSoundPool.load(Main.CurrentInstance, R.raw.e3,  1) );
        mSoundPoolMap.put("F3" , mSoundPool.load(Main.CurrentInstance, R.raw.f3,  1) );
        mSoundPoolMap.put("F#3", mSoundPool.load(Main.CurrentInstance, R.raw.fd3, 1) );
        mSoundPoolMap.put("G3" , mSoundPool.load(Main.CurrentInstance, R.raw.g3,  1) );
        mSoundPoolMap.put("G#3", mSoundPool.load(Main.CurrentInstance, R.raw.gd3, 1) );
        mSoundPoolMap.put("A3" , mSoundPool.load(Main.CurrentInstance, R.raw.a3,  1) );
        mSoundPoolMap.put("A#3", mSoundPool.load(Main.CurrentInstance, R.raw.ad3, 1) );
        mSoundPoolMap.put("H3" , mSoundPool.load(Main.CurrentInstance, R.raw.h3,  1) );

        mSoundPoolMap.put("C4" , mSoundPool.load(Main.CurrentInstance, R.raw.c4,  1) );
        mSoundPoolMap.put("C#4", mSoundPool.load(Main.CurrentInstance, R.raw.cd4, 1) );
        mSoundPoolMap.put("D4" , mSoundPool.load(Main.CurrentInstance, R.raw.d4,  1) );
        mSoundPoolMap.put("D#4", mSoundPool.load(Main.CurrentInstance, R.raw.dd4, 1) );
        mSoundPoolMap.put("E4" , mSoundPool.load(Main.CurrentInstance, R.raw.e4,  1) );
        mSoundPoolMap.put("F4" , mSoundPool.load(Main.CurrentInstance, R.raw.f4,  1) );
        mSoundPoolMap.put("F#4", mSoundPool.load(Main.CurrentInstance, R.raw.fd4, 1) );
        mSoundPoolMap.put("G4" , mSoundPool.load(Main.CurrentInstance, R.raw.g4,  1) );
        mSoundPoolMap.put("G#4", mSoundPool.load(Main.CurrentInstance, R.raw.gd4, 1) );
        mSoundPoolMap.put("A4" , mSoundPool.load(Main.CurrentInstance, R.raw.a4,  1) );
        mSoundPoolMap.put("A#4", mSoundPool.load(Main.CurrentInstance, R.raw.ad4, 1) );
        mSoundPoolMap.put("H4" , mSoundPool.load(Main.CurrentInstance, R.raw.h4,  1) );

        mSoundPoolMap.put("C5" , mSoundPool.load(Main.CurrentInstance, R.raw.c5,  1) );
        mSoundPoolMap.put("C#5", mSoundPool.load(Main.CurrentInstance, R.raw.cd5, 1) );
        mSoundPoolMap.put("D5" , mSoundPool.load(Main.CurrentInstance, R.raw.d5,  1) );
        mSoundPoolMap.put("D#5", mSoundPool.load(Main.CurrentInstance, R.raw.dd5, 1) );
        mSoundPoolMap.put("E5" , mSoundPool.load(Main.CurrentInstance, R.raw.e5,  1) );
        mSoundPoolMap.put("F5" , mSoundPool.load(Main.CurrentInstance, R.raw.f5,  1) );
        mSoundPoolMap.put("F#5", mSoundPool.load(Main.CurrentInstance, R.raw.fd5, 1) );
        mSoundPoolMap.put("G5" , mSoundPool.load(Main.CurrentInstance, R.raw.g5,  1) );
        mSoundPoolMap.put("G#5", mSoundPool.load(Main.CurrentInstance, R.raw.gd5, 1) );
        mSoundPoolMap.put("A5" , mSoundPool.load(Main.CurrentInstance, R.raw.a5,  1) );
        mSoundPoolMap.put("A#5", mSoundPool.load(Main.CurrentInstance, R.raw.ad5, 1) );
        mSoundPoolMap.put("H5" , mSoundPool.load(Main.CurrentInstance, R.raw.h5,  1) );

        mSoundPoolMap.put("C6" , mSoundPool.load(Main.CurrentInstance, R.raw.c6,  1) );
        mSoundPoolMap.put("C#6", mSoundPool.load(Main.CurrentInstance, R.raw.cd6, 1) );
        mSoundPoolMap.put("D6" , mSoundPool.load(Main.CurrentInstance, R.raw.d6,  1) );
        mSoundPoolMap.put("D#6", mSoundPool.load(Main.CurrentInstance, R.raw.dd6, 1) );
        mSoundPoolMap.put("E6" , mSoundPool.load(Main.CurrentInstance, R.raw.e6,  1) );
        mSoundPoolMap.put("F6" , mSoundPool.load(Main.CurrentInstance, R.raw.f6,  1) );
        mSoundPoolMap.put("F#6", mSoundPool.load(Main.CurrentInstance, R.raw.fd6, 1) );
        mSoundPoolMap.put("G6" , mSoundPool.load(Main.CurrentInstance, R.raw.g6,  1) );
        mSoundPoolMap.put("G#6", mSoundPool.load(Main.CurrentInstance, R.raw.gd6, 1) );
        mSoundPoolMap.put("A6" , mSoundPool.load(Main.CurrentInstance, R.raw.a6,  1) );
        mSoundPoolMap.put("A#6", mSoundPool.load(Main.CurrentInstance, R.raw.ad6, 1) );
        mSoundPoolMap.put("H6" , mSoundPool.load(Main.CurrentInstance, R.raw.h6,  1) );
        //endregion
    }
    @Override public void play(String key) {
        mSoundPool.autoPause();
        mSoundPool.play(mSoundPoolMap.get(key), 1f, 1f, 1, 0, 1f);
    }
    @Override public void play(NoteModel note) {
        play(note.toString());
    }
}