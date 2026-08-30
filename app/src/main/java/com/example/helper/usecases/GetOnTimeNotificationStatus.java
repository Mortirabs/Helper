package com.example.helper.usecases;

import com.example.helper.repository.SharedPrefRepository;

public class GetOnTimeNotificationStatus {
    private SharedPrefRepository sharedPref;
    public GetOnTimeNotificationStatus(SharedPrefRepository sharedPref) {
        this.sharedPref = sharedPref;
    }
    public boolean execute() {
        return sharedPref.getStatusOfTimeBasedNotification();
    }
}
