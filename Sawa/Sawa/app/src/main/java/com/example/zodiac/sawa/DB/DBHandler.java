package com.example.zodiac.sawa.DB;

import android.content.Context;
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
        + USER_STATUS + " TEXT," + USER_SONG +")";
        sqLiteDatabase.execSQL(CREATE_ABOUT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ABOUT);
// Creating tables again
        onCreate(sqLiteDatabase);
    }
}
