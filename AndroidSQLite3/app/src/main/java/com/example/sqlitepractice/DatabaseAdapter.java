package com.example.sqlitepractice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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

    public Cursor getData() {
        SQLiteDatabase db = myHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + myDBHelper.TABLE_NAME, null);
    }

    public long deleteData(int row_id) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        ContentValues cv1 = new ContentValues();

        long id = db.delete(myDBHelper.TABLE_NAME, myDBHelper.UID + "=?", new String[]{String.valueOf(row_id)});
        return id;
    }

    public long updateData(int row_id, String updateUsername, String updatePassword) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(myDBHelper.NAME, updateUsername);
        cv.put(myDBHelper.PASSWORD, updatePassword);

        long id = db.update(myDBHelper.TABLE_NAME, cv, myDBHelper.UID + "=?", new String[]{String.valueOf(row_id)});
        return id;
    }

    static class myDBHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "myDB.db";
        private static final String TABLE_NAME = "myUserTable";
        private static final int DATABASE_VERSION = 1;
        private static final String UID = "_id";
        private static final String NAME = "Usernames";
        private static final String PASSWORD = "Passwords";

        private Context context;

        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " VARCHAR(255), " +
                PASSWORD + " VARCHAR(255)" + ");";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public myDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
