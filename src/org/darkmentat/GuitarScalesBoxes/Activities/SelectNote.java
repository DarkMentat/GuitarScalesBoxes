package org.darkmentat.GuitarScalesBoxes.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.darkmentat.GuitarScalesBoxes.Model.Scale;
import org.darkmentat.GuitarScalesBoxes.R;

import static org.darkmentat.GuitarScalesBoxes.Model.NoteModel.NoteValue;

public class SelectNote extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectnote);

        final int scale = getIntent().getIntExtra("ScaleIndex", 0);

        ListView notes = (ListView) findViewById(R.id.selectnote_lvNotes);
        notes.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, NoteValue.values()));
        notes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Main.GuitarModel.setScale(new Scale(NoteValue.values()[position], getResources().getStringArray(R.array.scale_stepSequences)[scale]));
                finish();

                Intent i = new Intent(SelectNote.this, Main.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }
}