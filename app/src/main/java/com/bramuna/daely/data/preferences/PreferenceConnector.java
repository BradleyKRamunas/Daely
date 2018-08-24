package com.bramuna.daely.data.preferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.bramuna.daely.data.types.SettingsData;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class PreferenceConnector implements BasePreferencesConnector {

    private SharedPreferences sharedPreferences;

    public PreferenceConnector(Context context) {
        sharedPreferences = context.getSharedPreferences("Daely", Context.MODE_PRIVATE);
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public Completable setSelectedLocationIndex(int index) {
        return Completable.fromAction(() -> sharedPreferences.edit().putInt("index", index).commit()).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Integer> getSelectedLocationIndex() {
        return Single.just(sharedPreferences.getInt("index", 0)).subscribeOn(Schedulers.io());
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public Completable setNotificationOption(boolean option) {
        return Completable.fromAction(() -> sharedPreferences.edit().putBoolean("notifs", option).commit()).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Boolean> getNotificationOption() {
        return Single.just(sharedPreferences.getBoolean("notifs", true)).subscribeOn(Schedulers.io());
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public Completable setNotificationTime(String time) {
        return Completable.fromAction(() -> sharedPreferences.edit().putString("notiftime", time).commit()).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<String> getNotificationTime() {
        return Single.just(sharedPreferences.getString("notiftime", "8:00 AM")).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<SettingsData> getSettingsData() {
        return Single.just(new SettingsData(sharedPreferences.getInt("index", 0),
                sharedPreferences.getBoolean("notifs", true),
                sharedPreferences.getString("notiftime", "8:00 AM")))
                .subscribeOn(Schedulers.io());
    }
}
