package com.example.myapplication.Model;

public class WeatherProfile {
    private int id;
    private String name;
    private String city;
    private String unit;
    private String apiKey;
    private boolean isDefault = false;

    public WeatherProfile(int id, String name, String city, String apiKey, String unit, boolean isDefault) {
        this.name = name;
        this.city = city;
        this.unit = unit;
        this.apiKey = apiKey;
        this.isDefault = isDefault;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
