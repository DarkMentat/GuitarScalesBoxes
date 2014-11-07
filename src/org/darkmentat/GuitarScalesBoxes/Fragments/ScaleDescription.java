package org.darkmentat.GuitarScalesBoxes.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.darkmentat.GuitarScalesBoxes.Model.ScaleDefinition;
import org.darkmentat.GuitarScalesBoxes.R;

public class ScaleDescription extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_scaledescription, container, false);

        int scaleIndex = getArguments().getInt("ScaleIndex", 0);

        ((TextView) view.findViewById(R.id.scaledescription_tvName)).setText(ScaleDefinition.Scales.get(scaleIndex).Name);
        ((TextView) view.findViewById(R.id.scaledescription_tvDescription)).setText(ScaleDefinition.Scales.get(scaleIndex).Description);

        return view;
    }
}
