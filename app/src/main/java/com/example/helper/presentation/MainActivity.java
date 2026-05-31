package com.example.helper.presentation;

import static android.app.AppOpsManager.MODE_ALLOWED;
import static android.app.AppOpsManager.OPSTR_GET_USAGE_STATS;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import androidx.fragment.app.DialogFragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
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

import com.example.helper.HelperAnimation;
import com.example.helper.ListViewAdapter;
import com.example.helper.R;
import com.example.helper.data.JSONRepositoryImpl;
import com.example.helper.domain.DialogAlgorithm;
import com.example.helper.usecases.GetDayUsageStatsUseCase;
import com.example.helper.model.ListViewData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;


public class MainActivity extends AppCompatActivity {


    private ImageView headOfHelperS, bodyOfHelperS, eyesRight, eyesLeft;
    private boolean sliderState = true;
    public TextView textView;
    public ImageButton menuButton;
    public static TreeMap<Long, String> usageApplication = new TreeMap<>(Comparator.reverseOrder());


    @RequiresApi(api = Build.VERSION_CODES.O)
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
            UsageStatsManager usm = (UsageStatsManager) getSystemService(USAGE_STATS_SERVICE);
            PackageManager packageManager = getApplicationContext().getPackageManager();
            GetDayUsageStatsUseCase UsageStatsCase = new GetDayUsageStatsUseCase(usm,packageManager);
            for(UsageStats usageStats : UsageStatsCase.execute()) {
                try {
                    usageApplication.put(usageStats.getTotalTimeInForeground(),(String)packageManager.getApplicationLabel(packageManager.getApplicationInfo(usageStats.getPackageName(),PackageManager.GET_META_DATA)));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
            DialogAlgorithm dialogAlgorithm = new DialogAlgorithm(current,
                    new JSONRepositoryImpl(getApplicationContext()),usageApplication);
            for (Map.Entry<Long, String> item : usageApplication.entrySet()) {
                appNames.add(new ListViewData(item.getValue(), item.getKey()));
            }
            ListViewAdapter adapter = new ListViewAdapter(this,appNames);
            ls.setAdapter(adapter);
            HelperAnimation hAnimation = new HelperAnimation(headOfHelperS,bodyOfHelperS,eyesLeft,eyesRight,
                    textView);
            hAnimation.speechAnimation(dialogAlgorithm.getWelcomeDialogText());
        } else {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }
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
}
