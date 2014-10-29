package org.darkmentat.GuitarScalesBoxes.Activities;

import org.holoeverywhere.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import org.darkmentat.GuitarScalesBoxes.Model.GuitarSetting;
import org.darkmentat.GuitarScalesBoxes.R;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.BaseExpandableListAdapter;
import org.holoeverywhere.widget.ExpandableListView;

import java.util.List;

public class SelectSetting extends Activity
{
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectsetting);

        ExpandableListView settings = (ExpandableListView) findViewById(R.id.selectsetting_elvSettings);
        settings.setAdapter(new GuitarSettingExpandableListAdapter(this, GuitarSetting.Instruments));
        settings.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Main.GuitarModel.setSetting(GuitarSetting.Instruments.get(groupPosition).Settings.get(childPosition));
                finish();

                Intent i = new Intent(SelectSetting.this, Main.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                return true;
            }
        });
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.selectsetting, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.selectsetting_mCustomSetting:
                startActivity(new Intent(this, CreateCustomSetting.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class GuitarSettingExpandableListAdapter extends BaseExpandableListAdapter{
        private List<GuitarSetting.Instrument>  mInstruments;
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