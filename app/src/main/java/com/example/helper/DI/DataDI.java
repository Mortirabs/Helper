package com.example.helper.DI;

import android.content.Context;

import com.example.helper.data.JSONRepositoryImpl;
import com.example.helper.data.LocalInfoImpl;
import com.example.helper.data.UsageStatsRepositoryImpl;
import com.example.helper.domain.DialogAlgorithm;
import com.example.helper.repository.JSONRepository;
import com.example.helper.repository.LocalInfo;
import com.example.helper.repository.UsageStatsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataDI {
    @Singleton
    @Provides
    public UsageStatsRepository provideUsageStatsRepository(Context context) {
        return new UsageStatsRepositoryImpl(context);
    }
    @Provides
    @Singleton
    public JSONRepository provideJsonRepository(Context context) {
        return new JSONRepositoryImpl(context);
    }
    @Provides
    public LocalInfo provideLocalInfo(Context context) {
        return new LocalInfoImpl(context);
    }
}
