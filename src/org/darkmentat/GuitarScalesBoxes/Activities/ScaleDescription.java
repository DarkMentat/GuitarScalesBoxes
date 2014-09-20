package org.darkmentat.GuitarScalesBoxes.Activities;

import android.app.Activity;
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

        ((TextView) findViewById(R.id.scaledescription_tvName)).setText(getResources().getStringArray(R.array.scale_Names)[scaleIndex]);
        ((TextView) findViewById(R.id.scaledescription_tvDescription)).setText(getResources().getStringArray(R.array.scale_Descriptions)[scaleIndex]);
    }
}