package com.example.helper.usecases;

import com.example.helper.repository.SharedPrefRepository;

public class setNightModeUseCase {
    private SharedPrefRepository sharedPref;
    public setNightModeUseCase(SharedPrefRepository sharedPref) {
        this.sharedPref = sharedPref;
    }
    public void setNightMode(boolean mode) {
        sharedPref.setSystemNightMode(mode);
    }
}
