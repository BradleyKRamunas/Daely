package com.bramuna.daely.data.api.weather;

import com.bramuna.daely.data.types.Weather;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YahooWeatherApi {

    @GET("/v1/public/yql?format=json")
    Single<Weather> getWeather(@Query(value = "q", encoded = true) String query);

}
