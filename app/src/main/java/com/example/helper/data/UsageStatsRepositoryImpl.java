package com.example.helper.data;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.example.helper.model.AppInfo;
import com.example.helper.model.DayUsageModel;
import com.example.helper.repository.UsageStatsRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import jakarta.inject.Inject;

public class UsageStatsRepositoryImpl implements UsageStatsRepository {
    private Context context;
    private UsageStatsManager usm;
    private PackageManager pm;
    private List<AppInfo> listOfAppUsage = new ArrayList<AppInfo>();
    public UsageStatsRepositoryImpl(Context context) {
        this.context = context;
        usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        pm = context.getPackageManager();
    }
    @Override
    public List<AppInfo> getDayUsageStats() {
        if (!listOfAppUsage.isEmpty()) {
            Log.d("ListOfUsage","from the cache sent");
            return listOfAppUsage;
        } else {
            Log.d("ListOfUsage","from the calculation sent");
        List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                ZonedDateTime.now().toInstant().toEpochMilli());
        appList = appList.stream().filter(app -> app.getTotalTimeInForeground() > 5000).collect(Collectors.toList());
        Log.d("Today Usage stats collection: ","Collection from today");
        for(UsageStats usage: appList) {
            try{
                listOfAppUsage.add(new AppInfo((String)pm.getApplicationLabel(pm.getApplicationInfo(usage.getPackageName(),PackageManager.GET_META_DATA)),usage.getTotalTimeInForeground()));
            } catch (PackageManager.NameNotFoundException e){
                e.printStackTrace();
            }
        }
        return listOfAppUsage;
    }}

    @Override
    public HashMap<Integer,List<UsageStats>>  getWeekUsageStats() {
        HashMap<Integer,List<UsageStats>> dayTimeMap = new HashMap<>();
            for (int z=0,b=1 ; z <= 6; z++ ,b++) {
            // Total time for the day of week:
            long totalTimeInDay = 0L;

            // Day fetch end time:
            long endTimeMilli = ZonedDateTime.now().minusDays(z).toInstant().toEpochMilli();
            // Day fetch begin time:
            long beginTimeMilli = ZonedDateTime.now().minusDays(b).toInstant().toEpochMilli();

            List<UsageStats> usageStatsDay = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,beginTimeMilli,endTimeMilli);

            usageStatsDay = usageStatsDay.stream().filter(app -> app.getTotalTimeInForeground() > 5000).collect(Collectors.toList());

            dayTimeMap.put(z,usageStatsDay);
        }
        return dayTimeMap;
    }

}
