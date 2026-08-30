package com.example.helper.usecases;

import com.example.helper.repository.SharedPrefRepository;

public class SetOnTimeNotificationStatus {
    private SharedPrefRepository sharedPrefRep;
    public SetOnTimeNotificationStatus(SharedPrefRepository sharedPrefRep) {
        this.sharedPrefRep = sharedPrefRep;
    }
    public void execute(boolean status) {
        sharedPrefRep.setStatusOfTimeBasedNotification(status);
    }
}
