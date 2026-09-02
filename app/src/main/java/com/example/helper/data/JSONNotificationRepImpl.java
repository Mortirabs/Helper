package com.example.helper.data;

import android.content.Context;
import android.util.Log;

import com.example.helper.repository.JSONNotificationRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JSONNotificationRepImpl implements JSONNotificationRepository {
    private final Context context;
    public JSONNotificationRepImpl(Context context) {
        this.context = context;
    }
    @Override
    public String getJsonString() {
        try {
            InputStream inputStream = context.getAssets().open("notification_data");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            int i = inputStream.read(buffer);
            inputStream.close();
            if(i != -1) {
                return new String(buffer, StandardCharsets.UTF_8);
            } else {
                return "nothing";
            }
        } catch (IOException ex) {
            Log.d("JSONREPNOTIFY",ex.getMessage());
            return null;
        }
    }
}
