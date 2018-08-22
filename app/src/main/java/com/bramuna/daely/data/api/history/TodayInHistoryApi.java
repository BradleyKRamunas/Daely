package com.bramuna.daely.data.api.history;

import com.bramuna.daely.data.HistoryData;
import com.bramuna.daely.util.Constants;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TodayInHistoryApi implements BaseTodayInHistoryApi {

    private Retrofit retrofit;

    public TodayInHistoryApi() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.TODAY_IN_HISTORY_API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Override
    public Single<HistoryData> getTodayInHistory() {
        MuffinLabsApi muffinLabsApi = retrofit.create(MuffinLabsApi.class);
        return muffinLabsApi.getTodayInHistory()
                .map(HistoryData::from);
    }

}
