package com.example.helper.usecases;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class GetDayUsageStatsUseCase {
    private UsageStatsManager usm;
    private PackageManager pgc;
    public GetDayUsageStatsUseCase(UsageStatsManager usm, PackageManager pgc) {
        this.pgc = pgc;
        this.usm = usm;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<UsageStats> execute() {
        List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                ZonedDateTime.now().toInstant().toEpochMilli());
        appList = appList.stream().filter(app -> app.getTotalTimeInForeground() > 5000).collect(Collectors.toList());
        Log.d("Today Usage stats collection: ","Collection from today");
        return appList;
    }
}
