package com.example.helper.repository;

public interface SharedPrefRepository {
    public String getLastVisitedTime();
    public Integer getStrike();
    public Integer getClickedTime();
    public void setClickedTime(Integer clickedTime);
    public void setStrikeTime(Integer strikeTime);
    public void setLastVisitedTime(Integer lastTimeVisited);
    public Boolean getUserSystemNightMode();
    public void setSystemNightMode(boolean mode);
    public void setAutoSystemNightMode(boolean mode);
    public Boolean getAutoSystemNightMode();
    public Boolean getStatusOfTimeBasedNotification();
    public void setStatusOfTimeBasedNotification(Boolean status);
    public void setNotificationPermissionByUser(boolean permission);
    public boolean getNotificationPermissionByUser();
}
