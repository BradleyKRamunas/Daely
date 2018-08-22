package com.bramuna.daely.data.api.weather;

import com.bramuna.daely.data.api.types.Weather;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MetaWeatherApi {

    @GET("/api/location/{woeid}")
    Single<Weather> getWeather(@Path("woeid") int woeid);

}
