package com.example.helper.usecases;

import android.app.usage.EventStats;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.helper.model.DayUsageModel;
import com.example.helper.repository.UsageStatsRepository;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import jakarta.inject.Inject;

public class GetWeekUsageStatsUseCase {
    private UsageStatsRepository usm;
    private UsageStatsManager usgm;
    private HashMap<Integer, DayUsageModel> dayTimeMap = new HashMap<>();
    @Inject
    public GetWeekUsageStatsUseCase(UsageStatsRepository usm) {
        this.usm = usm;
    }
    public HashMap<Integer,DayUsageModel> execute() {
        HashMap<Integer, List<UsageStats>> listOfUsageForDays = usm.getWeekUsageStats();
        for (int z=0,b=1 ; z <= 6; z++ ,b++) {
            // Total time for the day of week:
            long totalTimeInDay = 0L;

            for (UsageStats usageStatsApp: Objects.requireNonNull(listOfUsageForDays.get(z))) {
                totalTimeInDay = totalTimeInDay + usageStatsApp.getTotalTimeInForeground();
            }

            long endTimeMilli = ZonedDateTime.now().minusDays(z).toInstant().toEpochMilli();

            java.time.Instant instant = java.time.Instant.ofEpochMilli(endTimeMilli);
            java.time.DayOfWeek day = java.time.LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault()).getDayOfWeek();
            String dayName = day.getDisplayName(TextStyle.SHORT, java.util.Locale.getDefault());

            dayTimeMap.put(z,new DayUsageModel(((int) TimeUnit.MILLISECONDS.toHours(totalTimeInDay)),dayName));}
            //dayTimeMap.put(z,new DayUsageModel(((int) TimeUnit.MILLISECONDS.toHours(totalTimeInDay)),dayName.substring(0,3)));}
//        }for (int z=0,b=1 ; z <= 6; z++ ,b++) {
//            // Total time for the day of week:
//            long totalTimeInDay = 0L;
//
//            // Day fetch end time:
//            long endTimeMilli = ZonedDateTime.now().minusDays(z).toInstant().toEpochMilli();
//            // Day fetch begin time:
//            long beginTimeMilli = ZonedDateTime.now().minusDays(b).toInstant().toEpochMilli();
//
//            List<UsageStats> usageStatsDay = usgm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,beginTimeMilli,endTimeMilli);
//
//            usageStatsDay = usageStatsDay.stream().filter(app -> app.getTotalTimeInForeground() > 5000).collect(Collectors.toList());
//
//            for (UsageStats usageStatsApp:usageStatsDay) {
//                totalTimeInDay = totalTimeInDay + usageStatsApp.getTotalTimeInForeground();
//            }
//
//            java.time.Instant instant = java.time.Instant.ofEpochMilli(endTimeMilli);
//            java.time.DayOfWeek day = java.time.LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault()).getDayOfWeek();
//            String dayName = day.getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.getDefault());
//
//            dayTimeMap.put(z,new DayUsageModel(((int) TimeUnit.MILLISECONDS.toHours(totalTimeInDay)),dayName.substring(0,3)));
//        }
        return dayTimeMap;
    }
}
