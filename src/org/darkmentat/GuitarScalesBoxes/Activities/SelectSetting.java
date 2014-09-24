package org.darkmentat.GuitarScalesBoxes.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.darkmentat.GuitarScalesBoxes.Model.GuitarSetting;
import org.darkmentat.GuitarScalesBoxes.R;

public class SelectSetting extends Activity
{
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectsetting);

        ListView settings = (ListView) findViewById(R.id.selectsetting_lvSettings);
        settings.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, GuitarSetting.Settings));
        settings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Main.GuitarModel.setSetting(GuitarSetting.Settings[position]);
                finish();

                Intent i = new Intent(SelectSetting.this, Main.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,0,0, "Custom setting");

        return super.onCreateOptionsMenu(menu);
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case 0:
                startActivity(new Intent(this, CreateCustomSetting.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}