package com.example.helper.data.storage;





import android.content.Context;
import android.content.SharedPreferences;

import com.example.helper.R;

public class SharedProfStorageImpl implements SharedPrefStorage{
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPrefSystem;
    private SharedPreferences.Editor sharedPrefSystemEditor;
    private SharedPreferences.Editor sharedPrefAvatarEditor;
    public SharedProfStorageImpl(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.interactive_data_shared_pref),Context.MODE_PRIVATE);
        sharedPrefSystem = context.getSharedPreferences("SYSTEM_DATA",Context.MODE_PRIVATE);
        sharedPrefAvatarEditor = sharedPreferences.edit();
        sharedPrefSystemEditor = sharedPrefSystem.edit();
    }

    @Override
    public String getLastTimeVisit() {
        return sharedPreferences.getString(context.getString(R.string.visited_time_file_key),"today");
    }

    @Override
    public Integer getAvatarClickedTime() {
        return sharedPreferences.getInt(context.getString(R.string.clicked_time_file_key),0);
    }

    @Override
    public Integer getStrike() {
        return sharedPreferences.getInt(context.getString(R.string.strike_file_key),0);
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
    public void setVisitTime(Integer daysAgoVisited) {
        sharedPrefAvatarEditor.putInt(context.getString(R.string.visited_time_file_key), daysAgoVisited);
        sharedPrefAvatarEditor.apply();
    }

    @Override
    public void setClickedTime(Integer clickedTime) {
        sharedPrefAvatarEditor.putInt(context.getString(R.string.clicked_time_file_key),clickedTime);
        sharedPrefAvatarEditor.apply();
    }

    @Override
    public void setStrike(Integer strikeDay) {
        sharedPrefAvatarEditor.putInt(context.getString(R.string.strike_file_key),strikeDay);
        sharedPrefAvatarEditor.apply();
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
