package com.bramuna.daely.data.api.quotes;

import com.bramuna.daely.data.types.QuoteData;

import io.reactivex.Single;

public interface BaseQuoteApi {

    Single<QuoteData> getQuoteOfTheDay();

}
