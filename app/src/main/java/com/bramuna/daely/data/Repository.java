package com.bramuna.daely.data;

import com.bramuna.daely.data.api.history.BaseTodayInHistoryApi;
import com.bramuna.daely.data.api.quotes.BaseQuoteApi;
import com.bramuna.daely.data.types.HistoryData;
import com.bramuna.daely.data.types.QuoteData;
import com.bramuna.daely.data.types.WeatherData;
import com.bramuna.daely.data.api.weather.BaseWeatherApi;
import com.bramuna.daely.data.preferences.BasePreferencesConnector;

import io.reactivex.Completable;
import io.reactivex.Single;

public class Repository implements BaseRepository {

    private static Repository instance;

    private BaseQuoteApi quoteApi;
    private BaseTodayInHistoryApi todayInHistoryApi;
    private BaseWeatherApi weatherApi;
    private BasePreferencesConnector preferencesConnector;

    private String[] locationNames;

    public static void init(BaseQuoteApi quoteApi, BaseTodayInHistoryApi todayInHistoryApi,
                            BaseWeatherApi weatherApi, BasePreferencesConnector preferencesConnector,
                            String[] locationNames) {
        instance = new Repository();
        instance.quoteApi = quoteApi;
        instance.todayInHistoryApi = todayInHistoryApi;
        instance.weatherApi = weatherApi;
        instance.preferencesConnector = preferencesConnector;
        instance.locationNames = locationNames;
    }

    public static Repository getInstance() {
        if(instance == null) {
            throw new RuntimeException("Repository was not initialized.");
        }
        return instance;
    }

    private Repository() {
    }

    @Override
    public String getSelectedLocationName() {
        int pos = getSelectedLocationIndex().blockingGet();
        return locationNames[pos];
    }

    @Override
    public Single<WeatherData> getWeather(String locationName) {
        return weatherApi.getWeather(locationName);
    }

    @Override
    public Single<HistoryData> getTodayInHistory() {
        return todayInHistoryApi.getTodayInHistory();
    }

    @Override
    public Single<QuoteData> getQuoteOfTheDay() {
        return quoteApi.getQuoteOfTheDay();
    }

    @Override
    public Completable setSelectedLocationIndex(int index) {
        return preferencesConnector.setSelectedLocationIndex(index);
    }

    @Override
    public Single<Integer> getSelectedLocationIndex() {
        return preferencesConnector.getSelectedLocationIndex();
    }

    @Override
    public Completable setNotificationOption(boolean option) {
        return preferencesConnector.setNotificationOption(option);
    }

    @Override
    public Single<Boolean> getNotificationOption() {
        return preferencesConnector.getNotificationOption();
    }

    @Override
    public Completable setNotificationTime(String time) {
        return preferencesConnector.setNotificationTime(time);
    }

    @Override
    public Single<String> getNotificationTime() {
        return preferencesConnector.getNotificationTime();
    }
}
