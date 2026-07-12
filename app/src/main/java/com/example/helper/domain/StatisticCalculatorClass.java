package com.example.helper.domain;

import java.util.Objects;
import java.util.TreeMap;

public class StatisticCalculatorClass {
    private TreeMap<Long, String> usageApps;
    public StatisticCalculatorClass(TreeMap<Long,String> usageApps) {
        this.usageApps = usageApps;
    }
    public String mostUsageApplication() {
        if (usageApps != null) {
            return usageApps.firstEntry().getValue();
        } else {
            return "Nothing";
        }
    }
    // setup weekend usage stats applications;
}
