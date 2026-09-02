package com.example.helper.presentation;

import androidx.lifecycle.ViewModel;

import com.example.helper.repository.LocalInfo;
import com.example.helper.usecases.GetSystemAutoNightMode;
import com.example.helper.usecases.GetUserChooseNightMode;
import com.example.helper.usecases.SetNightModeAutoUseCase;
import com.example.helper.usecases.SetUserPermissionNotification;import com.example.helper.usecases.getUserPermissionToNotification;
import com.example.helper.usecases.setNightModeUseCase;

import jakarta.inject.Inject;

public class MenuFragmentViewModel extends ViewModel {
    public GetUserChooseNightMode getUserChooseNightModeUseCase;
    public GetSystemAutoNightMode getSystemAutoNightModeUseCase;
    public SetNightModeAutoUseCase setNightModeAutoUseCase;
    public setNightModeUseCase setNightModeUseCase;
    public ScheduleNotification scheduleNotification;
    public getUserPermissionToNotification getUserPermissionToNotificationUseCase;
    public SetUserPermissionNotification setUserPermissionNotification;
    public LocalInfo localInfo;
    @Inject
    public MenuFragmentViewModel(
            ScheduleNotification scheduleNotification,
            GetUserChooseNightMode getUserChooseNightMode,
            GetSystemAutoNightMode getSystemAutoNightMode,
            SetNightModeAutoUseCase setNightModeAutoUseCase,
            setNightModeUseCase setNightModeUseCase,
            getUserPermissionToNotification getUserPermissionNotify,
            SetUserPermissionNotification setUserPermissionNotification,
            LocalInfo localInfo
    ) {
        this.getUserChooseNightModeUseCase = getUserChooseNightMode;
        this.scheduleNotification = scheduleNotification;
        this.getSystemAutoNightModeUseCase = getSystemAutoNightMode;
        this.getUserPermissionToNotificationUseCase = getUserPermissionNotify;
        this.setNightModeAutoUseCase = setNightModeAutoUseCase;
        this.setNightModeUseCase = setNightModeUseCase;
        this.setUserPermissionNotification = setUserPermissionNotification;
        this.localInfo = localInfo;
    }
}
