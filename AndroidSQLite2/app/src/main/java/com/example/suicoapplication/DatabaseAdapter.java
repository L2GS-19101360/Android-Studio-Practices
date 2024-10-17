package com.example.suicoapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class DatabaseAdapter {

    myDBHelper myHelper;

    public DatabaseAdapter(Context context) {
        myHelper = new myDBHelper(context);
    }

    public long insertData(String username, String password) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDBHelper.NAME, username);
        contentValues.put(myDBHelper.PASSWORD, password);
        long id = db.insert(myDBHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public String getData() {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        String[] columns = {myDBHelper.UID, myDBHelper.NAME, myDBHelper.PASSWORD};
        Cursor cursor = db.query(myDBHelper.TABLE_NAME, columns, null, null, null, null, null);
        StringBuffer stringBuffer = new StringBuffer();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int cid = cursor.getInt(cursor.getColumnIndex(myDBHelper.UID));
            @SuppressLint("Range") String cusername = cursor.getString(cursor.getColumnIndex(myDBHelper.NAME));
            @SuppressLint("Range") String cpassword = cursor.getString(cursor.getColumnIndex(myDBHelper.PASSWORD));
            stringBuffer.append(cid + "" + cusername + "" + cpassword + "\n");
        }
        return stringBuffer.toString();
    }

    static class myDBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "mydb";
        private static final String TABLE_NAME = "mytable";
        private static final int DATABASE_VERSION = 1;
        private static final String UID = "_id";
        private static final String NAME = "Usernames";
        private static final String PASSWORD = "Passwords";

        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " VARCHAR(255), " +
                PASSWORD + " VARCHAR(255)" + ");";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        private Context context;

        public myDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Toast.makeText(context.getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                sqLiteDatabase.execSQL(DROP_TABLE);
                onCreate(sqLiteDatabase);
            } catch (Exception e) {
                Toast.makeText(context.getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
