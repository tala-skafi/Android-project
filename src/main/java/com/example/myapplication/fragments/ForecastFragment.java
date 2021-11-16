package com.example.myapplication.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import android.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.WeatherForecastAdapter;
import com.example.myapplication.Common.Common;
import com.example.myapplication.Model.WeatherForecastResult;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.IOpenWeatherMap;
import com.example.myapplication.Retrofit.RetrofitClient;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ForecastFragment extends Fragment {
    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;
    TextView txt_city_name, txt_geo_coord;
    ProgressBar progressBar;
    RecyclerView recycler_forecast;
 static ForecastFragment instance;

    public static ForecastFragment getInstance() {
        if(instance==null)
            instance=new ForecastFragment();
        return instance;
    }

    public ForecastFragment() {
    compositeDisposable=new CompositeDisposable();
        Retrofit retrofit= RetrofitClient.getInstance();
        mService=retrofit.create(IOpenWeatherMap.class);
    }

    public static ForecastFragment newInstance() {
        ForecastFragment fragment = new ForecastFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View itemView =inflater.inflate(R.layout.fragment_forecast, container, false);
        txt_city_name=(TextView)itemView.findViewById(R.id.txt_city_name);
        txt_geo_coord=(TextView)itemView.findViewById(R.id.txt_geo_coord);
        recycler_forecast=(RecyclerView)itemView.findViewById(R.id.recycler_forecast);
        progressBar = itemView.findViewById(R.id.progress_indicator);
        recycler_forecast.setHasFixedSize(true);
        recycler_forecast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        getForecastWeatherInformation();

        return itemView;
    }

    private void getForecastWeatherInformation() {
        Runnable runnable = () -> {
            Geocoder gc = new Geocoder(getContext());
            List<Address> ads = null;
            try {
                ads = gc.getFromLocationName(Common.defaultProfile.getCity(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(ads == null || ads.size() == 0)
                return;
            compositeDisposable.add(mService.getForecastWeatherByCity(
                    ads.get(0).getLatitude(),
                    ads.get(0).getLongitude(),
                    Common.defaultProfile.getApiKey(),
                    "current, minutely, hourly, alerts",
                    Common.defaultProfile.getUnit()
            ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weatherForecastResult -> {
                        displayForecastWeather(weatherForecastResult);

                    }, throwable -> {
                        Log.d("test test", throwable.getMessage());
                        Toast.makeText(getActivity(),"" + throwable.getMessage(),Toast.LENGTH_SHORT).show();
                    }));
        };

        new Thread(runnable).start();
    }

    private void displayForecastWeather(WeatherForecastResult weatherForecastResult) {
        progressBar.setVisibility(View.GONE);
        recycler_forecast.setVisibility(View.VISIBLE);
        txt_city_name.setText(new StringBuilder(Common.defaultProfile.getCity()));
        txt_geo_coord.setText("[" + weatherForecastResult.getLat() + "," + weatherForecastResult.getLon() + "]");
        WeatherForecastAdapter adapter=new WeatherForecastAdapter(getContext(),weatherForecastResult);
        recycler_forecast.setAdapter(adapter);
    }
}