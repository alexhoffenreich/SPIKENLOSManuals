package com.example.alex_lenovi.spikenlosmanuals;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/*********
 * Adapter class extends with BaseAdapter and implements with OnClickListener
 ************/
public class ThumbAdapter extends BaseAdapter implements View.OnClickListener {

    private static LayoutInflater inflater = null;
    public Resources res;
    ThumbModel tempValues = null;
    int i = 0;
    int default_image_resource_id;
    /***********
     * Declare Used Variables
     *********/
    private Activity activity;
    private ArrayList data;


    /*************
     * CustomAdapter Constructor
     *****************/
    public ThumbAdapter(Activity a, ArrayList d, Resources resLocal, int default_image_resource_id) {

        /********** Take passed values **********/
        activity = a;
        data = d;
        res = resLocal;
        this.default_image_resource_id = default_image_resource_id;
        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /********
     * What is the size of Passed Arraylist Size
     ************/
    public int getCount() {

        if (data.size() <= 0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /******
     * Depends upon data size called for each row , Create each ListView row
     *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.list_thumb_layout, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.title_text = (TextView) vi.findViewById(R.id.thumb_title);
            holder.description_text = (TextView) vi.findViewById(R.id.thumb_description);
            holder.image = (ImageView) vi.findViewById(R.id.thumb_img);

            /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (data.size() <= 0) {
            holder.title_text.setText("No Title");
            holder.description_text.setText("-");


        } else {
            /***** Get each Model object from Arraylist ********/
            tempValues = null;
            tempValues = (ThumbModel) data.get(position);

            /************  Set Model values in Holder elements ***********/

            holder.title_text.setText(tempValues.getTitle());
            holder.description_text.setText(tempValues.getDescription());
            holder.image.setImageDrawable(res.getDrawable(default_image_resource_id));
/*
            try {

                InputStream ims = res.getAssets().open("thumbs/" + tempValues.getFile_name());
                Drawable d = Drawable.createFromStream(ims, null);
                holder.image.setImageDrawable(d);
            } catch (IOException ex) {

            }
*/


        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    /*********
     * Create a holder Class to contain inflated xml file elements
     *********/
    public static class ViewHolder {

        public TextView title_text;
        public TextView description_text;
        public ImageView image;

    }

}