package com.example.alex_lenovi.spikenlosmanuals;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by alex-lenovi on 7/29/2016.
 */
public class StrokeWidthView extends View {
    int line_width = 1;
    int stroke_color = Color.BLACK;
    Paint p;

    public StrokeWidthView(Context context) {
        super(context);

        init();
    }

    public StrokeWidthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StrokeWidthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public StrokeWidthView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        //canvas.drawColor(Color.BLACK);
        p = new Paint();
        p.setAntiAlias(true);
        p.setColor(stroke_color);
        p.setStrokeWidth(line_width);
        p.setAlpha(0x80);
    }

    public int getStrokeWidth() {
        return line_width;
    }

    public void setStrokeWidth(int line_width) {
        this.line_width = line_width;
        invalidate();
    }

    public int getStroke_color() {
        return stroke_color;
    }

    public void setStroke_color(int stroke_color) {
        this.stroke_color = stroke_color;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        p.setStrokeWidth(line_width);
        p.setColor(stroke_color);
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, p);
    }

}
