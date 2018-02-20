package com.example.jnguyen.slancho.Data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.jnguyen.slancho.Utilities.SlanchoDateUtils;

/**
 * Created by JNguyen on 18.2.2018.
 */

public class OpenWeatherProvider extends ContentProvider {
    OpenWeatherDbHelper mOpenHelper;
    public static final int CODE_WEATHER = 100;
    public static final int CODE_WEATHER_WITH_DATE = 101;
    private static final UriMatcher uriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = OpenWeatherContract.OpenWeatherEntry.CONTENT_AUTHORITY;

        matcher.addURI(authority,OpenWeatherContract.OpenWeatherEntry.PATH_WEATHER,CODE_WEATHER);
        matcher.addURI(authority, OpenWeatherContract.OpenWeatherEntry.PATH_WEATHER + "/#",CODE_WEATHER_WITH_DATE);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new OpenWeatherDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)){

            case CODE_WEATHER_WITH_DATE:
                String normalizedUtcDateString = uri.getLastPathSegment();
                String[] selectiongArguments =  new String[]{normalizedUtcDateString};
                cursor = mOpenHelper.getReadableDatabase()
                        .query(OpenWeatherContract.OpenWeatherEntry.TABLE_NAME,
                                projection,
                                OpenWeatherContract.OpenWeatherEntry.COLUMN_DATE + " = ?",
                                selectionArgs,
                                null,
                                null,
                                sortOrder);
            break;

            case CODE_WEATHER:
                cursor = mOpenHelper.getReadableDatabase()
                        .query(OpenWeatherContract.OpenWeatherEntry.TABLE_NAME,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null,
                                sortOrder);
            break;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case CODE_WEATHER:
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values) {
                        long weatherDate = value.getAsLong(OpenWeatherContract.OpenWeatherEntry.COLUMN_DATE);
                        if (!SlanchoDateUtils.isDateNormalized(weatherDate)) {
                            throw new IllegalArgumentException("Date must be normalized to insert!");
                        }
                        long _id = db.insert(OpenWeatherContract.OpenWeatherEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;
            default:
                return super.bulkInsert(uri, values);
        }

    }
}
