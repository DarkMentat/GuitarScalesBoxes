package org.darkmentat.GuitarScalesBoxes.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.darkmentat.GuitarScalesBoxes.Model.ScaleDefinition;
import org.darkmentat.GuitarScalesBoxes.R;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Fragment;

public class ScalesOverview extends Fragment
{
    public static interface ScalesOverviewListener{
        void onScaleSelected(int scaleIndex);
        void onScaleAskedForDescription(int scaleIndex);
    }

    private ScalesOverviewListener mScalesOverviewListener;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scaleoverview, container, false);

        ListView scales = (ListView) view.findViewById(R.id.scalesoverview_lvScales);
        scales.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item_scalesoverview, ScaleDefinition.ScaleNames){
            private android.view.LayoutInflater mInflater = (android.view.LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View view = convertView;
                if (view == null)
                    view = mInflater.inflate(R.layout.item_scalesoverview, parent, false);

                ((TextView) view.findViewById(R.id.listviewitem_tvText)).setText(this.getItem(position));
                view.findViewById(R.id.listviewitem_btnInfo).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        if(mScalesOverviewListener != null)
                            mScalesOverviewListener.onScaleAskedForDescription(position);
                    }
                });
                return view;
            }
        });
        scales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mScalesOverviewListener != null)
                    mScalesOverviewListener.onScaleSelected(position);
            }
        });

        return view;
    }
    public void setScalesOverviewListener(ScalesOverviewListener listener){
        mScalesOverviewListener = listener;
    }
}