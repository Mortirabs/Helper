package com.example.helper;

import static android.app.AppOpsManager.MODE_ALLOWED;
import static android.app.AppOpsManager.OPSTR_GET_USAGE_STATS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import androidx.core.os.LocaleListCompat;
import androidx.fragment.app.DialogFragment;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;

import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class MainActivity extends AppCompatActivity {


    private ImageView headOfHelperS, bodyOfHelperS, eyesRight, eyesLeft;
    private AnimatorSet mouth;
    public int numberOfStartedAnim;
    private boolean sliderState = true;
    public DialogAlgorithm dil;
    public TextView textView;
    public ImageButton menuButton;
    public static TreeMap<Long, String> usageApplication = new TreeMap<>(Comparator.reverseOrder());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        headOfHelperS = findViewById(R.id.head_of_helper);
        bodyOfHelperS = findViewById(R.id.body_of_helper);
        eyesRight = findViewById(R.id.right_eye);
        eyesLeft = findViewById(R.id.left_eye);
        textView = findViewById(R.id.dialog_text_view);
        ListView ls = findViewById(R.id.list_usage_time);
        menuButton = findViewById(R.id.menu_button);
        TextView dayTextView = findViewById(R.id.day_usage);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Locale current = getResources().getConfiguration().getLocales().get(0);
        Log.d("LOCALES:", current.getLanguage());


        if(sharedPreferences.getBoolean("IS_SYSTEM",true)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else if(sharedPreferences.getBoolean("IS_NIGHT", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


        dayTextView.setOnClickListener(view -> {
            if (sliderState) {
                ValueAnimator anim = ValueAnimator.ofInt(ls.getLayoutParams().height,1);
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
                ValueAnimator anims = ValueAnimator.ofInt(1,780);
                ObjectAnimator triangleRotations = ObjectAnimator.ofFloat(findViewById(R.id.triangle_static_day), "rotation",180f);

                anims.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                        ViewGroup.LayoutParams params = ls.getLayoutParams();
                        params.height = (int)valueAnimator.getAnimatedValue();
                        ls.setLayoutParams(params);
                    }
                });
                triangleRotations.start();
                anims.start();
                sliderState = true;
            }
        });
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuFun();
            }
        });

        ArrayList<ListViewData> appNames = new ArrayList<ListViewData>();

        if(getGrantStatus()) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, -12);
            cal.set(Calendar.MILLISECOND,0);
            cal.set(Calendar.MINUTE,0);
            Log.d("HOURS: ",cal.getTime().toString());
            Toast.makeText(this, cal.getTime().toString(),Toast.LENGTH_LONG).show();
            UsageStatsManager usm = (UsageStatsManager) getSystemService(USAGE_STATS_SERVICE);
            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,cal.getTimeInMillis(),System.currentTimeMillis() );
            appList = appList.stream().filter(app -> app.getTotalTimeInForeground() > 5000).collect(Collectors.toList());

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
        DialogAlgorithm dialogAlgorithm = new DialogAlgorithm(jsonLoader(), current);
        HelperAnimation hAnimation = new HelperAnimation(headOfHelperS,bodyOfHelperS,eyesLeft,eyesRight,
                textView);
        hAnimation.speechAnimation(dialogAlgorithm.getWelcomeDialogText());
    }
    public void menuFun() {
        DialogFragment m = new MenuFragment();
        m.show(getSupportFragmentManager(), "Dialog");
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
