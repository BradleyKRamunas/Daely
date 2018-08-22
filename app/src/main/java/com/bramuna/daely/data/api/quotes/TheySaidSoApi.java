package com.bramuna.daely.data.api.quotes;

import com.bramuna.daely.data.api.types.TheySaidSo;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface TheySaidSoApi {

    @GET("/qod.json")
    Single<TheySaidSo> getQuoteOfTheDay();
}
