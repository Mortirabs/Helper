package com.example.helper.usecases;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.helper.model.AppInfo;
import com.example.helper.repository.UsageStatsRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.inject.Inject;

public class GetDayUsageStatsUseCase {
    private final UsageStatsRepository usageRep;
    @Inject
    public GetDayUsageStatsUseCase(UsageStatsRepository usageRep) {
        this.usageRep = usageRep;
    }
    public List<AppInfo> execute() {
        List<AppInfo> appInfoList = usageRep.getDayUsageStats();
        appInfoList.sort(Comparator.comparingLong(AppInfo::getMillisecondOfUsage));
        Collections.reverse(appInfoList);
        return appInfoList;
//        List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
//                LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
//                ZonedDateTime.now().toInstant().toEpochMilli());
//        appList = appList.stream().filter(app -> app.getTotalTimeInForeground() > 5000).collect(Collectors.toList());
//        Log.d("Today Usage stats collection: ","Collection from today");
//        return appList;
    }
}
