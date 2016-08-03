package com.example.alex_lenovi.spikenlosmanuals;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by alex-lenovi on 7/30/2016.
 */
public class StrokeWidthPickerSpinner extends Spinner {

    ArrayList<Integer> stroke_widths_list;
    StrokeWidthPickerAdapter stroke_width_spinner_adapter;

    public StrokeWidthPickerSpinner(Context context) {
        super(context);
        init(context);
    }

    public StrokeWidthPickerSpinner(Context context, int mode) {
        super(context, mode);
        init(context);
    }

    public StrokeWidthPickerSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StrokeWidthPickerSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public StrokeWidthPickerSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
        init(context);
    }

    public StrokeWidthPickerSpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int mode) {
        super(context, attrs, defStyleAttr, defStyleRes, mode);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public StrokeWidthPickerSpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, defStyleRes, mode, popupTheme);
        init(context);
    }

    private void init(Context context) {
        stroke_widths_list = new ArrayList<>();
        stroke_widths_list.add(20);
        stroke_widths_list.add(16);
        stroke_widths_list.add(12);
        stroke_widths_list.add(8);
        stroke_widths_list.add(4);
        stroke_widths_list.add(2);

        stroke_width_spinner_adapter = new StrokeWidthPickerAdapter((Activity) context, stroke_widths_list, getResources());
        setAdapter(stroke_width_spinner_adapter);
    }

    public int getStrokeByPosition(int position) {
        return stroke_widths_list.get(position);
    }

    public int getSelectedStroke() {
        return getStrokeByPosition(getSelectedItemPosition());
    }

}
