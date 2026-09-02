package com.example.helper.repository;

import android.app.usage.UsageStats;

import com.example.helper.model.AppInfo;
import com.example.helper.model.DayUsageModel;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public interface UsageStatsRepository {
    public List<AppInfo> getDayUsageStats();
    public HashMap<Integer, List<UsageStats>> getWeekUsageStats();
}

