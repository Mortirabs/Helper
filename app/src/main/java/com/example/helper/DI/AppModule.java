package com.example.helper.DI;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import jakarta.inject.Singleton;
@Module
public class AppModule {
    private Context context;
    public AppModule(Context context) {
        this.context = context;
    }
    @Provides
    public Context provideContext() {
        return context;
    }
}
