package com.example.myapplication.Model;

import java.util.List;

public class WeatherForecastResult {
    private List<Daily> daily;
    private double lat;
    private double lon;

    public WeatherForecastResult() {
    }

    public WeatherForecastResult(List<Daily> daily, double lat, double lon) {
        this.daily = daily;
        this.lat = lat;
        this.lon = lon;
    }

    public List<Daily> getDaily() {
        return daily;
    }

    public void setDaily(List<Daily> daily) {
        this.daily = daily;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
