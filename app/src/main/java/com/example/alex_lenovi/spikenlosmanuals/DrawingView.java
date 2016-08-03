package com.example.alex_lenovi.spikenlosmanuals;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;

import java.util.Locale;

/**
 * Created by alex-lenovi on 7/26/2016.
 */
public class DrawingView extends View {
    private static final float TOUCH_TOLERANCE = 4;
    //ArrayList<MyPath> paths;
    Paint mPaint;
    //MaskFilter  mEmboss;
    //MaskFilter  mBlur;
    Bitmap mBitmap;
    Canvas mCanvas;
    Path mPath;
    Paint mBitmapPaint;
    Context context;
    StringBuilder cur_paths;
    int cur_color;
    private int cur_stroke_with;
    private boolean isReady = false;
    private OnDrawingViewReady on_drawing_view_ready;
    private float mX, mY;

    public DrawingView(Context context) {
        super(context);
        //paths = new ArrayList<>();

        this.context = context;
        // TODO Auto-generated constructor stub
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFFFF0000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(20);
        mPath = new Path();
        mBitmapPaint = new Paint();
        mBitmapPaint.setColor(Color.RED);

        cur_paths = new StringBuilder();

    }

    public void setOnDrawingViewReady(OnDrawingViewReady on_drawing_view_ready) {
        this.on_drawing_view_ready = on_drawing_view_ready;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        if (!isReady) {
            on_drawing_view_ready.onReady(this);
            isReady = true;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.draw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.drawPath(mPath, mPaint);

    }

    private String getCoordString(float v) {
        return String.format(Locale.ENGLISH, "%.1f", v);
    }

    private void touch_start(float x, float y) {
        //mPath.reset();
        mPaint.setColor(cur_color);
        mPaint.setStrokeWidth(cur_stroke_with);
        cur_paths.append("P" + cur_color + "," + cur_stroke_with + "/" + getCoordString(x) + "," + getCoordString(y));
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        cur_paths.append("M" + getCoordString(x) + "," + getCoordString(y));
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touch_up() {

        mPath.lineTo(mX, mY);
        //paths.add(new MyPath(mPath,mPaint));
        mCanvas.drawPath(mPath, mPaint);
        mPath.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }

    public String getPathString() {
        String out = cur_paths.toString();
        cur_paths = new StringBuilder();
        return out;
    }


    public void cleanCanvas() {
        cur_paths = new StringBuilder();
        //paths = new ArrayList<>();

        if (mCanvas != null) mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    public void restoreCanvasFromString(String path_string) {
        int cur_color = this.cur_color;
        int cur_stroke_width = this.cur_stroke_with;
/*        if (mCanvas == null)
        {
            invalidate();
        }*/

        cleanCanvas();
        String paths_array[] = path_string.split("P");
        for (String p : paths_array) {
            if (!p.trim().isEmpty()) {
                String path[] = p.split("/");
                String path_apperance[] = path[0].split(",");
                setScratchColor(Integer.parseInt(path_apperance[0]));
                setStrokeWidth(Integer.parseInt(path_apperance[1]));
                String path_coords[] = path[1].split("M");

                Coord coord = new Coord(path_coords[0]);
                if (path_coords.length > 0) {
                    touch_start(coord.x, coord.y);
                    for (int i = 1; i < path_coords.length; i++) {
                        coord = new Coord(path_coords[i]);
                        touch_move(coord.x, coord.y);
                    }
                    touch_up();
                }
            }

            invalidate();
        }

        setStrokeWidth(cur_stroke_width);
        setScratchColor(cur_color);
    }

    public void setScratchColor(int value) {
        cur_color = value;
    }

    public void setStrokeWidth(int value) {
        cur_stroke_with = value;

    }

    private class Coord {
        public float x, y;

        public Coord(String s) {
            String c[] = s.split(",");
            x = Float.valueOf(c[0]);
            y = Float.valueOf(c[1]);
        }
    }
}

