package com.example.zodiac.sawa.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.zodiac.sawa.models.AboutUser;

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
    private static final String TABLE_IMAGES = "images";
    // about Table Columns names
    private static final String USER_ID = "user_id";
    private static final String USER_BIO = "user_bio";
    private static final String USER_STATUS = "user_status";
    private static final String USER_SONG = "user_song";

    private static final String USER_IMAGE = "user_image";

    public DBHandler(Context context) {
        super(context, "database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_IMAGES_TABLE = "CREATE TABLE " + TABLE_IMAGES + "("
                + USER_ID + " INTEGER PRIMARY KEY," + USER_IMAGE + " BLOB" + ")";
        sqLiteDatabase.execSQL(CREATE_IMAGES_TABLE);

        String CREATE_ABOUT_TABLE = "CREATE TABLE " + TABLE_ABOUT + "("
                + USER_ID + " INTEGER PRIMARY KEY," + USER_BIO + " TEXT,"
                + USER_STATUS + " TEXT," + USER_SONG + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_ABOUT_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ABOUT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);


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
        if (cursor != null && cursor.moveToFirst()) {
            Log.d("user found","ss");
            cursor.moveToFirst();
            AboutUser aboutUser = new AboutUser(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2), cursor.getString(3));
            db.close();
            return aboutUser;

        } else return null;
// return shop
    }

    public void updateAboutSqlite(String bioText, String stausText, String songText) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(USER_BIO, bioText);
        cv.put(USER_STATUS, stausText);
        cv.put(USER_SONG, songText);
        db.update(TABLE_ABOUT, cv, "USER_ID" + "= ?", new String[]{String.valueOf(1)});
        db.close();

    }

    public void insertUserImage(int ID, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_ID, ID);
        cv.put(USER_IMAGE, image);
        db.insert(TABLE_IMAGES, null, cv);
        db.close();
    }

    public void updateUserImage(int ID, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_IMAGE, image);
        db.update(TABLE_IMAGES, cv, "USER_ID" + "= ?", new String[]{String.valueOf(1)});
        Log.d("Updated","s");
        db.close();
    }

    public Bitmap getUserImage(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_IMAGES+ " WHERE " + USER_ID + " = " + id;
        Log.d("database", selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
                byte[] image = cursor.getBlob(cursor.getColumnIndex(USER_IMAGE));


            Bitmap img = BitmapFactory.decodeByteArray(image, 0, image.length);
            db.close();
            return img;


        } else return null;

    }
}

