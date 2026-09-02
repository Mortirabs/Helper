package com.example.helper.presentation;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.helper.domain.DialogAlgorithm;
import com.example.helper.repository.LocalInfo;
import com.example.helper.repository.SharedPrefRepository;
import com.example.helper.usecases.GetDayUsageStatsUseCase;
import com.example.helper.usecases.GetJSONApplicationCategory;
import com.example.helper.usecases.GetOnTimeNotificationStatus;
import com.example.helper.usecases.GetSystemAutoNightMode;
import com.example.helper.usecases.GetUserChooseNightMode;
import com.example.helper.usecases.GetWeekUsageCallback;
import com.example.helper.usecases.GetWelcomeDialogUseCase;
import com.example.helper.usecases.SetNightModeAutoUseCase;
import com.example.helper.usecases.SetOnTimeNotificationStatus;
import com.example.helper.usecases.getUserPermissionToNotification;
import com.example.helper.usecases.setNightModeUseCase;

import javax.inject.Inject;

public class MainActivityViewModel extends ViewModel {
    public GetDayUsageStatsUseCase getDayUsageUseCase;
    public getUserPermissionToNotification getUserPermissionToNotification;
    public GetWeekUsageCallback getWeekUsageCallback;
    public GetWelcomeDialogUseCase getWelcomeDialogUseCase;
    public GetSystemAutoNightMode getSystemAutoNightModeUseCase;
    public GetUserChooseNightMode getUserChooseNightModeUseCase;
    public GetOnTimeNotificationStatus getOnTimeNotificationStatusUseCase;
    public SetOnTimeNotificationStatus setOnTimeNotificationStatusUseCase;
    public DialogAlgorithm dialogAlgo;
    public ScheduleNotification scheduleNotification;
    public LocalInfo localInfo;
    @Inject
    public MainActivityViewModel(GetDayUsageStatsUseCase getDayUsageUseCase,
                                 GetWeekUsageCallback getWeekUsageCallback,
                                 GetWelcomeDialogUseCase getWelcomeDialogUseCase,
                                 DialogAlgorithm dialogAlgo,
                                 GetSystemAutoNightMode getSystemAutoNightModeUseCase,
                                 GetUserChooseNightMode getUserChooseNightModeUseCase,
                                 GetOnTimeNotificationStatus getOnTimeNotificationStatus,
                                 SetOnTimeNotificationStatus setOnTimeNotificationStatusUseCase,
                                 ScheduleNotification scheduleNotification,
                                 LocalInfo li,
                                 getUserPermissionToNotification getUserNotify
                                 ) {
        this.dialogAlgo = dialogAlgo;
        this.getDayUsageUseCase= getDayUsageUseCase;
        this.getOnTimeNotificationStatusUseCase = getOnTimeNotificationStatus;
        this.scheduleNotification = scheduleNotification;
        this.setOnTimeNotificationStatusUseCase = setOnTimeNotificationStatusUseCase;
        this.getWelcomeDialogUseCase = getWelcomeDialogUseCase;
        this.getWeekUsageCallback = getWeekUsageCallback;
        this.getUserPermissionToNotification = getUserNotify;
        this.getUserChooseNightModeUseCase = getUserChooseNightModeUseCase;
        this.getSystemAutoNightModeUseCase = getSystemAutoNightModeUseCase;
        this.localInfo = li;
        Log.d("MainActivityViewModel","Created");
    }
}
