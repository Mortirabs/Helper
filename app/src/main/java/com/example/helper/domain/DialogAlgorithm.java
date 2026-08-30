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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class DialogAlgorithm {

    private final String jsonFile;
    public PublishSubject<String[]> publishSubject = PublishSubject.create();
    private int categoryPlace;
    private JSONObject jsonObject;
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
        try {
            jsonObject = new JSONObject(jsonFile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setNickname();
    }
    private static void initR() {

    }
    public void setNickname() {
        try {
            JSONObject o = new JSONObject(jsonFile);
            if (!appList.isEmpty()) {
                String mostUsageApplication = mostUsageAppName();
                int cIn=0;
                boolean found = false;
                for(; cIn < jsonObject.getJSONArray("applicationsCategory").length() && !found;cIn++) {
                    JSONArray a = jsonObject.getJSONArray(jsonObject.getJSONArray("applicationsCategory").getString(cIn));
                    for (int i = 0; i < a.length(); i++) {
                        if(mostUsageApplication.equals(a.getString(i))) {
                            found = true;
                            categoryPlace = cIn;
                            Log.d("Most usage app:", a.getString(i));
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
        Collections.reverse(appList);
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
                a = jsonObject.getJSONArray("welcomeCategoryRus");
            } else {
                a = jsonObject.getJSONArray("welcomeCategory");
            }
            return jsonObject.getString(a.getString(categoryPlace)).split(" ");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new String[]{"null"};
    }
    public void onClickEvent() {
        Schedulers.computation().scheduleDirect(() -> {
            String[] angryString = angryAboutTouchDialog();
            tappedTime = tappedTime + 1;
            if(tappedTime <5) {
                publishSubject.onNext(angryString);
            } else {
                publishSubject.onNext(crazyAboutTouchDialog());
            }
            Log.d("Thread check:", Thread.currentThread().getName());
        });
    }
    public String[] angryAboutTouchDialog() {
        Random random = new Random();
        int angryDialogPlace = random.nextInt(6);
        Log.d("angry random number:",angryDialogPlace + "");
        Log.d("Thread check:", Thread.currentThread().getName());
        try {
            JSONObject ob = new JSONObject(jsonFile);
            JSONArray a;
            if (languageTheme.equals("ru")) {
                a = jsonObject.getJSONArray("angryAboutTouchDialogRus");
            } else {
                a = jsonObject.getJSONArray("angryAboutTouchDialog");
            }
            return a.getString(angryDialogPlace).split(" ");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new String[]{"dont", "touch","me"};
    }
    public String[] crazyAboutTouchDialog() {
        String[] crazy = {"I", "SAID","THAT","DON'T","TOUCH","ME"};
        return crazy;
    }
}