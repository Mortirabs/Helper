package com.example.helper;

import android.app.Application;
import android.content.Context;

import com.example.helper.DI.AppModule;
import com.example.helper.DI.ApplicationComponent;
import com.example.helper.DI.DaggerApplicationComponent;
import com.example.helper.data.UsageStatsRepositoryImpl;
import com.example.helper.repository.UsageStatsRepository;

import dagger.Provides;


public class app extends Application{
    public ApplicationComponent appComponent = DaggerApplicationComponent
            .builder()
            .appModule(new AppModule(this))
            .build();

}
