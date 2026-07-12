package com.example.helper.usecases;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;

import com.example.helper.model.DayUsageModel;

import java.util.HashMap;
import java.util.concurrent.Callable;

import javax.security.auth.callback.Callback;

public class GetWeekUsageCallback implements Callable<HashMap<Integer, DayUsageModel>>{
    private UsageStatsManager usm;
    public GetWeekUsageCallback(UsageStatsManager usm) {
        this.usm = usm;
    }
    @Override
    public HashMap<Integer, DayUsageModel> call() throws Exception {
        GetWeekUsageStatsUseCase getWeekHashMap = new GetWeekUsageStatsUseCase(usm);
        return getWeekHashMap.execute();
    }
}
