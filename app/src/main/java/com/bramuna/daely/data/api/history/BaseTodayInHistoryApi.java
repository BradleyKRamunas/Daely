package com.bramuna.daely.data.api.history;

import com.bramuna.daely.data.types.HistoryData;

import io.reactivex.Single;

public interface BaseTodayInHistoryApi {

    Single<HistoryData> getTodayInHistory();

}
