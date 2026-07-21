package com.example.helper.presentation;

import android.util.Log;

import com.example.helper.usecases.GetDayUsageStatsUseCase;
import com.example.helper.usecases.GetJSONApplicationCategory;
import com.example.helper.usecases.GetWeekUsageCallback;
import com.example.helper.usecases.GetWelcomeDialogUseCase;

import javax.inject.Inject;

public class MainActivityViewModel {
    public GetDayUsageStatsUseCase getDayUsageUseCase;
    public GetWeekUsageCallback getWeekUsageCallback;
    public GetWelcomeDialogUseCase getWelcomeDialogUseCase;
    @Inject
    public MainActivityViewModel(GetDayUsageStatsUseCase getDayUsageUseCase,
                                 GetWeekUsageCallback getWeekUsageCallback,
                                 GetWelcomeDialogUseCase getWelcomeDialogUseCase
                                 ) {
        this.getDayUsageUseCase= getDayUsageUseCase;
        this.getWelcomeDialogUseCase = getWelcomeDialogUseCase;
        this.getWeekUsageCallback = getWeekUsageCallback;
        Log.d("MainActivityViewModel","Created");
    }
}
