package com.example.helper.data;

import android.content.Context;

import com.example.helper.repository.LocalInfo;

import java.util.Locale;

public class LocalInfoImpl implements LocalInfo {
    @Override
    public String getLocale() {
        return Locale.getDefault().getLanguage();
    }
}
