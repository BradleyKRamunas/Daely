package com.bramuna.daely.ui.daely;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.bramuna.daely.data.HistoryData;
import com.bramuna.daely.data.QuoteData;
import com.bramuna.daely.data.Response;
import com.bramuna.daely.data.Status;
import com.bramuna.daely.data.WeatherData;
import com.bramuna.daely.data.api.history.BaseTodayInHistoryApi;
import com.bramuna.daely.data.api.history.TodayInHistoryApi;
import com.bramuna.daely.data.api.quotes.BaseQuoteApi;
import com.bramuna.daely.data.api.quotes.QuoteApi;
import com.bramuna.daely.data.api.types.Weather;
import com.bramuna.daely.data.api.weather.BaseWeatherApi;
import com.bramuna.daely.data.api.weather.WeatherApi;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DaelyViewModel extends ViewModel {

    private final BaseWeatherApi weatherApi = new WeatherApi();
    private final BaseQuoteApi quoteApi = new QuoteApi();
    private final BaseTodayInHistoryApi todayInHistoryApi = new TodayInHistoryApi();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MutableLiveData<Response<WeatherData>> weatherLiveData = new MutableLiveData<>();
    private final MutableLiveData<Response<QuoteData>> quoteLiveData = new MutableLiveData<>();
    private final MutableLiveData<Response<HistoryData>> historyLiveData = new MutableLiveData<>();

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
    }

    public MutableLiveData<Response<WeatherData>> getWeatherLiveData() {
        return weatherLiveData;
    }

    public MutableLiveData<Response<QuoteData>> getQuoteLiveData() {
        return quoteLiveData;
    }

    public MutableLiveData<Response<HistoryData>> getHistoryLiveData() {
        return historyLiveData;
    }

    public void fetchWeather() {
        Disposable disposable = weatherApi.getWeather(	2455920)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(consumer -> weatherLiveData.setValue(new Response<>(Status.LOADING, null, null)))
                .subscribe(data -> weatherLiveData.setValue(new Response<>(Status.COMPLETE, data, null)),
                        error -> weatherLiveData.setValue(new Response<>(Status.ERROR, null, error)));
        compositeDisposable.add(disposable);
    }

    public void fetchQuote() {
        Disposable disposable = quoteApi.getQuoteOfTheDay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(consumer -> quoteLiveData.setValue(new Response<>(Status.LOADING, null, null)))
                .subscribe(data -> quoteLiveData.setValue(new Response<>(Status.COMPLETE, data, null)),
                        error -> quoteLiveData.setValue(new Response<>(Status.ERROR, null, error)));
        compositeDisposable.add(disposable);
    }

    public void fetchTodayInHistory() {
        Disposable disposable = todayInHistoryApi.getTodayInHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(consumers -> historyLiveData.setValue(new Response<>(Status.LOADING, null, null)))
                .subscribe(data -> historyLiveData.setValue(new Response<>(Status.COMPLETE, data, null)),
                        error -> historyLiveData.setValue(new Response<>(Status.ERROR, null, error)));
        compositeDisposable.add(disposable);
    }

}
