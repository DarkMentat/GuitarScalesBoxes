package org.darkmentat.GuitarScalesBoxes.Activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import org.darkmentat.GuitarScalesBoxes.Fragments.ScaleDescription;
import org.darkmentat.GuitarScalesBoxes.Fragments.ScalesOverview;
import org.darkmentat.GuitarScalesBoxes.Fragments.SelectNote;
import org.darkmentat.GuitarScalesBoxes.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.LayoutParams;

public class Scales extends ActionBarActivity implements ScalesOverview.ScalesOverviewListener
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

        int sideFragment = isScreenLarge()? R.id.rightFragment : R.id.leftFragment;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(sideFragment, frag);
        if(getSupportFragmentManager().findFragmentById(R.id.rightFragment) == null)
            transaction.addToBackStack(null);
        transaction.commit();

        if(isScreenLarge())
            findViewById(R.id.rightFragment).setLayoutParams(new LayoutParams(MATCH_PARENT, WRAP_CONTENT, 1));
    }
    @Override public void onScaleAskedForDescription(int scaleIndex) {
        Bundle bundle = new Bundle();
        bundle.putInt("ScaleIndex", scaleIndex);
        ScaleDescription frag = new ScaleDescription();
        frag.setArguments(bundle);

        int sideFragment = isScreenLarge()? R.id.rightFragment : R.id.leftFragment;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(sideFragment, frag);
        if(getSupportFragmentManager().findFragmentById(R.id.rightFragment) == null)
            transaction.addToBackStack(null);
        transaction.commit();

        if(isScreenLarge())
            findViewById(R.id.rightFragment).setLayoutParams(new LayoutParams(MATCH_PARENT, WRAP_CONTENT, 1));
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.rightFragment);
        if(frag != null)
            getSupportFragmentManager().beginTransaction().remove(frag).commit();
        findViewById(R.id.rightFragment).setLayoutParams(new LayoutParams(0, WRAP_CONTENT, 1));
    }

    private boolean isScreenLarge() {
        return (getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
