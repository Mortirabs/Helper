package com.example.helper.usecases;

import com.example.helper.repository.SharedPrefRepository;

public class getUserPermissionToNotification {
    private SharedPrefRepository sharedPref;
    public getUserPermissionToNotification(SharedPrefRepository sharedPref) {
        this.sharedPref = sharedPref;
    }
    public boolean execute() {
        return sharedPref.getNotificationPermissionByUser();
    }
}
