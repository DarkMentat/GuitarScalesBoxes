package org.darkmentat.GuitarScalesBoxes.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import org.darkmentat.GuitarScalesBoxes.Activities.Main;
import org.darkmentat.GuitarScalesBoxes.Model.GuitarSetting;
import org.darkmentat.GuitarScalesBoxes.R;

import java.util.List;

public class SettingsOverview extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settingsoverview, container, false);

        ExpandableListView settings = (ExpandableListView) view.findViewById(R.id.selectsetting_elvSettings);
        settings.setAdapter(new GuitarSettingExpandableListAdapter(getActivity(), GuitarSetting.Instruments));
        settings.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Main.GuitarModel.setSetting(GuitarSetting.Instruments.get(groupPosition).Settings.get(childPosition));
                getActivity().finish();

                Intent i = new Intent(getActivity(), Main.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                return true;
            }
        });

        return view;
    }

    private static class GuitarSettingExpandableListAdapter extends BaseExpandableListAdapter
    {
        private List<GuitarSetting.Instrument> mInstruments;
        private LayoutInflater mInflater;

        public GuitarSettingExpandableListAdapter(Context context, List<GuitarSetting.Instrument> instruments) {
            mInstruments = instruments;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override public int getGroupCount() {
            return mInstruments.size();
        }
        @Override public int getChildrenCount(int groupPosition) {
            return mInstruments.get(groupPosition).Settings.size();
        }

        @Override public Object getGroup(int groupPosition) {
            return mInstruments.get(groupPosition);
        }
        @Override public Object getChild(int groupPosition, int childPosition) {
            return mInstruments.get(groupPosition).Settings.get(childPosition);
        }

        @Override public long getGroupId(int groupPosition) {
            return 0;
        }
        @Override public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }
        @Override public boolean hasStableIds() {
            return false;
        }

        @Override public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if (convertView == null)
                convertView = mInflater.inflate(android.R.layout.simple_expandable_list_item_1, null);

            GuitarSetting.Instrument instrument = (GuitarSetting.Instrument) getGroup(groupPosition);

            TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
            textView.setText(instrument.Name);

            return convertView;
        }
        @Override public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView =  mInflater.inflate(android.R.layout.simple_expandable_list_item_1, null);

            GuitarSetting children = (GuitarSetting) getChild(groupPosition, childPosition);

            TextView text = (TextView) convertView.findViewById(android.R.id.text1);
            text.setText(children.Name);

            return convertView;
        }

        @Override public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
