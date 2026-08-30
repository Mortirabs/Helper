package com.example.helper.usecases;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.helper.R;
import com.example.helper.data.JSONNotificationRepImpl;
import com.example.helper.data.LocalInfoImpl;
import com.example.helper.repository.JSONNotificationRepository;
import com.example.helper.repository.LocalInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NotifyReceiver extends BroadcastReceiver {
    private CompositeDisposable disposables = new CompositeDisposable();
    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("receiver","logged");
        PendingResult pendingResult = goAsync();
        JSONNotificationRepository notifyRep = new JSONNotificationRepImpl(context);
        LocalInfo localInfo = new LocalInfoImpl(context);
        createNotificationChannel(context);
        Disposable disposableJson = (Disposable) Single.fromCallable(()->getNotificationText(notifyRep.getJsonString(),localInfo)
                )
                .subscribeOn(Schedulers.io())
                .timeout(5, TimeUnit.SECONDS)
                .doFinally(pendingResult::finish)
                .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onSuccess -> {
                            NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(context,"helperchannel")
                                .setSmallIcon(R.drawable.head)
                                .setContentTitle("firstNotify")
                                .setContentText(onSuccess)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                            NotificationManagerCompat notifyManager = NotificationManagerCompat.from(context);
                            notifyManager.notify(100,notifyBuilder.build());
                        },
                                throwable -> {
                            Log.d("notify","not work");
                            }
                        );
        disposables.add(disposableJson);
    }
    public void createNotificationChannel(Context context) {
        CharSequence name = "main_channel";
        String description = "channel for notifitcation";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        NotificationChannel channel = new NotificationChannel("helperchannel", name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
    public String getNotificationText(String jsonText,LocalInfo localInfo) {
        try {
            JSONObject jsonObject = new JSONObject(jsonText);
            if (Objects.equals(localInfo.getLocale(), "ru")) {
                JSONArray jsonArrayOfMessage = jsonObject.getJSONArray("notification_list_rus");
                return jsonArrayOfMessage.getString(getRandomMessage(jsonArrayOfMessage.length()));
            } else {
                Log.d("locales",localInfo.getLocale());
                JSONArray jsonArrayOfMessage = jsonObject.getJSONArray("notification_list");
                return jsonArrayOfMessage.getString(getRandomMessage(jsonArrayOfMessage.length()));
            }
        } catch (JSONException e) {
            Log.d("problem",e.getMessage());
            return "I AM SO STRESSED HERE";
        }
    }
    public Integer getRandomMessage(int maxInt) {
        return (int) (Math.random() * maxInt);
    }

}
