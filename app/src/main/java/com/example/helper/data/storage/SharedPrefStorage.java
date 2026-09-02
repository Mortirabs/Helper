package com.example.helper.data.storage;

public interface SharedPrefStorage {
    Boolean getSystemNightMode();
    Boolean getSystemAuto();
    void setSystemNightMode(boolean nightMode);
    void setNightAutoMode(boolean mode);
    boolean getTimeBasedNotificationStatus();
    void setTimeNotificationStatus(boolean status);
    void setNotificationPermissionByUser(boolean permission);
    boolean getNotificationPermissionByUser();

}
