package com.example.app2241.views.ui.spinner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.app2241.R;

import java.util.ArrayList;

public class AdapterSpinnerFilter extends BaseAdapter {

    private Activity activity;
    private ArrayList<FiltersSpinner> filters_values;

    public AdapterSpinnerFilter(Activity activity, ArrayList<FiltersSpinner> filters_values) {
        this.activity = activity;
        this.filters_values = filters_values;
    }

    @Override
    public int getCount() {
        return filters_values.size();
    }

    @Override
    public Object getItem(int position) {
        return filters_values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {//realiza el inflado del layout solo si se ha realizado por primera vez

            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.style_spinner, null);

            TextView textview_filter = v.findViewById(R.id.textview_values_filters);
            FiltersSpinner filtersSpinner = filters_values.get(position);

            textview_filter.setText("  " +filtersSpinner.getTextview_filter());
            textview_filter.setCompoundDrawablesWithIntrinsicBounds(filtersSpinner.getImage_filter(),0,0,0);
        }
        return v;
    }
}