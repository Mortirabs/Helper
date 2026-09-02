package com.example.helper.usecases;

import com.example.helper.repository.SharedPrefRepository;

public class SetNightModeAutoUseCase {
    private SharedPrefRepository sharedPrefRep;
    public SetNightModeAutoUseCase(SharedPrefRepository sharedPrefRep) {
        this.sharedPrefRep = sharedPrefRep;
    }
    public void execute(boolean mode) {
        sharedPrefRep.setSystemNightMode(mode);
    }
}
