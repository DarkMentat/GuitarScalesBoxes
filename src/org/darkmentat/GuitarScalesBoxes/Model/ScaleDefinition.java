package org.darkmentat.GuitarScalesBoxes.Model;

import org.darkmentat.GuitarScalesBoxes.Activities.Main;
import org.darkmentat.GuitarScalesBoxes.R;
import org.json.JSONException;
import org.json.JSONObject;

public class ScaleDefinition
{
    public static ScaleDefinition parseJson(String json) throws JSONException {
        JSONObject jScale = new JSONObject(json);
        String name = jScale.getString("Name");
        String description = jScale.getString("Description");
        String stepSequence = jScale.getString("StepSequence");

        return new ScaleDefinition(name, description, stepSequence);
    }

    public static ScaleDefinition[] Scales;
    public static String[] ScaleNames;
    static{
        String[] scales = Main.CurrentInstance.getApplicationContext().getResources().getStringArray(R.array.Scales);

        Scales = new ScaleDefinition[scales.length];
        ScaleNames = new String[scales.length];
        try
        {
            for (int i = 0; i < scales.length; i++)
            {
                Scales[i] = parseJson(scales[i]);
                ScaleNames[i] = Scales[i].Name;
            }
        }
        catch (JSONException e)
        {
            throw new RuntimeException("Bad json!!");
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
