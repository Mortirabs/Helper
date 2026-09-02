package com.example.helper.data.storage;





import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefStorageImpl implements SharedPrefStorage{
    private final SharedPreferences sharedPrefSystem;
    private final SharedPreferences.Editor sharedPrefSystemEditor;
    public SharedPrefStorageImpl(Context context) {
        sharedPrefSystem = context.getSharedPreferences("SYSTEM_DATA",Context.MODE_PRIVATE);
        sharedPrefSystemEditor = sharedPrefSystem.edit();
    }

    @Override
    public Boolean getSystemNightMode() {
        return sharedPrefSystem.getBoolean("IS_NIGHT",false);
    }

    @Override
    public Boolean getSystemAuto() {
        return sharedPrefSystem.getBoolean("IS_SYSTEM",false);
    }

    @Override
    public void setSystemNightMode(boolean nightMode) {
        sharedPrefSystemEditor.putBoolean("IS_NIGHT", nightMode);
        sharedPrefSystemEditor.apply();
    }

    @Override
    public void setNightAutoMode(boolean mode) {
        sharedPrefSystemEditor.putBoolean("IS_SYSTEM",mode);
    }

    @Override
    public boolean getTimeBasedNotificationStatus() {
        return sharedPrefSystem.getBoolean("ON_TIME_NOTIFICATION", true);
    }

    @Override
    public void setTimeNotificationStatus(boolean status) {
        sharedPrefSystemEditor.putBoolean("ON_TIME_NOTIFICATION", status);
        sharedPrefSystemEditor.apply();
    }

    @Override
    public void setNotificationPermissionByUser(boolean permission) {
        sharedPrefSystemEditor.putBoolean("NOTIFICATION_PERMISSION_BY_USER",permission);
    }

    @Override
    public boolean getNotificationPermissionByUser() {
        return sharedPrefSystem.getBoolean("NOTIFICATION_PERMISSION_BY_USER",true);
    }
}
