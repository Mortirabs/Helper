package com.example.helper.presentation;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.example.helper.usecases.NotifyReceiver;

public class ScheduleNotification {
    private Context context;
    private AlarmManager alarmManager;
    private PendingIntent pendIntent;
    public ScheduleNotification(Context context) {
        this.context = context;
        alarmManager =(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intentToPend = new Intent(context, NotifyReceiver.class);
        pendIntent = PendingIntent.getBroadcast(context,0,intentToPend,PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
    }
    @SuppressLint("ScheduleExactAlarm")
    public void scheduleNotification() {
        int delayTime = 1 * 60 * 1000;
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + delayTime,pendIntent);
        Log.d("setting schedule notify","scheduled");
    }
    public void cancelScheduleNotification() {
        if(pendIntent != null) {
            pendIntent.cancel();
        }
    }
}
