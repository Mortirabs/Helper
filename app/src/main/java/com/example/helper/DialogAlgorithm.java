package com.example.helper;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Objects;

public class DialogAlgorithm {

    private final String jsonFile;
    private int categoryPlace;
    private Locale languageTheme;
    static int initializationTime;

    public DialogAlgorithm(String js, Locale LocaleTheme) {
        initR();
        jsonFile = js;
        languageTheme = LocaleTheme;
        setNickname();
    }
    private static void initR() {
        ++initializationTime;
    }
    public void setNickname() {
        try {
            JSONObject o = new JSONObject(jsonFile);
            if (MainActivity.usageApplication != null) {
                String mostUsageApplication = Objects.requireNonNull(MainActivity.usageApplication.firstEntry()).toString();
                String[] mostUsageApplicationS = mostUsageApplication.split("=");
                int cIn=0;
                boolean found = false;
                for(; cIn < o.getJSONArray("applicationsCategory").length() && !found;cIn++) {
                    JSONArray a = o.getJSONArray(o.getJSONArray("applicationsCategory").getString(cIn));
                    for (int i = 0; i < a.length(); i++) {
                        if(mostUsageApplicationS[1].equals(a.getString(i))) {
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
    public final String[] getWelcomeDialogText() {
        try {
            JSONObject ob = new JSONObject(jsonFile);
            JSONArray a;
            if (languageTheme.getLanguage().equals("ru")) {
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