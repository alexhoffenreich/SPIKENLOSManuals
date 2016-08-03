package com.example.alex_lenovi.spikenlosmanuals;

/**
 * Created by ADSL on 21/07/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/*********
 * Adapter class extends with BaseAdapter and implements with OnClickListener
 ************/
public class TOCAdapter extends BaseAdapter implements View.OnClickListener {

    private static LayoutInflater inflater = null;
    public Resources res;
    TOCItem tempValues = null;
    int i = 0;
    /***********
     * Declare Used Variables
     *********/
    private Activity activity;
    private ArrayList<TOCItem> data;

    /*************
     * CustomAdapter Constructor
     *****************/
    public TOCAdapter(Activity a, ArrayList<TOCItem> d, Resources resLocal) {

        /********** Take passed values **********/
        activity = a;
        data = d;
        res = resLocal;

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
            vi = inflater.inflate(R.layout.toc_item_layout, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.title_text = (TextView) vi.findViewById(R.id.toc_item_title);

            /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (data.size() <= 0) {
            holder.title_text.setText("No Title");
        } else {
            /***** Get each Model object from Arraylist ********/
            tempValues = data.get(position);

            /************  Set Model values in Holder elements ***********/
            holder.title_text.setText(tempValues.getTitle());
            holder.title_text.setPadding(tempValues.getLevel() * 15 + 10, 8, 8, 8);
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
        //public TextView description_text;
        //public ImageView image;

    }

}