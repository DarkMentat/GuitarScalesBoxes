package org.darkmentat.GuitarScalesBoxes.Activities;

import org.darkmentat.GuitarScalesBoxes.Model.ScaleDefinition;
import org.holoeverywhere.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import org.darkmentat.GuitarScalesBoxes.R;

public class ScaleDescription extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scaledescription);

        int scaleIndex = getIntent().getIntExtra("ScaleIndex", 0);

        ((TextView) findViewById(R.id.scaledescription_tvName)).setText(ScaleDefinition.Scales[scaleIndex].Name);
        ((TextView) findViewById(R.id.scaledescription_tvDescription)).setText(ScaleDefinition.Scales[scaleIndex].Description);
    }
}