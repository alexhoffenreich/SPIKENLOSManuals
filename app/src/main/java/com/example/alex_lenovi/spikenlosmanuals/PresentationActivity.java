package com.example.alex_lenovi.spikenlosmanuals;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.IOException;

public class PresentationActivity extends AppCompatActivity {
    int cur_slide_num;
    int slides_count;
    String presentation_folder_name;
    EditText slide_number_text;
    ImageView slide_img_view;
    EditText remarks_edit_view;
    Toolbar app_bar;
    DrawingView mDrawingView;
    ColorPickerSpinner color_picker;
    StrokeWidthPickerSpinner stroke_width_picker;
    UserInfoDBHelper user_info_db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        slide_img_view = (ImageView) findViewById(R.id.slide_img_view);
        remarks_edit_view = (EditText) findViewById(R.id.remarks_edit_view);

        cur_slide_num = 0;

        app_bar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(app_bar);

        mDrawingView = new DrawingView(this);
        final RelativeLayout mDrawingPad = (RelativeLayout) findViewById(R.id.slide_drawing_pad);
        if (mDrawingPad != null) {
            mDrawingPad.addView(mDrawingView);
        }

        presentation_folder_name = getIntent().getExtras().getString("presentation_folder_name");

        user_info_db = new UserInfoDBHelper(this);

        setTitle(getIntent().getExtras().getString("presentation_title"));

        color_picker = (ColorPickerSpinner) findViewById(R.id.color_picker);
        color_picker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDrawingView.setScratchColor(color_picker.getSelectedColor());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDrawingView.setScratchColor(color_picker.getColorByPosition(0));
            }
        });

        stroke_width_picker = (StrokeWidthPickerSpinner) findViewById(R.id.stroke_width_picker);
        stroke_width_picker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDrawingView.setStrokeWidth(stroke_width_picker.getSelectedStroke());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDrawingView.setStrokeWidth(stroke_width_picker.getStrokeByPosition(0));
            }
        });

        slide_number_text = (EditText) findViewById(R.id.slide_num_text);
        slide_number_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int i = 0;
                try {
                    i = Long.valueOf(s.toString()).intValue();
                    if (i != cur_slide_num && i > 0) showSlide(i);
                } catch (Exception e) {
                    Log.e("Slide", e.getMessage());
                }


            }


        });

        try {
            slides_count = getAssets().list("presentations/" + presentation_folder_name).length;
        } catch (IOException e) {
            e.printStackTrace();
        }

        mDrawingView.setOnDrawingViewReady(new OnDrawingViewReady() {
            @Override
            public void onReady(DrawingView drawingView) {
                showSlide(1);
                //mDrawingPad.invalidate();
            }
        });

        //showSlide(1);
        //getCurSlideRemarks();

    }

    private void showSlide(int i) {
        try {
            if ((i >= 1) && (i <= slides_count)) {
                slide_img_view.setImageDrawable(Drawable.createFromStream(getAssets().open("presentations/" + presentation_folder_name + "/Slide" + i + ".JPG"), null));
                cur_slide_num = i;
                slide_number_text.setText(String.valueOf(cur_slide_num));
                getCurSlideRemarks();
            }

        } catch (Exception e) {
            Log.e("Slide", e.getMessage());
        }
    }

    private void getCurSlideRemarks() {
        Cursor cur = user_info_db.getSlidesRemarks(presentation_folder_name, cur_slide_num);
        if (cur == null || cur.getCount() == 0) {
            remarks_edit_view.setText("");
            //mDrawingView.cleanCanvas();
        } else {
            cur.moveToFirst();
            String content = cur.getString(cur.getColumnIndex("content"));
            String scratches = cur.getString(cur.getColumnIndex("scratch"));
            remarks_edit_view.setText(content);
            mDrawingView.restoreCanvasFromString(scratches);
            //mDrawingView.invalidate();
        }
    }

    private void saveCurSlideRemarks() {
        String content = remarks_edit_view.getText().toString();
        String scratch = mDrawingView.getPathString();
        String presentation_id = presentation_folder_name;
        user_info_db.setSlideRemark(presentation_id, cur_slide_num, content, scratch);
    }


    public void onPreviousSlideButtonClick(View view) {
        slide_number_text.clearFocus();
        view.requestFocus();
        saveCurSlideRemarks();
        showSlide(cur_slide_num - 1);
    }

    public void onNextSlideButtonClick(View view) {
        slide_number_text.clearFocus();
        view.requestFocus();
        saveCurSlideRemarks();
        showSlide(cur_slide_num + 1);
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveCurSlideRemarks();
    }

    public void onClearScratchButtonClick(View view) {
        mDrawingView.cleanCanvas();
    }
}
