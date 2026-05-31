package com.example.helper.data;

import android.content.Context;

import com.example.helper.repository.JSONRepository;

import java.io.IOException;
import java.io.InputStream;

public class JSONRepositoryImpl implements JSONRepository {
    private Context context;
    public JSONRepositoryImpl(Context context) {
        this.context = context;
    }
    @Override
    public String getJsonString() {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open("json_data");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
            return json;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void includeNewApplication(String applicationCategory, String applicationName) {

    }

    @Override
    public void deleteApplication(String applicationCategory, String applicationName) {

    }
}
