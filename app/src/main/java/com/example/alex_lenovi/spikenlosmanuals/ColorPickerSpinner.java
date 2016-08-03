package com.example.alex_lenovi.spikenlosmanuals;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by alex-lenovi on 7/30/2016.
 */
public class ColorPickerSpinner extends Spinner {

    private ArrayList<ColorItem> colors_list;
    private ColorPickerAdapter color_picker_adapter;

    public ColorPickerSpinner(Context context) {
        super(context);
        init(context);
    }

    public ColorPickerSpinner(Context context, int mode) {
        super(context, mode);
        init(context);
    }

    public ColorPickerSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ColorPickerSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ColorPickerSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
        init(context);
    }

    public ColorPickerSpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int mode) {
        super(context, attrs, defStyleAttr, defStyleRes, mode);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public ColorPickerSpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, defStyleRes, mode, popupTheme);
        init(context);
    }

    private void init(Context context) {
        colors_list = new ArrayList<>();
        colors_list.add(new ColorItem("Red", Color.RED));
        colors_list.add(new ColorItem("Green", Color.GREEN));
        colors_list.add(new ColorItem("Green", Color.BLUE));
        colors_list.add(new ColorItem("Green", Color.CYAN));
        colors_list.add(new ColorItem("Green", Color.YELLOW));
        colors_list.add(new ColorItem("Green", Color.MAGENTA));
        colors_list.add(new ColorItem("Green", Color.GRAY));
        colors_list.add(new ColorItem("Green", Color.BLACK));
        colors_list.add(new ColorItem("Green", Color.WHITE));


        color_picker_adapter = new ColorPickerAdapter((Activity) context, colors_list, context.getResources());
        this.setAdapter(color_picker_adapter);
    }

    public int getColorByPosition(int position) {
        return colors_list.get(position).value;
    }

    public int getSelectedColor() {
        return getColorByPosition(getSelectedItemPosition());
    }

}
