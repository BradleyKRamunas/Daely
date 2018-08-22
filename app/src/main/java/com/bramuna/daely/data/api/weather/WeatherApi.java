package com.bramuna.daely.data.api.weather;

import com.bramuna.daely.data.WeatherData;
import com.bramuna.daely.util.Constants;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherApi implements BaseWeatherApi {

    private Retrofit retrofit;

    public WeatherApi() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.WEATHER_API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Override
    public Single<WeatherData> getWeather(int woeid) {
        MetaWeatherApi metaWeatherApi = retrofit.create(MetaWeatherApi.class);
        return metaWeatherApi.getWeather(woeid)
                .map(WeatherData::from);
    }
}
