package com.example.helper.data.storage;

public interface SharedPrefStorage {
    public String getLastTimeVisit();
    public Integer getAvatarClickedTime();
    public Integer getStrike();
    public void setVisitTime(Integer daysAgoVisited);
    public void setClickedTime(Integer clickedTime);
    public void setStrike(Integer strikeDay);
}
