package com.example.helper.data.storage;

public interface SharedPrefStorage {
    public String getLastTimeVisit();
    public Integer getAvatarClickedTime();
    public Integer getStrike();
    public Boolean getSystemNightMode();
    public Boolean getSystemAuto();

    public void setSystemNightMode(boolean nightMode);
    public void setNightAutoMode(boolean mode);
    public void setVisitTime(Integer daysAgoVisited);
    public void setClickedTime(Integer clickedTime);
    public void setStrike(Integer strikeDay);
    public boolean getTimeBasedNotificationStatus();
    public void setTimeNotificationStatus(boolean status);

    public void setNotificationPermissionByUser(boolean permission);
    public boolean getNotificationPermissionByUser();

}
