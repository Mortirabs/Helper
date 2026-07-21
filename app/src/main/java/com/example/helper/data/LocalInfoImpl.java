package com.example.helper.data;

import android.content.Context;

import com.example.helper.repository.LocalInfo;

import java.util.Locale;

public class LocalInfoImpl implements LocalInfo {
    private Context context;
    public LocalInfoImpl(Context context) {
        this.context = context;
    }
    @Override
    public String getLocale() {
        java.util.Locale current = context.getResources().getConfiguration().getLocales().get(0);

        return current.getLanguage();
    }
}
