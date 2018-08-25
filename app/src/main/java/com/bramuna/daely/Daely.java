package com.bramuna.daely;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.bramuna.daely.data.Repository;
import com.bramuna.daely.data.api.history.TodayInHistoryApi;
import com.bramuna.daely.data.api.quotes.BaseQuoteApi;
import com.bramuna.daely.data.api.quotes.QuoteApi;
import com.bramuna.daely.data.api.weather.WeatherApi;
import com.bramuna.daely.data.preferences.PreferenceConnector;
import com.bramuna.daely.data.types.QuoteData;
import com.bramuna.daely.util.Constants;
import com.bramuna.daely.util.NotificationReceiver;
import com.bramuna.daely.util.NotificationUtils;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class Daely extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Repository.init(new QuoteApi(), new TodayInHistoryApi(), new WeatherApi(), new PreferenceConnector(this),
                getResources().getStringArray(R.array.location_options));

        createNotificationChannel();
        NotificationUtils.scheduleNotification(this, Repository.getInstance().getNotificationTime().blockingGet());
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String notificationName = "Daely Notification";
            String notificationDescription = "A daily notification preparing you for the day.";
            NotificationChannel channel = new NotificationChannel(Constants.NOTIFICATION_CHANNEL,
                    notificationName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(notificationDescription);
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }
    }

}
