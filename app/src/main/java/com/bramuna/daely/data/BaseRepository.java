package com.bramuna.daely.data;

import com.bramuna.daely.data.types.HistoryData;
import com.bramuna.daely.data.types.QuoteData;
import com.bramuna.daely.data.types.WeatherData;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface BaseRepository {
    Single<WeatherData> getWeather(String locationName);
    Single<HistoryData> getTodayInHistory();
    Single<QuoteData> getQuoteOfTheDay();

    String getSelectedLocationName();
    Completable setSelectedLocationIndex(int index);
    Single<Integer> getSelectedLocationIndex();
    Completable setNotificationOption(boolean option);
    Single<Boolean> getNotificationOption();
    Completable setNotificationTime(String time);
    Single<String> getNotificationTime();
}
