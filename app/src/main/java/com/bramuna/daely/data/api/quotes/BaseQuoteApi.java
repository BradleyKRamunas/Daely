package com.bramuna.daely.data.api.quotes;

import com.bramuna.daely.data.QuoteData;

import io.reactivex.Single;

public interface BaseQuoteApi {

    Single<QuoteData> getQuoteOfTheDay();

}
