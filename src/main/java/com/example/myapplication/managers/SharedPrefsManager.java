package com.example.myapplication.managers;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefsManager {

    private final static String SHARED_PREFS_NAME = "WEATHER_SHARED_PREFS";
    private final static String KEY_DEFAULT_PROFILE = "DEFAULT_PROFILE";

    private static SharedPrefsManager instance;
    private SharedPreferences sharedPreferences;

    private SharedPrefsManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME,
                Context.MODE_PRIVATE);
    }

    public static SharedPrefsManager getInstance(Context context) {
        if(instance == null)
            instance = new SharedPrefsManager(context);

        return instance;
    }

    public String getDefaultProfile() {
        return sharedPreferences.getString(KEY_DEFAULT_PROFILE, "");
    }

    public void setDefaultProfile(String profile) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_DEFAULT_PROFILE, profile);
        editor.apply();
    }
}
