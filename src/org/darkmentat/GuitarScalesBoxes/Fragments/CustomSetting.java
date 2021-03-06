package org.darkmentat.GuitarScalesBoxes.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import org.darkmentat.GuitarScalesBoxes.Activities.Main;
import org.darkmentat.GuitarScalesBoxes.Model.GuitarSetting;
import org.darkmentat.GuitarScalesBoxes.Model.NoteModel;
import org.darkmentat.GuitarScalesBoxes.R;

import java.util.ArrayList;
import java.util.List;

public class CustomSetting extends Fragment
{
    private static class Tuple{
        public NoteModel.NoteValue Value;
        public Integer Octave;

        private Tuple(NoteModel.NoteValue value, Integer octave) {
            Value = value;
            Octave = octave;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customsetting, container, false);

        final List<Tuple> notes = new ArrayList<>();
        notes.add(new Tuple(null, null));
        notes.add(new Tuple(null, null));
        notes.add(new Tuple(null, null));

        final ArrayAdapter adapter = new ArrayAdapter<Tuple>(getActivity(), R.layout.item_createcustomsetting, notes){
            private android.view.LayoutInflater mInflater = (android.view.LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            @Override
            public View getView(final int notePosition, View convertView, ViewGroup parent) {
                View view = convertView;
                //todo fix that
                //if (view == null)
                view = mInflater.inflate(R.layout.item_createcustomsetting, parent, false);

                final ArrayAdapter<NoteModel.NoteValue> adapterNote = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, NoteModel.NoteValue.values());
                adapterNote.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                final ArrayAdapter<Integer> adapterOctave = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new Integer[]{0,1,2,3,4,5});
                adapterOctave.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                NoteModel.NoteValue n = getItem(notePosition).Value;
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
        ((ListView) view.findViewById(R.id.createcustomsetting_lvStrings)).setAdapter(adapter);

        view.findViewById(R.id.createcustomsetting_btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes.add(new Tuple(null,null));
                adapter.notifyDataSetChanged();
            }
        });
        view.findViewById(R.id.createcustomsetting_btnSave).setOnClickListener(new View.OnClickListener() {
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
                getActivity().finish();

                Intent i = new Intent(getActivity(), Main.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        return view;
    }
}
