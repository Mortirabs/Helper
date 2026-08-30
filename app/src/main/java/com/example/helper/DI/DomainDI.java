
package com.example.helper.DI;

import com.example.helper.domain.DialogAlgorithm;
import com.example.helper.repository.JSONRepository;
import com.example.helper.repository.LocalInfo;
import com.example.helper.repository.SharedPrefRepository;
import com.example.helper.repository.UsageStatsRepository;
import com.example.helper.usecases.GetDayUsageStatsUseCase;
import com.example.helper.usecases.GetJSONApplicationCategory;
import com.example.helper.usecases.GetOnTimeNotificationStatus;
import com.example.helper.usecases.GetSystemAutoNightMode;
import com.example.helper.usecases.GetUserChooseNightMode;
import com.example.helper.usecases.GetWeekUsageCallback;
import com.example.helper.usecases.GetWeekUsageStatsUseCase;
import com.example.helper.usecases.GetWelcomeDialogUseCase;

import com.example.helper.usecases.SetNightModeAutoUseCase;
import com.example.helper.usecases.SetOnTimeNotificationStatus;
import com.example.helper.usecases.SetUserPermissionNotification;import com.example.helper.usecases.getUserPermissionToNotification;
import com.example.helper.usecases.setNightModeUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class DomainDI {
    @Provides
    public SetNightModeAutoUseCase provideSetNightAutoUseCase(SharedPrefRepository sharedPref) {
        return new SetNightModeAutoUseCase(sharedPref);
    }
    @Provides
    public setNightModeUseCase provideSetNightModeUseCase(SharedPrefRepository sharedPref) {
        return new setNightModeUseCase(sharedPref);
    }
    @Provides
    public GetDayUsageStatsUseCase provideDayUsageUseCase(UsageStatsRepository usageRep){
        return new GetDayUsageStatsUseCase(usageRep);
    }
    @Provides
    public GetWeekUsageStatsUseCase provideWeekUsageStatsUseCase(UsageStatsRepository usageRep) {
        return new GetWeekUsageStatsUseCase(usageRep);
    }
    @Provides
    public GetWeekUsageCallback provideWeekUsageCallback(GetWeekUsageStatsUseCase getWeekUseCase) {
        return new GetWeekUsageCallback(getWeekUseCase);
    }
    @Provides
    public GetJSONApplicationCategory provideJSONAppCategoryUseCase(JSONRepository jsonREP) {
        return new GetJSONApplicationCategory(jsonREP);
    }
    @Provides
    public DialogAlgorithm provideDialogAlgo(JSONRepository JSONRep,
                                             LocalInfo localInfo,
                                             UsageStatsRepository usageRep) {
        return new DialogAlgorithm(JSONRep,localInfo,usageRep);
    }
    @Provides
    public GetWelcomeDialogUseCase provideWelcomeDialogUseCase(DialogAlgorithm dialogAlgo) {
        return new GetWelcomeDialogUseCase(dialogAlgo);
    }
    @Provides
    public GetSystemAutoNightMode provideSystemAutoNightMode(SharedPrefRepository sharedPref) {
        return new GetSystemAutoNightMode(sharedPref);
    }
    @Provides
    public GetUserChooseNightMode provideGetUserChooseNightMode(SharedPrefRepository sharedPref) {
        return new GetUserChooseNightMode(sharedPref);
    }
    @Provides
    public getUserPermissionToNotification provideGetUserPermissionToNotification(SharedPrefRepository sharedPrefRepository) {
        return new getUserPermissionToNotification(sharedPrefRepository);
    }

    @Provides
    public SetUserPermissionNotification provideSetUserPermissionNotification(SharedPrefRepository sharedPrefRep) {
        return new SetUserPermissionNotification(sharedPrefRep);
    }
    @Provides
    public GetOnTimeNotificationStatus provideGetOnTimeNotificationStatus(SharedPrefRepository sharedPrefRep) {
        return new GetOnTimeNotificationStatus(sharedPrefRep);
    }
    @Provides
    public SetOnTimeNotificationStatus provideSetOnTimeNotificationStatus(SharedPrefRepository sharedPrefRep) {
        return new SetOnTimeNotificationStatus(sharedPrefRep);
    }

}

