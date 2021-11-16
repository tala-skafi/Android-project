package com.example.myapplication.Model;

import java.util.List;

public class Daily {
    private Temp temp;
    private List<Weather> weather;

    public Daily() {
    }

    public Daily(Temp temp, List<Weather> weather) {
        this.temp = temp;
        this.weather = weather;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
}
