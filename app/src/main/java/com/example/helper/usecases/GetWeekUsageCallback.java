package com.example.helper.usecases;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;

import com.example.helper.model.DayUsageModel;
import com.example.helper.repository.UsageStatsRepository;

import java.util.HashMap;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.security.auth.callback.Callback;

public class GetWeekUsageCallback implements Callable<HashMap<Integer, DayUsageModel>>{
    private UsageStatsManager usm;
    private UsageStatsRepository usageRep;
    private GetWeekUsageStatsUseCase weekUsageUseCase;
    @Inject
    public GetWeekUsageCallback(GetWeekUsageStatsUseCase weekUsageUseCase) {this.weekUsageUseCase = weekUsageUseCase;
    }
    @Override
    public HashMap<Integer, DayUsageModel> call() throws Exception {
        return weekUsageUseCase.execute();
    }
}
