package org.darkmentat.GuitarScalesBoxes.Model;

import org.darkmentat.GuitarScalesBoxes.Activities.Main;
import org.darkmentat.GuitarScalesBoxes.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static org.darkmentat.GuitarScalesBoxes.Model.NoteModel.NoteValue;

public final class GuitarSetting
{
    public static class Instrument{
        public final String Name;
        public final List<GuitarSetting> Settings;

        public Instrument(String name) {
            Name = name;
            Settings = new ArrayList<>();
        }
    }

    public static void parseJson(String json) throws JSONException {
        JSONArray jInstruments = new JSONArray(json);
        for (int iInstrument = 0; iInstrument < jInstruments.length(); iInstrument++)
        {
            JSONObject jInstrument = jInstruments.getJSONObject(iInstrument);
            Instrument instrument = new Instrument(jInstrument.getString("Instrument"));
            JSONArray jSettings = jInstrument.getJSONArray("Settings");
            for (int iSetting = 0; iSetting < jSettings.length(); iSetting++)
            {
                JSONObject jSetting = jSettings.getJSONObject(iSetting);
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
                GuitarSetting setting = new GuitarSetting(notes, name);
                Settings.add(setting);
                instrument.Settings.add(setting);
            }
            Instruments.add(instrument);
        }
    }

    public static List<Instrument> Instruments = new ArrayList<>();
    public static List<GuitarSetting> Settings = new ArrayList<>();
    static{
        StringBuilder json = new StringBuilder();
        BufferedReader stream = new BufferedReader(new InputStreamReader(Main.CurrentInstance.getResources().openRawResource(R.raw.guitar_settings)));
        try
        {
            String line;
            while ((line = stream.readLine()) != null)
                json.append(line);

            stream.close();
            parseJson(json.toString());
        }
        catch (JSONException e)
        {
            throw new RuntimeException("Bad json!!");
        }
        catch (IOException e)
        {
            throw new RuntimeException("Bad json file!!");
        }
    }

    public final int StringCount;
    public final NoteModel[] StartNotes;
    public final String Name;

    public GuitarSetting(NoteModel[] notes) {
        this(notes, "");
    }
    public GuitarSetting(NoteModel[] notes, String name) {
        Name = name;
        StringCount = notes.length;
        StartNotes = notes;
    }

    @Override public String toString() {
        return Name;
    }
}
