package com.example.helper.repository;

public interface SharedPrefRepository {
    Boolean getUserSystemNightMode();
    void setSystemNightMode(boolean mode);
    void setAutoSystemNightMode(boolean mode);
    Boolean getAutoSystemNightMode();
    Boolean getStatusOfTimeBasedNotification();
    void setStatusOfTimeBasedNotification(Boolean status);
    void setNotificationPermissionByUser(boolean permission);
    boolean getNotificationPermissionByUser();
}
