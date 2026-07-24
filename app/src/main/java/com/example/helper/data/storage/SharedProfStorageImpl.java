package com.example.helper.data.storage;





import static androidx.core.content.res.TypedArrayUtils.getString;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.helper.R;

public class SharedProfStorageImpl implements SharedPrefStorage{
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public SharedProfStorageImpl(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.interactive_data_shared_pref),Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
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
    public void setVisitTime(Integer daysAgoVisited) {
        editor.putInt(context.getString(R.string.visited_time_file_key), daysAgoVisited);
        editor.apply();
    }

    @Override
    public void setClickedTime(Integer clickedTime) {
        editor.putInt(context.getString(R.string.clicked_time_file_key),clickedTime);
        editor.apply();
    }

    @Override
    public void setStrike(Integer strikeDay) {
        editor.putInt(context.getString(R.string.strike_file_key),strikeDay);
        editor.apply();
    }
}
