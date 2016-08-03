package com.example.alex_lenovi.spikenlosmanuals;

/**
 * Created by ADSL on 21/07/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

public class StrokeWidthPickerAdapter extends BaseAdapter implements SpinnerAdapter {
    private static LayoutInflater inflater = null;
    public Resources res;
    Integer tempValues = 1;
    StrokeWidthView strokeWidthView;
    private Activity activity;
    private ArrayList<Integer> data;

    public StrokeWidthPickerAdapter(Activity a, ArrayList<Integer> d, Resources resLocal) {
        activity = a;
        data = d;
        res = resLocal;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        if (data.size() <= 0) return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.stroke_picker_spinner_layout, null);
            strokeWidthView = (StrokeWidthView) vi.findViewById(R.id.stroke_width_view);
        }

        if (data.size() > 0) {
            tempValues = data.get(position);
            strokeWidthView.setStrokeWidth(tempValues);
        }

        return vi;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}