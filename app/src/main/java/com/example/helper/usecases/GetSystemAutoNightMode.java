package com.example.helper.usecases;

import com.example.helper.repository.SharedPrefRepository;

public class GetSystemAutoNightMode {
    private SharedPrefRepository sharedPrefRep;
    public GetSystemAutoNightMode(SharedPrefRepository sharedPref) {
        this.sharedPrefRep = sharedPref;
    }
    public boolean execute() {
        return sharedPrefRep.getAutoSystemNightMode();
    }
}
