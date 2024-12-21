package com.example.helper;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DialogAlgorithm {

    private Thread thread1;
    private String wc;
    private final String jsonFile;
    private int categoryPlace;
    static int initializationTime;
    private JSONObject jsonObject;
    static int getClickedTime;
    static String NickName;

    public DialogAlgorithm(String js) {
        initR();
        jsonFile = js;
        setNickname();


        Runnable runnable = this::setNickname;
        thread1 = new Thread(runnable);
        thread1.start();
    }
    private static void initR() {
        ++initializationTime;
    }
    public void setNickname() {
        try {
            JSONObject o = new JSONObject(jsonFile);
            if (MainActivity.usageApplication != null) {
                String mostUsageApplication = MainActivity.usageApplication.firstEntry().toString();
                String[] mostUsageApplicationS = mostUsageApplication.split("=");
                Log.d("If states: ", mostUsageApplicationS[1]);
                int cIn=0;
                boolean found = false;
                for(; cIn < o.getJSONArray("applicationsCategory").length() && !found;cIn++) {
                    Log.d("ForState: ","Worked" + cIn);
                    JSONArray a = o.getJSONArray(o.getJSONArray("applicationsCategory").getString(cIn));
                    for (int i = 0; i < a.length(); i++) {
                        Log.d("ForState2: ","Worked" + i);
                        if(mostUsageApplicationS[1].equals(a.getString(i))) {
                            found = true;
                            categoryPlace = cIn;
                            Log.d("Most usage app: ", a.getString(i));
                            break;}
                    }
            }
        } else {Log.d("If states: ","States #1 worked)");}
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public final String[] getWelcomeDialogText() {
        try {
            JSONObject ob = new JSONObject(jsonFile);
            JSONArray a = ob.getJSONArray("welcomeCategory");
            return ob.getString(a.getString(categoryPlace)).split(" ");
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return null;
    }
}