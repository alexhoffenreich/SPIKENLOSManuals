package com.example.alex_lenovi.spikenlosmanuals;

import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by alex-lenovi on 7/30/2016.
 */
public class MyPath {
    public Path path;
    public Paint paint;

    public MyPath(Path path, Paint paint) {
        this.path = new Path(path);
        this.paint = new Paint(paint);
    }

}
