package com.bramuna.daely.data.preferences;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface BasePreferencesConnector {
    Completable setSelectedLocationIndex(int index);
    Single<Integer> getSelectedLocationIndex();
    Completable setNotificationOption(boolean option);
    Single<Boolean> getNotificationOption();
    Completable setNotificationTime(String time);
    Single<String> getNotificationTime();
}
