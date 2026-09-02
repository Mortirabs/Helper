package com.example.helper.DI;

import android.content.Context;

import com.example.helper.data.JSONRepositoryImpl;
import com.example.helper.data.LocalInfoImpl;
import com.example.helper.data.SharedPrefRepositoryImpl;
import com.example.helper.data.UsageStatsRepositoryImpl;
import com.example.helper.data.storage.SharedPrefStorage;
import com.example.helper.data.storage.SharedPrefStorageImpl;
import com.example.helper.repository.JSONRepository;
import com.example.helper.repository.LocalInfo;
import com.example.helper.repository.SharedPrefRepository;
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
    @Singleton
    @Provides
    public SharedPrefStorage provideSharedPrefStorage(Context context) {
        return new SharedPrefStorageImpl(context);
    }
    @Singleton
    @Provides
    public SharedPrefRepository provideSharedPref(SharedPrefStorage sharedPrefStorage) {
        return new SharedPrefRepositoryImpl(sharedPrefStorage);
    }

    @Provides
    public LocalInfo provideLocalInfo() {
        return new LocalInfoImpl();
    }
}
