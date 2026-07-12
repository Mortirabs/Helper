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

import android.annotation.SuppressLint;
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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.helper.HelperAnimation;
import com.example.helper.ListViewAdapter;
import com.example.helper.R;
import com.example.helper.data.JSONRepositoryImpl;
import com.example.helper.domain.DialogAlgorithm;
import com.example.helper.model.DayUsageModel;
import com.example.helper.usecases.GetDayUsageStatsUseCase;
import com.example.helper.model.ListViewData;
import com.example.helper.usecases.GetWeekUsageCallback;
import com.example.helper.usecases.GetWeekUsageStatsUseCase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {


    private ImageView headOfHelperS, bodyOfHelperS, eyesRight, eyesLeft;
    private boolean sliderState = true;
    private boolean statisticState = false; // here need to be false
    public TextView dialogTextView;
    public ImageButton menuButton;
    public FrameLayout statisticView;
    private final CompositeDisposable disposables = new CompositeDisposable();
    public static TreeMap<Long, String> usageApplication = new TreeMap<>(Comparator.reverseOrder());

    private HashMap<Integer, DayUsageModel> weekUsageStatsHash;


    @SuppressLint("CheckResult")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statisticView = findViewById(R.id.statisticView);
        headOfHelperS = findViewById(R.id.head_of_helper);
        bodyOfHelperS = findViewById(R.id.body_of_helper);
        eyesRight = findViewById(R.id.right_eye);
        eyesLeft = findViewById(R.id.left_eye);
        dialogTextView = findViewById(R.id.dialog_text_view);
        ListView ls = findViewById(R.id.list_usage_time);
        menuButton = findViewById(R.id.menu_button);

        TextView statisticTextView = findViewById(R.id.statistic);
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
//        statisticTextView.setOnClickListener(new View.OnClickListener() {
//            ViewGroup.LayoutParams viewParams = statisticView.getLayoutParams();
//            StatisticDraw statisticDrawClass = new StatisticDraw(getApplicationContext(),new GetWeekUsageStatsUseCase());
//            @Override
//            public void onClick(View view) {
//                if (statisticState) {
//                    ValueAnimator statisticCloseAnim = ValueAnimator.ofInt(viewParams.height,1);
//                    statisticCloseAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
//                            viewParams.height = (int) statisticCloseAnim.getAnimatedValue();
//                            statisticView.setLayoutParams(viewParams);
//                        }
//                    });
//                    statisticCloseAnim.start();
//                    statisticState = false;
//                } else {
//                    ValueAnimator statisticOpenAnim = ValueAnimator.ofInt(1,600);
//                    statisticOpenAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
//                            viewParams.height = (int) statisticOpenAnim.getAnimatedValue();
//                            statisticView.setLayoutParams(viewParams);
//                        }
//                    });
//                    statisticOpenAnim.start();
//                    statisticState = true;
//                    if(statisticView.getChildCount() == 0) {
//                        statisticView.addView(statisticDrawClass);
//                    }
//                }
//            }
//        });

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
            Disposable disbo = Single.fromCallable(new GetWeekUsageCallback(usm))
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::setStatisticView,
                                            Throwable::printStackTrace
                                    );
            disposables.add(disbo);

//            GetWeekUsageStatsUseCase uws = new GetWeekUsageStatsUseCase(usm);
//            HashMap<Integer, DayUsageModel> s = uws.execute();
            statisticTextView.setOnClickListener(new View.OnClickListener() {
                ViewGroup.LayoutParams viewParams = statisticView.getLayoutParams();
//                StatisticDraw statisticDrawClass = new StatisticDraw(getApplicationContext(),s);
                @Override
                public void onClick(View view) {
                    if (statisticState) {
                        ValueAnimator statisticCloseAnim = ValueAnimator.ofInt(viewParams.height,1);
                        statisticCloseAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                                viewParams.height = (int) statisticCloseAnim.getAnimatedValue();
                                statisticView.setLayoutParams(viewParams);
                            }
                        });
                        statisticCloseAnim.start();
                        statisticState = false;
                    } else {
                        ValueAnimator statisticOpenAnim = ValueAnimator.ofInt(1,600);
                        statisticOpenAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                                viewParams.height = (int) statisticOpenAnim.getAnimatedValue();
                                statisticView.setLayoutParams(viewParams);
                            }
                        });
                        statisticOpenAnim.start();
                        statisticState = true;
                        if(statisticView.getChildCount() == 0) {
//                            statisticView.addView(statisticDrawClass);
                        }
                    }
                }
            });

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
                    dialogTextView);
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
    private void setStatisticView(HashMap<Integer,DayUsageModel> hash) {
        if(statisticView.getChildCount() == 0){
            Log.d("setView", "Completed");
            StatisticDraw draw = new StatisticDraw(this, hash);
            statisticView.addView(draw);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }
}
