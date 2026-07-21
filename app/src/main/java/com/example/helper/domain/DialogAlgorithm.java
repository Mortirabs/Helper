package com.example.helper.domain;

import android.util.Log;

import com.example.helper.model.AppInfo;
import com.example.helper.repository.JSONRepository;
import com.example.helper.presentation.MainActivity;
import com.example.helper.repository.LocalInfo;
import com.example.helper.repository.UsageStatsRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

public class DialogAlgorithm {

    private final String jsonFile;
    private int categoryPlace;
    private String languageTheme;
    static int tappedTime;
    private List<AppInfo> appList;
    public DialogAlgorithm(JSONRepository JSONRep,
                           LocalInfo localInfo,
                           UsageStatsRepository usageRep) {
        initR();
        jsonFile = JSONRep.getJsonString();
        languageTheme = localInfo.getLocale();
        appList = usageRep.getDayUsageStats();
        setNickname();
    }
    private static void initR() {

    }
    public void setNickname() {
        try {
            JSONObject o = new JSONObject(jsonFile);
            if (MainActivity.usageApplication != null) {
                String mostUsageApplication = mostUsageAppName();
                int cIn=0;
                boolean found = false;
                for(; cIn < o.getJSONArray("applicationsCategory").length() && !found;cIn++) {
                    JSONArray a = o.getJSONArray(o.getJSONArray("applicationsCategory").getString(cIn));
                    for (int i = 0; i < a.length(); i++) {
                        if(mostUsageApplication.equals(a.getString(i))) {
                            found = true;
                            categoryPlace = cIn;
                            Log.d("Most usage app: ", a.getString(i));
                            break;}
                    }
            }
        }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String mostUsageAppName() {
        appList.sort(Comparator.comparingLong(AppInfo::getMillisecondOfUsage));
        if(appList.isEmpty()) {
            return "hellnagh";
        } else {
            return appList.get(0).nameOfApp;
        }
    }
    public final String[] getWelcomeDialogText() {
        try {
            JSONObject ob = new JSONObject(jsonFile);
            JSONArray a;
            if (languageTheme.equals("ru")) {
                a = ob.getJSONArray("welcomeCategoryRus");
            } else {
                a = ob.getJSONArray("welcomeCategory");
            }
            return ob.getString(a.getString(categoryPlace)).split(" ");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}