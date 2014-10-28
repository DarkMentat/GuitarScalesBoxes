package org.darkmentat.GuitarScalesBoxes.Model;

import org.darkmentat.GuitarScalesBoxes.Activities.Main;
import org.darkmentat.GuitarScalesBoxes.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ScaleDefinition
{
    public static void parseJson(String json) throws JSONException {
        JSONArray jScales = new JSONArray(json);
        for (int iScale = 0; iScale < jScales.length(); iScale++)
        {
            JSONObject jScale = jScales.getJSONObject(iScale);
            String name = jScale.getString("Name");
            String description = jScale.getString("Description");
            String stepSequence = jScale.getString("StepSequence");
            Scales.add(new ScaleDefinition(name, description,stepSequence));
            ScaleNames.add(name);
        }
    }

    public static List<ScaleDefinition> Scales = new ArrayList<>();
    public static List<String> ScaleNames = new ArrayList<>();
    static{
        StringBuilder json = new StringBuilder();
        BufferedReader stream = new BufferedReader(new InputStreamReader(Main.CurrentInstance.getResources().openRawResource(R.raw.scales)));
        try
        {
            String line = "";
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

    public final String Name;
    public final String Description;
    public final String StepSequence;

    public ScaleDefinition(String name, String description, String stepSequence) {
        Name = name;
        Description = description;
        StepSequence = stepSequence;
    }
}
