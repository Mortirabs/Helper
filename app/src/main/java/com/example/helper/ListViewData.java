package com.example.helper;

public class ListViewData {
    private final long usageTimeInMilliseconds;
    private final String usageAppName;

    public ListViewData(String usageAppN, long usageTime) {
        usageTimeInMilliseconds = (int) (long) usageTime;
        usageAppName = usageAppN;
    }

    public String getApplicationNameList() {
        return  usageAppName;
    }
    public String getUsageTimeString() {
        if (usageTimeInMilliseconds / 3600000 < 1) {
            return usageTimeInMilliseconds / 60000 + " Minutes";
        } else {
            return usageTimeInMilliseconds/3600000 + " Hours";
        }
    }
}
