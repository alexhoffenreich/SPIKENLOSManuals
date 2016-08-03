package com.example.alex_lenovi.spikenlosmanuals;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by alex-lenovi on 7/23/2016.
 */
public class UserInfoDBHelper extends SQLiteOpenHelper {
    private final static String db_name = "user_info.db";
    private Context context;

    public UserInfoDBHelper(Context context) {
        // 2 - remove no null from id

        super(context, db_name, null, 5);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE presentation_remarks (\n" +
                "    _id              INTEGER  PRIMARY KEY ASC AUTOINCREMENT,\n" +
                "    user_name        STRING,\n" +
                "    create_time      DATETIME DEFAULT Now,\n" +
                "    last_update_time DATETIME DEFAULT Now,\n" +
                "    presentation_id  STRING,\n" +
                "    slide_num        INTEGER,\n" +
                "    content          VARCHAR,\n" +
                "    scratch          VARCHAR\n" +
                ");");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE presentation_remarks RENAME TO presentation_remarks_OLD_V" + oldVersion + "_" + getTimeStamp());

        onCreate(db);
    }


    public boolean setSlideRemark(String presentation_id, int slide_number, String content, String scratch) {
        int result;
        //String remark_type = "text";
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("presentation_id", presentation_id);
        values.put("slide_num", slide_number);
        values.put("scratch", scratch);
        values.put("content", content);
        Cursor cur = getSlidesRemarks(presentation_id, slide_number);
        int cur_count = cur.getCount();

        if (cur_count == 0) {
            result = (int) database.insert("presentation_remarks", null, values);
        } else {
            cur.moveToFirst();
            int cur_id = cur.getInt(cur.getColumnIndex("_id"));
            result = database.update("presentation_remarks", values, "_id=\"" + cur_id + "\"", null);
        }

        return result != -1;

    }


    public Cursor getSlidesRemarks(String presentation_id, int slide_number) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] cols = new String[]{"_id", "presentation_id", "slide_num", "content", "scratch"};
        Cursor mCursor = database.query(false, "presentation_remarks", cols,
                "presentation_id=\"" + presentation_id + "\" AND " +
                        "slide_num=\"" + slide_number + "\""
                , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor; // iterate to get each value.
    }

    private JSONArray getJSONArray(String myTable) {
        SQLiteDatabase database = getReadableDatabase();

        String searchQuery = "SELECT  * FROM " + myTable;
        Cursor cursor = database.rawQuery(searchQuery, null);

        JSONArray resultSet = new JSONArray();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        return resultSet;
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "SPIKE NLOS");
        if (!file.mkdirs()) {
            Log.e("", "Directory not created");
        }
        return file;
    }

    private String writeTableDataToTempFile(String table_name) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String file_name = table_name + "_" + getTimeStamp() + ".dat";
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Trainee_Package");
            if (file.mkdirs()) {
                try {
                    file = new File(file.getPath(), file_name);
                    if (file.createNewFile()) {
                        OutputStream output_stream = new FileOutputStream(file);
                        String out_string = getJSONArray(table_name).toString();
                        output_stream.write(out_string.getBytes());
                        output_stream.close();
                        return file_name;
                    }

                } catch (Exception e) {
                    Toast.makeText(context, "Error: Can't create file " + file_name, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(context, "Error: Can't create directory " + file.getPath(), Toast.LENGTH_LONG).show();
            }
        }
        return "";

            /*File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), file_name);
            OutputStream output_stream = new FileOutputStream(file);
            output_stream.write(json_array.toString().getBytes());
            output_stream.close();*/
/*
        if (isExternalStorageWritable()) {
            try {
                File file = new File(android.os.Environment.getExternalStorageDirectory(), "DBExports/" + file_name);
                if (!file.exists()) {
                    if (file.mkdirs())
                        file.createNewFile();
                }

                FileOutputStream fOut = new FileOutputStream(file);
                OutputStreamWriter osw = new OutputStreamWriter(fOut);
                String out_string = getJSONArray(table_name).toString();
                osw.write(out_string);
                osw.flush();
                osw.close();

                return file_name;
            } catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }
        }
*/


    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public void exportDBToEMail() {


        String filename = writeTableDataToTempFile("presentation_remarks");

        if (!filename.isEmpty()) {

            Uri path = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DBExports", filename));
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("vnd.android.cursor.dir/email");
            String to[] = {"alex.hoffenreich@gmail.com"};
            emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
            emailIntent.putExtra(Intent.EXTRA_STREAM, path);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            context.startActivity(Intent.createChooser(emailIntent, "Send email..."));

        }


    }

    private String getTimeStamp() {
        Date cur_date = new Date(System.currentTimeMillis());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss", Locale.US);
        return dateFormat.format(cur_date);
    }
}
