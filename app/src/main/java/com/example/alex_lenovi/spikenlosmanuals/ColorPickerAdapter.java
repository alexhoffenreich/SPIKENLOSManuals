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
import android.widget.ImageView;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

public class ColorPickerAdapter extends BaseAdapter implements SpinnerAdapter {
    private static LayoutInflater inflater = null;
    public Resources res;
    ColorItem tempValues = null;
    private Activity activity;
    private ArrayList data;

    public ColorPickerAdapter(Activity a, ArrayList d, Resources resLocal) {
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
        ViewHolder holder;

        if (convertView == null) {
            vi = inflater.inflate(R.layout.color_picker_spinner_layout, null);
            holder = new ViewHolder();
            holder.color_image_view = (ImageView) vi.findViewById(R.id.color_image_view);

            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (data.size() > 0) {
            tempValues = (ColorItem) data.get(position);
            holder.color_image_view.setBackgroundColor(tempValues.value);
        }
        return vi;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    public static class ViewHolder {
        public ImageView color_image_view;
    }
}