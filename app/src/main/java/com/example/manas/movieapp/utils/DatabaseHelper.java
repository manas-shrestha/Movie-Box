package com.example.manas.movieapp.utils;

/**
 * Created by Manas on 3/27/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 3/27/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    Context context;
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Movie";

    /**
     * *database table ****
     */
    private static final String TABLE_NAME = "tbl_movie";
    private static final String CREATE_TABLE_MOVIE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,movieName VARCHAR(255),status INTEGER DEFAULT 0)";
    private static final String DROP_TABLE_MOVIE = "DROP TABLE IF EXISTS tbl_movie";
    private static final String COLUMN_MOVIE_NAME = "movieName";
    private static final String COLUMN_STATUS = "status";


    SQLiteDatabase sqLiteDatabase;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;


    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_TABLE_MOVIE);
    }

    /**
     * This method inserts movie name.
     *
     * @param movieName
     * @return void
     * **
     */

    public void insertMovieName(String[] movieName) {
        ContentValues contentValues;
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int i;
        for (i = 0; i < movieName.length; i++) {
            contentValues = new ContentValues();
            contentValues.put(COLUMN_MOVIE_NAME, movieName[i]);
            sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        }

    }


    /**
     * This method update the movie status.
     *
     * @param movieName
     * @param status
     */
    public void updateMovieStatus(String movieName, int status) {

        sqLiteDatabase = getWritableDatabase();
        try {
            getWritableDatabase().execSQL("UPDATE tbl_movie SET status=" + status + " WHERE movieName='" + movieName + "'");
        } finally {
            //sqLiteDatabase.close();
        }
    }


    /**
     * @param movieName
     * @return status of particular movie.
     */
    public int getMovieStatus(String movieName) {
        Cursor cursor;
        int status = 0;
        sqLiteDatabase = getReadableDatabase();

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM tbl_movie where movieName='" + movieName + "'", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {

                status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS));

            }
        }
        return status;

    }

}
