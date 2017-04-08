package com.example.zodiac.sawa.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rabee on 4/5/2017.
 */

public class DBHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "sawa";
    // Contacts table name
    private static final String TABLE_ABOUT = "about";
    // about Table Columns names
    private static final String USER_ID = "user_id";
    private static final String USER_BIO = "user_bio";
    private static final String USER_STATUS = "user_status";
    private static final String USER_SONG = "user_song";


    public DBHandler(Context context) {
        super(context, "database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_ABOUT_TABLE = "CREATE TABLE " + TABLE_ABOUT + "("
                + USER_ID + " INTEGER PRIMARY KEY," + USER_BIO + " TEXT,"
                + USER_STATUS + " TEXT," + USER_SONG + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_ABOUT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ABOUT);
// Creating tables again
        onCreate(sqLiteDatabase);
    }

    public void addAboutUser(AboutUser aboutUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, aboutUser.getUser_id()); // Shop Name
        values.put(USER_BIO, aboutUser.getUser_bio());
        values.put(USER_STATUS, aboutUser.getUser_status());
        values.put(USER_SONG, aboutUser.getUser_song());
        // Inserting Row
        db.insert(TABLE_ABOUT, null, values);
        db.close(); // Closing database connection
    }

    public AboutUser getAboutUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ABOUT, new String[]{USER_ID,
                        USER_BIO, USER_STATUS, USER_SONG}, USER_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null&& cursor.moveToFirst()){
            cursor.moveToFirst();
        AboutUser aboutUser = new AboutUser(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
            return aboutUser;

        }else return null;
// return shop
    }
}
