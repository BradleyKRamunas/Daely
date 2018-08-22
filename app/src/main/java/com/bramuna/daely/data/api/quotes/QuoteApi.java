package com.bramuna.daely.data.api.quotes;

import com.bramuna.daely.data.QuoteData;
import com.bramuna.daely.util.Constants;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuoteApi implements BaseQuoteApi {

    private Retrofit retrofit;

    public QuoteApi() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.QUOTE_API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Override
    public Single<QuoteData> getQuoteOfTheDay() {
        TheySaidSoApi theySaidSoApi = retrofit.create(TheySaidSoApi.class);
        return theySaidSoApi.getQuoteOfTheDay()
                .map(QuoteData::from);
    }

}
