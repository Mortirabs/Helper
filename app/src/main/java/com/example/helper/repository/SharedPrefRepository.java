package com.example.helper.repository;

public interface SharedPrefRepository {
    public String getLastVisitedTime();
    public Integer getStrike();
    public Integer getClickedTime();
    public void setClickedTime(Integer clickedTime);
    public void setStrikeTime(Integer strikeTime);
    public void setLastVisitedTime(Integer lastTimeVisited);
}
