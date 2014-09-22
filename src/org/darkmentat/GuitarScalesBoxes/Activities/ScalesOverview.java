package org.darkmentat.GuitarScalesBoxes.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.darkmentat.GuitarScalesBoxes.R;

public class ScalesOverview extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scalesoverview);

        ListView scales = (ListView) findViewById(R.id.scalesoverview_lvScales);
        scales.setAdapter(new ArrayAdapter<String>(this, R.layout.listviewitem, getResources().getStringArray(R.array.scale_Names))
        {
            private LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View view = convertView;
                if (view == null)
                    view = mInflater.inflate(R.layout.listviewitem, parent, false);

                ((TextView) view.findViewById(R.id.listviewitem_tvText)).setText(this.getItem(position));
                view.findViewById(R.id.listviewitem_tvText).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ScalesOverview.this, SelectNote.class);
                        i.putExtra("ScaleIndex", position);
                        startActivity(i);
                    }
                });
                view.findViewById(R.id.listviewitem_btnInfo).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ScalesOverview.this, ScaleDescription.class);
                        i.putExtra("ScaleIndex", position);
                        startActivity(i);
                    }
                });
                return view;
            }
        });
    }
}