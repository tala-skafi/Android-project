package com.example.myapplication.Retrofit;

import com.example.myapplication.Model.WeatherForecastResult;
import com.example.myapplication.Model.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {
    @GET("onecall")
    Observable<WeatherForecastResult> getForecastWeatherByCity(@Query("lat") double lat,
                                                               @Query("lon") double lon,
                                                               @Query("appid") String appid,
                                                               @Query("exclude") String exclude,
                                                               @Query("units") String unit);

    @GET("weather")
    Observable<WeatherResult> getWeatherByCity(
            @Query("q") String city,
            @Query("appid") String appid,
            @Query("units") String unit
    );
}
