package org.darkmentat.GuitarScalesBoxes.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.GuitarView;
import org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.OnFretIntervalSelectedListener;
import org.darkmentat.GuitarScalesBoxes.Model.*;
import org.darkmentat.GuitarScalesBoxes.R;

import java.util.Observable;
import java.util.Observer;

public class Main extends ActionBarActivity implements OnFretIntervalSelectedListener, ActionMode.Callback, Observer
{
    public static Main CurrentInstance;
    public static GuitarModel GuitarModel;

    public Handler Handler = new Handler();

    private GuitarView mGuitarView;
    private Metronome mMetronome;
    private Menu mMenu;

    private boolean mActionMode = false;

    @Override public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CurrentInstance = this;

        loadPreferences();
        if(GuitarModel == null)
            GuitarModel = new GuitarModel(GuitarSetting.Settings.get(0), 24);
        GuitarModel.addObserver(this);

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
        mMenu = menu;
        update(GuitarModel, null);
        return true;
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.main_mSelectScale:
                startActivity(new Intent(this, Scales.class));
                break;
            case R.id.main_mSelectSetting:
                startActivity(new Intent(this, Settings.class));
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
        mActionMode = true;
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
        mActionMode = false;
        mMetronome.stop();
    }

    @Override public void update(Observable observable, Object data) {
        if(observable == null)
            return;
        GuitarModel model = (GuitarModel) observable;

        if(model.Scale != null)
            mMenu.findItem(R.id.main_mCleanUpAll).setVisible(true);
        else
            mMenu.findItem(R.id.main_mCleanUpAll).setVisible(false);

        if(model.Box != null)
            mMenu.findItem(R.id.main_mIterateBox).setVisible(true);
        else
            mMenu.findItem(R.id.main_mIterateBox).setVisible(false);
    }

    @Override public void onBackPressed() {
        if(mActionMode)
            super.onBackPressed();
        else
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing Activity")
                    .setMessage("Are you sure you want to close this activity?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
    }
    @Override protected void onPause() {
        super.onPause();
        savePreferences();
    }

    private void savePreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("FretCount", GuitarModel.FretCount);
        editor.putString("SettingName", GuitarModel.Setting.Name);
        editor.putString("SettingNotes", notesArrayToString(GuitarModel.Setting.StartNotes));

        editor.putString("ScaleStepSequence", GuitarModel.Scale != null ? GuitarModel.Scale.StepSequence : "");
        editor.putString("ScaleTonicValue", GuitarModel.Scale != null ? GuitarModel.Scale.Tonic.Value.name() : "");

        editor.putInt("BoxStartFret", GuitarModel.Box != null ? GuitarModel.Box.StartFret : -1);
        editor.putInt("BoxEndFret", GuitarModel.Box != null ? GuitarModel.Box.EndFret : -1);

        editor.commit();
    }
    private void loadPreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        int fretCount = preferences.getInt("FretCount", -1);

        String settingName = preferences.getString("SettingName", "");
        NoteModel[] settingNotes = notesArrayFromString(preferences.getString("SettingNotes", ""));

        if(fretCount == -1 || settingNotes.length <= 0) return;

        GuitarModel = new GuitarModel(new GuitarSetting(settingNotes, settingName), fretCount);

        String stepSequence = preferences.getString("ScaleStepSequence", "");
        String tonicValue = preferences.getString("ScaleTonicValue", "");

        if(stepSequence.equals("") || tonicValue.equals("")) return;

        GuitarModel.setScale(new Scale(NoteModel.NoteValue.valueOf(tonicValue), stepSequence));

        int boxStartFret = preferences.getInt("BoxStartFret", -1);
        int boxEndFret = preferences.getInt("BoxEndFret", -1);

        if(boxStartFret < 0 || boxEndFret < 0) return;

        GuitarModel.setBox(boxStartFret, boxEndFret);
    }

    private String notesArrayToString(NoteModel[] notes){
        StringBuilder res = new StringBuilder();
        for (NoteModel note : notes)
        {
            res.append(note.Value.name());
            res.append(' ');
            res.append(note.Octave.ordinal());
            res.append(" | ");
        }
        res.delete(res.length()-3, res.length());

        return res.toString();
    }
    private NoteModel[] notesArrayFromString(String string){
        if(string.equals(""))
            return new NoteModel[0];

        String[] split = string.split(" \\| ");

        NoteModel[] res = new NoteModel[split.length];

        for (int i = 0; i < res.length; i++)
        {
            String[] note = split[i].split(" ");
            NoteModel.NoteValue value = NoteModel.NoteValue.valueOf(note[0]);
            Integer octave = Integer.decode(note[1]);
            res[i] = new NoteModel(value,octave);
        }

        return res;
    }
}
