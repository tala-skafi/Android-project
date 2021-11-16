package com.example.myapplication.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.myapplication.Model.WeatherProfile;

import java.util.ArrayList;

public class SqLiteManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "weather_db";
    private static final String TABLE_WEATHER = "weather_table";

    public static final String KEY_ID = "id";
    public static final String KEY_PROFILE_NAME = "profile_name";
    public static final String KEY_CITY = "city";
    public static final String KEY_IS_DEFAULT = "is_default";
    public static final String KEY_API_KEY = "api_key";
    public static final String KEY_UNIT = "unit";

    private static SqLiteManager instance;

    private SqLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SqLiteManager getInstance(Context context) {
        if (instance == null)
            instance = new SqLiteManager(context);

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_WEATHER + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_PROFILE_NAME + " TEXT UNIQUE,"
                + KEY_CITY + " TEXT,"
                + KEY_API_KEY + " TEXT,"
                + KEY_UNIT + " TEXT,"
                + KEY_IS_DEFAULT + " INTEGER DEFAULT 0"
                + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER);
        onCreate(sqLiteDatabase);
    }

    public int insertEntry(String pName, String cName, String apiKey, String unit, boolean isDefault) {

        ContentValues values = new ContentValues();
        values.put(KEY_PROFILE_NAME, pName);
        values.put(KEY_CITY, cName);
        values.put(KEY_API_KEY, apiKey);
        values.put(KEY_UNIT, unit);
        values.put(KEY_IS_DEFAULT, isDefault ? 1 : 0);

        return (int) getWritableDatabase().insert(TABLE_WEATHER, null, values);
    }

    public ArrayList<WeatherProfile> getAllEntries() {
        Cursor cursor = getReadableDatabase().rawQuery("select * from " + TABLE_WEATHER, null);

        ArrayList<WeatherProfile> profiles = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                String pName = cursor.getString(cursor.getColumnIndex(KEY_PROFILE_NAME));
                String cName = cursor.getString(cursor.getColumnIndex(KEY_CITY));
                String apiKey = cursor.getString(cursor.getColumnIndex(KEY_API_KEY));
                String unit = cursor.getString(cursor.getColumnIndex(KEY_UNIT));
                boolean isDefault = cursor.getInt(cursor.getColumnIndex(KEY_IS_DEFAULT)) == 1;

                WeatherProfile profile = new WeatherProfile(id, pName, cName, apiKey,unit, isDefault);

                profiles.add(profile);
                cursor.moveToNext();
            }
        }

        cursor.close();
        return profiles;
    }

    public WeatherProfile getEntryByKey(String Key, String val) {
        Cursor cursor = getReadableDatabase().rawQuery("select * from " + TABLE_WEATHER + " WHERE " + Key + " = " + val, null);


        WeatherProfile profile = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            String pName = cursor.getString(cursor.getColumnIndex(KEY_PROFILE_NAME));
            String cName = cursor.getString(cursor.getColumnIndex(KEY_CITY));
            String apiKey = cursor.getString(cursor.getColumnIndex(KEY_API_KEY));
            String unit = cursor.getString(cursor.getColumnIndex(KEY_UNIT));
            boolean isDefault = cursor.getInt(cursor.getColumnIndex(KEY_IS_DEFAULT)) == 1;

            profile = new WeatherProfile(id, pName, cName, apiKey,unit, isDefault);
        }

        cursor.close();
        return profile;
    }

    public void editEntry(int id, String pName, String cName, String apiKey, String unit, Boolean isDefault) {

        ContentValues values = new ContentValues();
        if (pName != null)
            values.put(KEY_PROFILE_NAME, pName);
        if (cName != null)
            values.put(KEY_CITY, cName);
        if (apiKey != null)
            values.put(KEY_API_KEY, apiKey);
        if (unit != null)
            values.put(KEY_UNIT, unit);
        if (isDefault != null)
            values.put(KEY_IS_DEFAULT, isDefault ? 1 : 0);

        getWritableDatabase().update(TABLE_WEATHER, values, KEY_ID + " = " + id, null);
    }

    public WeatherProfile getDefaultProfile() {
        return getEntryByKey(KEY_IS_DEFAULT, String.valueOf(1));
    }
    public void removePrevDefault() {
        WeatherProfile prevDefaultProfile = getDefaultProfile();
        if (prevDefaultProfile != null) {
            editEntry(
                    prevDefaultProfile.getId(),
                    null,
                    null,
                    null,
                    null,
                    false
            );
        }
    }
}
