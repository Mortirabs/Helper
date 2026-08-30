package com.example.helper.DI;

import android.content.Context;

import com.example.helper.app;
import com.example.helper.presentation.MainActivity;
import com.example.helper.presentation.MenuFragment;

import java.lang.annotation.Retention;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;
import kotlin.annotation.AnnotationRetention;

@Component(modules = {AppModule.class,DataDI.class,DomainDI.class})
@Singleton
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
    void inject(MenuFragment menuFragment);
 }