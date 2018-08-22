package com.bramuna.daely.data.api.weather;

import com.bramuna.daely.data.WeatherData;

import io.reactivex.Single;

public interface BaseWeatherApi {

    Single<WeatherData> getWeather(int woeid);

}
