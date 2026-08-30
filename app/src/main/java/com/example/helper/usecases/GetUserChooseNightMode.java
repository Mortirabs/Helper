package com.example.helper.usecases;

import com.example.helper.repository.SharedPrefRepository;

public class GetUserChooseNightMode {
    private SharedPrefRepository sharedPrefRep;
    public GetUserChooseNightMode(SharedPrefRepository sharedPrefRep) {
        this.sharedPrefRep = sharedPrefRep;
    }
    public boolean execute() {
        return sharedPrefRep.getUserSystemNightMode();
    }
}
