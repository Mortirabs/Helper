package com.example.helper;

import static android.app.AppOpsManager.MODE_ALLOWED;
import static android.app.AppOpsManager.OPSTR_GET_USAGE_STATS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class MainActivity extends AppCompatActivity {


    private ImageView headOfHelperS, bodyOfHelperS, eyesRight, eyesLeft;
    private AnimatorSet mouth;
    public int numberOfStartedAnim;
    private boolean sliderState = true;
    public TextView textView;
    public static TreeMap<Long, String> usageApplication = new TreeMap<>(Comparator.reverseOrder());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        headOfHelperS = findViewById(R.id.head_of_helper);
        bodyOfHelperS = findViewById(R.id.body_of_helper);
        eyesRight = findViewById(R.id.right_eye);
        eyesLeft = findViewById(R.id.left_eye);
        textView = findViewById(R.id.dialog_text_view);
        ListView ls = findViewById(R.id.list_usage_time);
        TextView dayTextView = findViewById(R.id.day_usage);
        dayTextView.setOnClickListener(view -> {
            if (sliderState) {
                ValueAnimator anim = ValueAnimator.ofInt(459,0);
                ObjectAnimator triangleRotation = ObjectAnimator.ofFloat(findViewById(R.id.triangle_static_day), "rotation",0f);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                        ViewGroup.LayoutParams params = ls.getLayoutParams();
                        params.height = (int)valueAnimator.getAnimatedValue();
                        ls.setLayoutParams(params);
                    }
                });
                anim.start();
                triangleRotation.start();
                sliderState = false;
            } else {
                ValueAnimator anim = ValueAnimator.ofInt(0,459);
                ObjectAnimator triangleRotation = ObjectAnimator.ofFloat(findViewById(R.id.triangle_static_day), "rotation",180f);

                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                        ViewGroup.LayoutParams params = ls.getLayoutParams();
                        params.height = (int)valueAnimator.getAnimatedValue();
                        ls.setLayoutParams(params);
                    }
                });
                triangleRotation.start();
                anim.start();
                sliderState = true;
            }
        });
        ArrayList<ListViewData> appNames = new ArrayList<ListViewData>();
        if(getGrantStatus()) {
            UsageStatsManager usm = (UsageStatsManager) getSystemService(USAGE_STATS_SERVICE);
            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_BEST,  System.currentTimeMillis() - 1000*3600*12,  System.currentTimeMillis());
            appList = appList.stream().filter(app -> app.getTotalTimeInForeground() > 0).collect(Collectors.toList());
            PackageManager packageManager = getApplicationContext().getPackageManager();

            for(UsageStats usageStats : appList) {
                try {
                    usageApplication.put(usageStats.getTotalTimeInForeground(),(String)packageManager.getApplicationLabel(packageManager.getApplicationInfo(usageStats.getPackageName(),PackageManager.GET_META_DATA)));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
            for (Map.Entry<Long, String> item : usageApplication.entrySet()) {
                appNames.add(new ListViewData(item.getValue(), item.getKey()));
            }
            ListViewAdapter adapter = new ListViewAdapter(this,appNames);
            ls.setAdapter(adapter);
        } else {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }
        DialogAlgorithm dialogAlgorithm = new DialogAlgorithm(jsonLoader());
        speech(dialogAlgorithm.getWelcomeDialogText());
    }
    private void helperSWelcomeAnimation(){
        ObjectAnimator gratingUp = ObjectAnimator.ofFloat(headOfHelperS, "translationY", -20f);
        gratingUp.setDuration(500);
        ObjectAnimator gratingUpWithRightEye = ObjectAnimator.ofFloat(eyesRight, "translationY",-20f);
        ObjectAnimator gratingUpWithLeftEye = ObjectAnimator.ofFloat(eyesLeft, "translationY",-20f);
        ObjectAnimator gratingDown = ObjectAnimator.ofFloat(headOfHelperS, "translationY", 0f);
        ObjectAnimator gratingDownWithLeftEye = ObjectAnimator.ofFloat(eyesLeft, "translationY", 0f);
        ObjectAnimator gratingDownWithRightEye = ObjectAnimator.ofFloat(eyesRight, "translationY", 0f);
        AnimatorSet grating = new AnimatorSet();
        grating.play(gratingUp).before(gratingDown);
        grating.play(gratingUp).with(gratingUpWithRightEye).with(gratingUpWithLeftEye);
        grating.play(gratingDown).after(3000).after(gratingUp).with(gratingDownWithLeftEye).with(gratingDownWithRightEye);
        grating.start();
    }
    private void normalStateHelperAnim() {
    }
    private void speech(String[] speechText) {

        ObjectAnimator mouthClose = ObjectAnimator.ofFloat(bodyOfHelperS, "translationY", -3f);
        ObjectAnimator mouthOpen = ObjectAnimator.ofFloat(bodyOfHelperS,"translationY",2f);
        mouth = new AnimatorSet();
        mouth.play(mouthOpen).before(mouthClose);
        mouth.setDuration(300);
        mouth.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (textView.getText() != null) {
                    textView.setText(textView.getText() + " " + speechText[numberOfStartedAnim]);
                } else {
                    textView.setText(speechText[numberOfStartedAnim]);
                }
                ++numberOfStartedAnim;
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(numberOfStartedAnim < speechText.length) {
                    mouth.start();
                }
            }
        });
        mouth.start();
    }
    private boolean getGrantStatus() {
        AppOpsManager appOps = (AppOpsManager) getApplicationContext()
                .getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getApplicationContext().getPackageName());
        if (mode == AppOpsManager.MODE_DEFAULT) {
            return (getApplicationContext().checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        } else {
            return (mode == MODE_ALLOWED);
        }
    }
    private String jsonLoader() {
        String json = null;
        try {
            InputStream is = getAssets().open("json_data");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            return json;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
