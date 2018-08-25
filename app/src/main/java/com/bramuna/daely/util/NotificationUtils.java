package com.bramuna.daely.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bramuna.daely.Daely;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NotificationUtils {

    private final static String TAG = "NotificationUtils";

    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.ENGLISH);

    public static void scheduleNotification(Context context, String time) {
        Log.i(TAG, "Scheduling notification at: " + time);
        if(!isNotificationScheduled(context)) {
            Calendar calendar = Calendar.getInstance();
            try {
                // without min api of 26, this is the way that this has to be done...
                Calendar modify = Calendar.getInstance(); // create a new instance to modify
                modify.setTime(dateFormat.parse(time)); // this will set it to UNIX start time with specified hour and minute
                calendar.set(Calendar.HOUR_OF_DAY, modify.get(Calendar.HOUR_OF_DAY)); // modify our actual calendar's values
                calendar.set(Calendar.MINUTE, modify.get(Calendar.MINUTE)); // and here too
                if(calendar.before(Calendar.getInstance())) calendar.add(Calendar.DAY_OF_MONTH, 1); // since we do not want a notification immediately if this is before now, we add a day
            } catch (ParseException e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
            Log.i(TAG, "Time: " + calendar.toString());
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, createPendingIntent(context));
        }
    }

    public static void updateNotification(Context context, String time) {
        Log.i(TAG, "Updating notification at: " + time);
        if(isNotificationScheduled(context)) {
            Calendar calendar = Calendar.getInstance();
            try {
                Calendar modify = Calendar.getInstance();
                modify.setTime(dateFormat.parse(time));
                calendar.set(Calendar.HOUR_OF_DAY, modify.get(Calendar.HOUR_OF_DAY));
                calendar.set(Calendar.MINUTE, modify.get(Calendar.MINUTE));
                if(calendar.before(Calendar.getInstance())) calendar.add(Calendar.DAY_OF_MONTH, 1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = createPendingIntent(context);
            alarmManager.cancel(pendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, createPendingIntent(context));
        } else {
            scheduleNotification(context, time);
        }
    }

    public static void cancelNotification(Context context) {
        Log.i(TAG, "Canceling notification");
        if(isNotificationScheduled(context)) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = createPendingIntent(context);
            pendingIntent.cancel();
            alarmManager.cancel(pendingIntent);
        }
    }

    private static PendingIntent createPendingIntent(Context context) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        return PendingIntent.getBroadcast(context, Constants.NOTIFICATION_REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static boolean isNotificationScheduled(Context context) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Constants.NOTIFICATION_REQUEST_CODE,
                intent, PendingIntent.FLAG_NO_CREATE);
        return pendingIntent != null;
    }
}
