package com.bramuna.daely;

import android.app.Application;

import com.bramuna.daely.data.Repository;
import com.bramuna.daely.data.api.history.TodayInHistoryApi;
import com.bramuna.daely.data.api.quotes.BaseQuoteApi;
import com.bramuna.daely.data.api.quotes.QuoteApi;
import com.bramuna.daely.data.api.weather.WeatherApi;
import com.bramuna.daely.data.preferences.PreferenceConnector;
import com.bramuna.daely.data.types.QuoteData;
import com.bramuna.daely.util.Constants;

import io.reactivex.Single;

public class Daely extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Repository.init(new BaseQuoteApi() {
            @Override
            public Single<QuoteData> getQuoteOfTheDay() {
                return Single.just(QuoteData.mockData);
            }
        }, new TodayInHistoryApi(), new WeatherApi(), new PreferenceConnector(this),
                getResources().getStringArray(R.array.location_options));
    }
}
