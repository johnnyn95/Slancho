package com.example.jnguyen.slancho.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JNguyen on 16.2.2018.
 */

public class OpenWeatherDbHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "openWeather.db";
    private static final int DATABASE_VERSION = 1;

    public OpenWeatherDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_WEATHER_TABLE =

                "CREATE TABLE " + OpenWeatherContract.OpenWeatherEntry.TABLE_NAME + " (" +
                OpenWeatherContract.OpenWeatherEntry._ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                OpenWeatherContract.OpenWeatherEntry.COLUMN_DATE + " INTEGER,NOT NULL "  +
                OpenWeatherContract.OpenWeatherEntry.COLUMN_WEATHER_ID + " INTEGER,NOT NULL "  +
                OpenWeatherContract.OpenWeatherEntry.COLUMN_MIN_TEMP + " REAL,NOT NULL " +
                OpenWeatherContract.OpenWeatherEntry.COLUMN_MAX_TEMP + " REAL,NOT NULL " +
                OpenWeatherContract.OpenWeatherEntry.COLUMN_HUMIDITY + " REAL,NOT NULL " +
                OpenWeatherContract.OpenWeatherEntry.COLUMN_PRESSURE + " REAL,NOT NULL " +
                OpenWeatherContract.OpenWeatherEntry.COLUMN_WIND_SPEED + " REAL,NOT NULL " +
                OpenWeatherContract.OpenWeatherEntry.COLUMN_DEGREES + " REAL,NOT NULL" +
                "UNIQUE (" + OpenWeatherContract.OpenWeatherEntry.COLUMN_DATE + ") ON CONFLICT REPLACE);";

        db.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + OpenWeatherContract.OpenWeatherEntry.TABLE_NAME);
        onCreate(db);
    }
}
