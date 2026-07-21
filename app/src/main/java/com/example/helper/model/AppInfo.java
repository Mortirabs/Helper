package com.example.helper.model;

public class AppInfo {
    public String nameOfApp;
    public Long millisecondOfUsage;
    public AppInfo(String nameOfApp,Long millisecond) {
        this.nameOfApp = nameOfApp;
        this.millisecondOfUsage = millisecond;
    }

    public Long getMillisecondOfUsage() {
        return millisecondOfUsage;
    }
}
