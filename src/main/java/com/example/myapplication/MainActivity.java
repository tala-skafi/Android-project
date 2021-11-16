package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.Common.Common;
import com.example.myapplication.Model.Weather;
import com.example.myapplication.Model.WeatherProfile;
import com.example.myapplication.contracts.MainActivityContract;
import com.example.myapplication.fragments.AddProfileFragment;
import com.example.myapplication.fragments.EditProfileFragment;
import com.example.myapplication.fragments.ForecastFragment;
import com.example.myapplication.fragments.SwitchProfilesFragment;
import com.example.myapplication.fragments.TodayWeatherFragment;
import com.example.myapplication.managers.SqLiteManager;
import com.google.android.material.navigation.NavigationView;

public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MainActivityContract

{
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        loadDefaultProfileToMemory();
        showDrawerForCurrentWeather();
    }

    private void loadDefaultProfileToMemory() {
        Common.defaultProfile = SqLiteManager.getInstance(this).getDefaultProfile();
        if(Common.defaultProfile == null)
            showAddProfileFragment();
        else
            showCurrentWeatherFragment();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_item_add_profile:
                showAddProfileFragment();
                break;
            case R.id.nav_item_edit_profile:
                showEditProfileFragment();
                break;
            case R.id.nav_item_switch_profile:
                showSwitchProfileFragment();
                break;
            case R.id.nav_item_five_day:
                showForecastWeatherFragment();
                break;
            case R.id.nav_item_current_weather:
                showCurrentWeatherFragment();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showAddProfileFragment() {
        AddProfileFragment newFragment = new AddProfileFragment();
        newFragment.setContractor(this);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, newFragment,  "Add Profile").commit();
    }

    private void showEditProfileFragment() {
        EditProfileFragment newFragment = new EditProfileFragment();
        newFragment.setContractor(this);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, newFragment,  "Edit Profile").commit();
    }

    private void showSwitchProfileFragment() {
        SwitchProfilesFragment newFragment = new SwitchProfilesFragment();
        newFragment.setContractor(this);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, newFragment,  "Switch Profile").commit();
    }

    private void showCurrentWeatherFragment() {
        showDrawerForCurrentWeather();
        TodayWeatherFragment newFragment = new TodayWeatherFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, newFragment,  "Current Weather").commit();
    }

    private void showForecastWeatherFragment() {
        showDrawerForForecast();
        Fragment newFragment = new ForecastFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, newFragment,  "Switch Profile").commit();
    }

    private void showDrawerForForecast() {
        if(Common.defaultProfile == null) {
            showStarterDrawer();
            return;
        }

        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.activity_main_drawer_forecast);
    }
    private void showDrawerForCurrentWeather() {
        if(Common.defaultProfile == null) {
            showStarterDrawer();
            return;
        }

        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.activity_main_drawer);
    }
    private void showStarterDrawer() {
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.activity_main_drawer_starter);
    }

    @Override
    public void newProfileAdded(boolean isDefault) {
        showCurrentWeatherFragment();

    }

    @Override
    public void currentProfileSwitched(WeatherProfile profile) {
        Common.defaultProfile = profile;
        showCurrentWeatherFragment();
    }

    @Override
    public void currentProfileUpdated() {
        showCurrentWeatherFragment();
    }

    @Override
    public void cancel() {
        showCurrentWeatherFragment();
    }
}
