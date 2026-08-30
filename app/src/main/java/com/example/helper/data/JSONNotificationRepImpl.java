package com.example.helper.data;

import android.content.Context;
import android.util.Log;

import com.example.helper.repository.JSONNotificationRepository;

import java.io.IOException;
import java.io.InputStream;

public class JSONNotificationRepImpl implements JSONNotificationRepository {
    private final Context context;
    public JSONNotificationRepImpl(Context context) {
        this.context = context;
    }
    @Override
    public String getJsonString() {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open("notification_data");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
            return json;
        } catch (IOException ex) {
            Log.d("JSONREPNOTIFY",ex.getMessage());
            return null;
        }
    }
}
