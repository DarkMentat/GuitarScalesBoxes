package org.darkmentat.GuitarScalesBoxes.Activities;

import org.holoeverywhere.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.holoeverywhere.widget.AdapterView;
import org.holoeverywhere.widget.ArrayAdapter;
import org.holoeverywhere.widget.ListView;
import org.holoeverywhere.widget.Spinner;
import org.darkmentat.GuitarScalesBoxes.Model.GuitarSetting;
import org.darkmentat.GuitarScalesBoxes.Model.NoteModel;
import org.darkmentat.GuitarScalesBoxes.R;

import java.util.ArrayList;
import java.util.List;

import static org.darkmentat.GuitarScalesBoxes.Model.NoteModel.NoteValue;

public class CreateCustomSetting extends Activity
{
    private static class Tuple{
        public NoteValue Value;
        public Integer Octave;

        private Tuple(NoteValue value, Integer octave) {
            Value = value;
            Octave = octave;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createcustomsetting);

        final List<Tuple> notes = new ArrayList<>();
        notes.add(new Tuple(null, null));
        notes.add(new Tuple(null, null));
        notes.add(new Tuple(null, null));

        final ArrayAdapter adapter = new ArrayAdapter<Tuple>(this, R.layout.createcustomsetting_item, notes){
            private LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            @Override
            public View getView(final int notePosition, View convertView, ViewGroup parent) {
                View view = convertView;
                //todo fix that
                //if (view == null)
                    view = mInflater.inflate(R.layout.createcustomsetting_item, parent, false);

                final ArrayAdapter<NoteValue> adapterNote = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, NoteValue.values());
                adapterNote.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                final ArrayAdapter<Integer> adapterOctave = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new Integer[]{0,1,2,3,4,5});
                adapterOctave.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                NoteValue n = getItem(notePosition).Value;
                Integer o = getItem(notePosition).Octave;

                ((Spinner) view.findViewById(R.id.createcustomsetting_item_note)).setAdapter(adapterNote);
                if(n != null) ((Spinner) view.findViewById(R.id.createcustomsetting_item_note)).setSelection(n.ordinal());
                ((Spinner) view.findViewById(R.id.createcustomsetting_item_note)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        getItem(notePosition).Value = adapterNote.getItem(position);
                    }
                    @Override public void onNothingSelected(AdapterView<?> parent) {}
                });
                ((Spinner) view.findViewById(R.id.createcustomsetting_item_octave)).setAdapter(adapterOctave);
                if(o != null) ((Spinner) view.findViewById(R.id.createcustomsetting_item_octave)).setSelection(o);
                ((Spinner) view.findViewById(R.id.createcustomsetting_item_octave)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        getItem(notePosition).Octave = position;
                    }
                    @Override public void onNothingSelected(AdapterView<?> parent) {}
                });

                view.findViewById(R.id.createcustomsetting_item_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notes.remove(notePosition);
                        notifyDataSetChanged();
                    }
                });

                return view;
            }
        };
        ((ListView) findViewById(R.id.createcustomsetting_lvStrings)).setAdapter(adapter);

        findViewById(R.id.createcustomsetting_btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes.add(new Tuple(null,null));
                adapter.notifyDataSetChanged();
            }
        });
        findViewById(R.id.createcustomsetting_btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean allIsOk = notes.size() > 2;

                for (Tuple note : notes)
                {
                    if(note.Value == null) { allIsOk = false; break; }
                    if(note.Octave == null) { allIsOk = false; break; }
                }
                if(!allIsOk) return;

                NoteModel[] setting = new NoteModel[notes.size()];
                for (int i = 0; i < setting.length; i++)
                    setting[i] = new NoteModel(notes.get(i).Value, notes.get(i).Octave);

                Main.GuitarModel.setSetting(new GuitarSetting(setting));
                finish();

                Intent i = new Intent(CreateCustomSetting.this, Main.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

}