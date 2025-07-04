package com.example.helper.domain;

import java.util.Objects;
import java.util.TreeMap;

public class StatisticCalculatorClass {
    private TreeMap<Long, String> usageApps = new TreeMap<Long,String>();
    public StatisticCalculatorClass(TreeMap<Long,String> usageApps) {
        this.usageApps = usageApps;
    }
    public String mostUsageApplication() {
        return Objects.requireNonNull(usageApps.firstEntry()).toString();
    }
    // setup weekend usage stats applications;
}
