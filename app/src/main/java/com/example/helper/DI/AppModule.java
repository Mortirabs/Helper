package com.example.helper.DI;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;
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
