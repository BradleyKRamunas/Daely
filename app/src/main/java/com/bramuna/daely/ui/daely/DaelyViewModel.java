package com.bramuna.daely.ui.daely;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.bramuna.daely.data.Response;
import com.bramuna.daely.data.Status;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DaelyViewModel extends ViewModel {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MutableLiveData<Response<String>> testLiveData = new MutableLiveData<>();

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
    }

    public MutableLiveData<Response<String>> getLiveData() {
        return testLiveData;
    }

    public void testLoadWeather() {
        Disposable disposable = Observable.just("Weather Data")
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(consumer -> testLiveData.setValue(new Response<>(Status.LOADING, null, null)))
                .subscribe(data -> testLiveData.setValue(new Response<>(Status.COMPLETE, data, null)),
                        error -> testLiveData.setValue(new Response<>(Status.ERROR, null, error)));
        compositeDisposable.add(disposable);
    }

}
