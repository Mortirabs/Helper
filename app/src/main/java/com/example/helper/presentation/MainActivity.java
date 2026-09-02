package com.example.helper.presentation;

import static android.app.AppOpsManager.MODE_ALLOWED;
import static android.app.AppOpsManager.OPSTR_GET_USAGE_STATS;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
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
import com.example.helper.app;
import com.example.helper.model.AppInfo;
import com.example.helper.model.DayUsageModel;
import com.example.helper.model.ListViewData;
import com.example.helper.usecases.NotifyReceiver;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;


public class MainActivity extends AppCompatActivity {


    private ImageView headOfHelperS, bodyOfHelperS, eyesRight, eyesLeft;
    private boolean sliderState = true;
    private boolean statisticState = false; // here need to be false
    public TextView dialogTextView;
    public ImageButton menuButton;
    public FrameLayout statisticView;
    private final MenuFragment menuFragment = new MenuFragment();
    private final CompositeDisposable disposables = new CompositeDisposable();
    private AlarmManager alarmManager;
    private PendingIntent pendIntent;

    @Inject
    MainActivityViewModel viewModel;


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((app)getApplicationContext()).appComponent.inject(this);

        statisticView = findViewById(R.id.statisticView);
        headOfHelperS = findViewById(R.id.head_of_helper);
        bodyOfHelperS = findViewById(R.id.body_of_helper);
        eyesRight = findViewById(R.id.right_eye);
        eyesLeft = findViewById(R.id.left_eye);
        dialogTextView = findViewById(R.id.dialog_text_view);
        ListView ls = findViewById(R.id.list_usage_time);
        menuButton = findViewById(R.id.menu_button);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        headOfHelperS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.dialogAlgo.onClickEvent();
            }
        });

        TextView statisticTextView = findViewById(R.id.statistic);
        TextView dayTextView = findViewById(R.id.day_usage);

        if(viewModel.getSystemAutoNightModeUseCase.execute()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else if(viewModel.getUserChooseNightModeUseCase.execute()) {
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
        menuButton.setOnClickListener(view -> menuFun());


        if(getGrantStatus()) {
            HelperAnimation hAnimation = new HelperAnimation(headOfHelperS,bodyOfHelperS,eyesLeft,eyesRight,
                    dialogTextView);

            Disposable welcomeDiscoDialog = (Disposable) Single.fromCallable(() -> viewModel.getWelcomeDialogUseCase.execute())
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {hAnimation.speechAnimation(result);
                        Log.d("Thread check:", Thread.currentThread().getName());},Throwable::printStackTrace);
            Disposable discoInteraction = viewModel.dialogAlgo.publishSubject
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                    result -> {
                        clearDialogText();
                        hAnimation.mouth = new AnimatorSet();
                        hAnimation.speechAnimation(result);
                        Log.d("Thread check:", Thread.currentThread().getName());
                    },
                    Throwable::printStackTrace
            );
            Disposable disbo = Single.fromCallable(viewModel.getWeekUsageCallback)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::setStatisticView,
                                            Throwable::printStackTrace
                                    );
            disposables.add(discoInteraction);
            disposables.add(disbo);
            disposables.add(welcomeDiscoDialog);
            statisticTextView.setOnClickListener(new View.OnClickListener() {
                ViewGroup.LayoutParams viewParams = statisticView.getLayoutParams();
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
                    }
                }
            });
            ListViewAdapter adapter = new ListViewAdapter(this,viewModel.getDayUsageUseCase.execute());
            ls.setAdapter(adapter);
        } else {
            Log.d("usage stats:" ,"not granted");
            AlertDialog.Builder explainUsageDialog = new AlertDialog.Builder(this);
            explainUsageDialog.setTitle("Usage stats Permission");
            explainUsageDialog.setMessage("For statistic, and to show you your usage for the day, we need permission to your usage stats");
            explainUsageDialog.setPositiveButton("allow", (dialogInterface, i) -> startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)));
            explainUsageDialog.setNegativeButton("not allow", (dialogInterface, i) -> finishAffinity());
            explainUsageDialog.setOnDismissListener(dialogInterface -> recreateActivity());
            explainUsageDialog.create();
            explainUsageDialog.show();
        }
        scheduleNotification();

    }
    public void recreateActivity() {
        this.recreate();
    }
    public void menuFun() {
        menuFragment.show(getSupportFragmentManager(), "dialog");
    }
    public void clearDialogText() {
        dialogTextView.setText(" ");
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
    @SuppressLint("ScheduleExactAlarm")
    private void setScheduleNotification() {
        viewModel.scheduleNotification.scheduleNotification();
    }
    private void setStatisticView(HashMap<Integer,DayUsageModel> hash) {
        if(statisticView.getChildCount() == 0){
            Log.d("setView", "Completed");
            StatisticDraw draw = new StatisticDraw(this, hash);
            statisticView.addView(draw);
        }
    }
    public void scheduleNotification() {
        if(viewModel.getUserPermissionToNotification.execute()
                &&
                !viewModel.getOnTimeNotificationStatusUseCase.execute()) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.POST_NOTIFICATIONS)
                        ==
                        PackageManager.PERMISSION_GRANTED && alarmManager.canScheduleExactAlarms())
                {
                    setScheduleNotification();
                } else
                {
                    ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.POST_NOTIFICATIONS},100);
                }
            } else {
                setScheduleNotification();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("AGREE","schedule");
                startActivity(new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}
