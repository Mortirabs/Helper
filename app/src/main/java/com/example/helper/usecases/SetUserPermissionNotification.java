package com.example.helper.usecases;

import com.example.helper.repository.SharedPrefRepository;

public class SetUserPermissionNotification {
    private final SharedPrefRepository sharedPrefRepository;
    public SetUserPermissionNotification(SharedPrefRepository sharedPrefRep) {
        this.sharedPrefRepository = sharedPrefRep;
    }
    public void execute(boolean status) {
        sharedPrefRepository.setNotificationPermissionByUser(status);
    }
}
