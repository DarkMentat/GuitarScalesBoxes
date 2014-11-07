package org.darkmentat.GuitarScalesBoxes.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.darkmentat.GuitarScalesBoxes.Activities.Main;
import org.darkmentat.GuitarScalesBoxes.Model.NoteModel;
import org.darkmentat.GuitarScalesBoxes.Model.Scale;
import org.darkmentat.GuitarScalesBoxes.Model.ScaleDefinition;
import org.darkmentat.GuitarScalesBoxes.R;

import static android.widget.AdapterView.OnItemClickListener;

public class SelectNote extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_selectnote, container, false);

        final int scale = getArguments().getInt("ScaleIndex", 0);

        ListView notes = (ListView) view.findViewById(R.id.selectnote_lvNotes);
        notes.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, NoteModel.NoteValue.values()));
        notes.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {
                Main.GuitarModel.setScale(new Scale(NoteModel.NoteValue.values()[position], ScaleDefinition.Scales.get(scale).StepSequence));
                getActivity().finish();

                Intent i = new Intent(getActivity(), Main.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        return view;
    }
}
