package com.bramuna.daely.data.api.history;

import com.bramuna.daely.data.types.History;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface MuffinLabsApi {

    @GET("/date")
    Single<History> getTodayInHistory();

}
