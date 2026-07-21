package com.example.helper.DI;

import android.content.Context;

import com.example.helper.app;
import com.example.helper.presentation.MainActivity;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;

@Component(modules = {AppModule.class,DataDI.class,DomainDI.class})
@Singleton
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
 }
