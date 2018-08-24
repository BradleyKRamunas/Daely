package com.bramuna.daely.ui.daely;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.bramuna.daely.data.BaseRepository;
import com.bramuna.daely.data.Repository;
import com.bramuna.daely.data.types.HistoryData;
import com.bramuna.daely.data.types.QuoteData;
import com.bramuna.daely.data.Response;
import com.bramuna.daely.data.Status;
import com.bramuna.daely.data.types.SettingsData;
import com.bramuna.daely.data.types.WeatherData;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DaelyViewModel extends ViewModel {

    private static final String TAG = "DaelyViewModel";

    private final BaseRepository repository = Repository.getInstance();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MutableLiveData<Response<WeatherData>> weatherLiveData = new MutableLiveData<>();
    private final MutableLiveData<Response<QuoteData>> quoteLiveData = new MutableLiveData<>();
    private final MutableLiveData<Response<HistoryData>> historyLiveData = new MutableLiveData<>();
    private final MutableLiveData<Response<SettingsData>> settingsLiveData = new MutableLiveData<>();

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
    public MutableLiveData<Response<SettingsData>> getSettingsLiveData() {
        return settingsLiveData;
    }

    public void fetchWeather() {
        Disposable disposable = repository.getWeather(repository.getSelectedLocationName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(consumer -> weatherLiveData.setValue(new Response<>(Status.LOADING, null, null)))
                .subscribe(data -> weatherLiveData.setValue(new Response<>(Status.COMPLETE, data, null)),
                        error -> weatherLiveData.setValue(new Response<>(Status.ERROR, null, error)));
        compositeDisposable.add(disposable);
    }

    public void fetchQuote() {
        Disposable disposable = repository.getQuoteOfTheDay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(consumer -> quoteLiveData.setValue(new Response<>(Status.LOADING, null, null)))
                .subscribe(data -> quoteLiveData.setValue(new Response<>(Status.COMPLETE, data, null)),
                        error -> quoteLiveData.setValue(new Response<>(Status.ERROR, null, error)));
        compositeDisposable.add(disposable);
    }

    public void fetchTodayInHistory() {
        Disposable disposable = repository.getTodayInHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(consumers -> historyLiveData.setValue(new Response<>(Status.LOADING, null, null)))
                .subscribe(data -> historyLiveData.setValue(new Response<>(Status.COMPLETE, data, null)),
                        error -> historyLiveData.setValue(new Response<>(Status.ERROR, null, error)));
        compositeDisposable.add(disposable);
    }

    public void fetchSettings() {
        Disposable disposable = repository.getSettingsData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(consumer -> settingsLiveData.setValue(new Response<>(Status.LOADING, null, null)))
                .subscribe(data -> settingsLiveData.setValue(new Response<>(Status.COMPLETE, data, null)),
                        error -> settingsLiveData.setValue(new Response<>(Status.ERROR, null, error)));
        compositeDisposable.add(disposable);
    }

    public Completable setLocationSelectedIndex(int index) {
        return repository.setSelectedLocationIndex(index);
    }

    public Completable setNotificationTime(String time) {
        return repository.setNotificationTime(time);
    }

    public Completable setNotifications(boolean value) {
        //TODO: handle canceling/creating notifications via AlarmManager
        return repository.setNotificationOption(value);
    }

}
