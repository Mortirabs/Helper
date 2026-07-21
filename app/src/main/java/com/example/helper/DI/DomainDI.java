
package com.example.helper.DI;

import android.app.Dialog;

import com.example.helper.data.UsageStatsRepositoryImpl;
import com.example.helper.domain.DialogAlgorithm;
import com.example.helper.repository.JSONRepository;
import com.example.helper.repository.LocalInfo;
import com.example.helper.repository.UsageStatsRepository;
import com.example.helper.usecases.GetDayUsageStatsUseCase;
import com.example.helper.usecases.GetJSONApplicationCategory;
import com.example.helper.usecases.GetWeekUsageCallback;
import com.example.helper.usecases.GetWeekUsageStatsUseCase;
import com.example.helper.usecases.GetWelcomeDialogUseCase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DomainDI {
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
}

