package org.darkmentat.GuitarScalesBoxes.Model;

import org.darkmentat.GuitarScalesBoxes.Activities.Main;
import org.darkmentat.GuitarScalesBoxes.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static org.darkmentat.GuitarScalesBoxes.Model.NoteModel.NoteValue;

public final class GuitarSetting
{
    public static GuitarSetting parseJson(String json) throws JSONException {
        JSONObject jSetting = new JSONObject(json);
        String name = jSetting.getString("Name");
        JSONArray jNotes = jSetting.getJSONArray("Notes");
        NoteModel[] notes = new NoteModel[jNotes.length()];
        for (int i = 0; i < jNotes.length(); i++)
        {
            JSONObject jNote = jNotes.getJSONObject(i);
            String value = jNote.getString("Value");
            int octave = jNote.getInt("Octave");
            notes[i] = new NoteModel(NoteValue.valueOf(value), octave);
        }
        return new GuitarSetting(notes, name);
    }

    public static GuitarSetting[] Settings;
    static{
        String[] settings = Main.CurrentInstance.getApplicationContext().getResources().getStringArray(R.array.GuitarSettings);

        Settings = new GuitarSetting[settings.length];
        try
        {
            for (int i = 0; i < settings.length; i++)
                Settings[i] = parseJson(settings[i]);
        }
        catch (JSONException e)
        {
            throw new RuntimeException("Bad json!!");
        }
    }

    public final int StringCount;
    public final NoteModel[] StartNotes;
    private final String mName;

    public GuitarSetting(NoteModel[] notes) {
        this(notes, "");
    }
    public GuitarSetting(NoteModel[] notes, String name) {
        mName = name;
        StringCount = notes.length;
        StartNotes = notes;
    }

    @Override public String toString() {
        return mName;
    }
}
