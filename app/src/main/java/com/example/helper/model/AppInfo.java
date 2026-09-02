package com.example.helper.model;

public class AppInfo {
    public String nameOfApp;
    public Long millisecondOfUsage;
    public AppInfo(String nameOfApp,Long millisecond) {
        this.nameOfApp = nameOfApp;
        this.millisecondOfUsage = millisecond;
    }
    public String getApplicationName() {
        return nameOfApp;
    }
    public String getApplicationUsageTime() {
        if (millisecondOfUsage / 3600000 < 1) {
            return millisecondOfUsage / 60000 + " Minutes";
        } else {
            return millisecondOfUsage/3600000 + " Hours";
        }
    }
    public Long getMillisecondOfUsage() {
        return millisecondOfUsage;
    }
}
