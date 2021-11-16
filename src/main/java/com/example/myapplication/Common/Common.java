package com.example.myapplication.Common;

import com.example.myapplication.Model.Weather;
import com.example.myapplication.Model.WeatherProfile;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    public static WeatherProfile defaultProfile = null;

    public static void updateInMemoryProfile(
            int id,
            String name,
            String city,
            String apiKey,
            String unit,
            boolean isDefault) {
        defaultProfile = new WeatherProfile(id, name, city, apiKey, unit, isDefault);
    }

    public static String convertunixtoDate(long dt) {
        Date date = new Date(dt * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd EEE MM yyyy");
        String formatted = sdf.format(date);
        return formatted;


    }

    public static String convertunixtoHour(long dt) {
        Date date = new Date(dt * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String formatted = sdf.format(date);
        return formatted;

    }
}
