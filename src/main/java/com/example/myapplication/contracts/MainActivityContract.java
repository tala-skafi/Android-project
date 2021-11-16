package com.example.myapplication.contracts;

import com.example.myapplication.Model.WeatherProfile;

public interface MainActivityContract {
    void newProfileAdded(boolean isDefault);
    void currentProfileSwitched(WeatherProfile id);
    void currentProfileUpdated();
    void cancel();
}
