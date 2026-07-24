package com.example.helper.data;

import com.example.helper.data.storage.SharedPrefStorage;
import com.example.helper.repository.SharedPrefRepository;

public class SharedPrefRepositoryImpl implements SharedPrefRepository {
    private SharedPrefStorage sharedPrefStorage;
    public SharedPrefRepositoryImpl(SharedPrefStorage sharedPrefStorage) {
        this.sharedPrefStorage = sharedPrefStorage;
    }

    @Override
    public String getLastVisitedTime() {
        return sharedPrefStorage.getLastTimeVisit();
    }

    @Override
    public Integer getStrike() {
        return sharedPrefStorage.getStrike();
    }

    @Override
    public Integer getClickedTime() {
        return sharedPrefStorage.getAvatarClickedTime();
    }

    @Override
    public void setClickedTime(Integer clickedTime) {
        sharedPrefStorage.setClickedTime(clickedTime);
    }

    @Override
    public void setStrikeTime(Integer strikeTime) {
        sharedPrefStorage.setStrike(strikeTime);
    }

    @Override
    public void setLastVisitedTime(Integer lastTimeVisited) {
        sharedPrefStorage.setVisitTime(lastTimeVisited);
    }
}
