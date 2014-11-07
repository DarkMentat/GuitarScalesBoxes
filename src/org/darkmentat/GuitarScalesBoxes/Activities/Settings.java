package org.darkmentat.GuitarScalesBoxes.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import org.darkmentat.GuitarScalesBoxes.Fragments.CustomSetting;
import org.darkmentat.GuitarScalesBoxes.Fragments.SettingsOverview;
import org.darkmentat.GuitarScalesBoxes.R;

public class Settings extends ActionBarActivity
{
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportFragmentManager().beginTransaction().add(R.id.leftFragment, new SettingsOverview()).commit();
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.selectsetting, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.selectsetting_mCustomSetting:
                getSupportFragmentManager().beginTransaction().replace(R.id.leftFragment, new CustomSetting()).addToBackStack(null).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}