package com.example.helper.presentation;

import android.animation.AnimatorSet;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.helper.domain.DialogAlgorithm;
import com.example.helper.model.AppInfo;
import com.example.helper.model.DayUsageModel;
import com.example.helper.repository.LocalInfo;
import com.example.helper.repository.SharedPrefRepository;
import com.example.helper.usecases.GetDayUsageStatsUseCase;
import com.example.helper.usecases.GetJSONApplicationCategory;
import com.example.helper.usecases.GetOnTimeNotificationStatus;
import com.example.helper.usecases.GetSystemAutoNightMode;
import com.example.helper.usecases.GetUserChooseNightMode;
import com.example.helper.usecases.GetWeekUsageCallback;
import com.example.helper.usecases.GetWelcomeDialogUseCase;
import com.example.helper.usecases.SetNightModeAutoUseCase;
import com.example.helper.usecases.SetOnTimeNotificationStatus;
import com.example.helper.usecases.getUserPermissionToNotification;
import com.example.helper.usecases.setNightModeUseCase;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivityViewModel extends ViewModel {
    public GetDayUsageStatsUseCase getDayUsageUseCase;
    public getUserPermissionToNotification getUserPermissionToNotification;
    public GetWeekUsageCallback getWeekUsageCallback;
    public GetWelcomeDialogUseCase getWelcomeDialogUseCase;
    public GetSystemAutoNightMode getSystemAutoNightModeUseCase;
    public GetUserChooseNightMode getUserChooseNightModeUseCase;
    public GetOnTimeNotificationStatus getOnTimeNotificationStatusUseCase;
    public SetOnTimeNotificationStatus setOnTimeNotificationStatusUseCase;
    public DialogAlgorithm dialogAlgo;
    public ScheduleNotification scheduleNotification;
    public LocalInfo localInfo;

    private MutableLiveData<String[]> dialogMassive = new MutableLiveData<>();
    private MutableLiveData<List<AppInfo>> dayListOfAppUsage = new MutableLiveData<>();
    private MutableLiveData<HashMap<Integer, DayUsageModel>> weekListOfUsage = new MutableLiveData<>();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public MainActivityViewModel(GetDayUsageStatsUseCase getDayUsageUseCase,
                                 GetWeekUsageCallback getWeekUsageCallback,
                                 GetWelcomeDialogUseCase getWelcomeDialogUseCase,
                                 DialogAlgorithm dialogAlgo,
                                 GetSystemAutoNightMode getSystemAutoNightModeUseCase,
                                 GetUserChooseNightMode getUserChooseNightModeUseCase,
                                 GetOnTimeNotificationStatus getOnTimeNotificationStatus,
                                 SetOnTimeNotificationStatus setOnTimeNotificationStatusUseCase,
                                 ScheduleNotification scheduleNotification,
                                 LocalInfo li,
                                 getUserPermissionToNotification getUserNotify
                                 ) {
        this.dialogAlgo = dialogAlgo;
        this.getDayUsageUseCase= getDayUsageUseCase;
        this.getOnTimeNotificationStatusUseCase = getOnTimeNotificationStatus;
        this.scheduleNotification = scheduleNotification;
        this.setOnTimeNotificationStatusUseCase = setOnTimeNotificationStatusUseCase;
        this.getWelcomeDialogUseCase = getWelcomeDialogUseCase;
        this.getWeekUsageCallback = getWeekUsageCallback;
        this.getUserPermissionToNotification = getUserNotify;
        this.getUserChooseNightModeUseCase = getUserChooseNightModeUseCase;
        this.getSystemAutoNightModeUseCase = getSystemAutoNightModeUseCase;
        this.localInfo = li;
        getWelcomeDialog();
        getInteractionDialog();
        getWeekUsage();
        getDayUsage();
    }
    public LiveData<String[]> getHelperSpeech() {
        return dialogMassive;
    }
    public LiveData<HashMap<Integer,DayUsageModel>> getWeekUsageStats() {
        return weekListOfUsage;
    }
    public LiveData<List<AppInfo>> getDayAppUsage() {
        return dayListOfAppUsage;
    }

    public void getWelcomeDialog() {
        Disposable welcomeDiscoDialog = (Disposable) Single.fromCallable(() -> getWelcomeDialogUseCase.execute())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> dialogMassive.setValue(result), Throwable::printStackTrace);
    compositeDisposable.add(welcomeDiscoDialog);
    }
    public void getInteractionDialog() {
        Disposable discoInteraction = dialogAlgo.publishSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> dialogMassive.setValue(result),
                        Throwable::printStackTrace
                );
        compositeDisposable.add(discoInteraction);
    }
    public void getWeekUsage() {
        Disposable weekUsageDisco = Single.fromCallable(getWeekUsageCallback)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> weekListOfUsage.setValue(result),
                        Throwable::printStackTrace
                );
        compositeDisposable.add(weekUsageDisco);
    }
    public void getDayUsage() {
        Disposable dayUsageDisposable = Single.fromCallable(() -> getDayUsageUseCase.execute())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> dayListOfAppUsage.setValue(result),
                        Throwable::printStackTrace
                );
        compositeDisposable.add(dayUsageDisposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("MainActivityViewModel","Cleared");
        compositeDisposable.dispose();
    }
}
