package org.darkmentat.GuitarScalesBoxes.Activities;

import android.os.Bundle;
import org.darkmentat.GuitarScalesBoxes.Fragments.ScaleDescription;
import org.darkmentat.GuitarScalesBoxes.Fragments.ScalesOverview;
import org.darkmentat.GuitarScalesBoxes.Fragments.SelectNote;
import org.darkmentat.GuitarScalesBoxes.R;
import org.holoeverywhere.app.Activity;

public class Scales extends Activity implements ScalesOverview.ScalesOverviewListener
{
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scales);

        ScalesOverview frag = new ScalesOverview();
        frag.setScalesOverviewListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.leftFragment, frag).commit();
    }

    @Override public void onScaleSelected(int scaleIndex) {
        Bundle bundle = new Bundle();
        bundle.putInt("ScaleIndex", scaleIndex);
        SelectNote frag = new SelectNote();
        frag.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.leftFragment, frag).addToBackStack(null).commit();
    }
    @Override public void onScaleAskedForDescription(int scaleIndex) {
        Bundle bundle = new Bundle();
        bundle.putInt("ScaleIndex", scaleIndex);
        ScaleDescription frag = new ScaleDescription();
        frag.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.leftFragment, frag).addToBackStack(null).commit();
    }
}
