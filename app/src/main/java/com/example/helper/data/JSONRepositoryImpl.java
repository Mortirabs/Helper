package com.example.helper.data;

import android.content.Context;

import com.example.helper.repository.JSONRepository;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import jakarta.inject.Inject;

public class JSONRepositoryImpl implements JSONRepository {
    private final Context context;
    @Inject
    public JSONRepositoryImpl(Context context) {
        this.context = context;
    }
    @Override
    public String getJsonString() {
        try {
            InputStream inputStream = context.getAssets().open("application_data");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            int byteReaderInt = inputStream.read(buffer);
            inputStream.close();
            if(byteReaderInt != -1) {
                return new String(buffer, StandardCharsets.UTF_8);
            } else {
                return "Problem";
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }


}
