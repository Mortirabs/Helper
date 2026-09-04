package com.example.helper.presentation;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.os.LocaleListCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.helper.R;
import com.example.helper.app;

import java.util.Locale;
import java.util.Objects;

import jakarta.inject.Inject;

public class MenuFragment extends DialogFragment {
    private String appLocales;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    MenuFragmentViewModel viewModelMenuFragment;

    @Override
    public void onResume() {
        super.onResume();
        Window window = Objects.requireNonNull(getDialog()).getWindow();
        WindowManager.LayoutParams p = window.getAttributes();
        p.y = 500;
        window.setAttributes(p);
        window.setDimAmount(0);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, 600);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        ((app)view.getContext().getApplicationContext()).appComponent.inject(this);
        viewModelMenuFragment = new ViewModelProvider(this,viewModelFactory).get(MenuFragmentViewModel.class);

        SwitchCompat themeSwitch = view.findViewById(R.id.theme_switch);
        SwitchCompat remindNotification = view.findViewById(R.id.notification_switch);

        Spinner languageSpinner = view.findViewById(R.id.spinner);


        appLocales = Locale.getDefault().getLanguage();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                view.getContext(),
                R.array.menu_languages,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        if(!appLocales.equals("en")) {
                            Log.d("switch",viewModelMenuFragment.localInfo.getLocale());
                            LocaleListCompat localList = LocaleListCompat.forLanguageTags("en");
                            AppCompatDelegate.setApplicationLocales((localList));
                            Locale.setDefault(new Locale("en"));
                            break;
                        }
                        break;
                    case 1:
                        if(!appLocales.equals("ru")) {
                            Log.d("switch",viewModelMenuFragment.localInfo.getLocale());
                            LocaleListCompat localList = LocaleListCompat.forLanguageTags("ru");
                            AppCompatDelegate.setApplicationLocales((localList));
                            Locale.setDefault(new Locale("ru"));
                            break;
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(view.getContext(), "Nothing was selected",Toast.LENGTH_SHORT).show();
            }
        });

        languageSpinner.setAdapter(adapter);
        if (appLocales.equals("ru")) {
            languageSpinner.setSelection(1);
        } else {
            languageSpinner.setSelection(0);
        }
        remindNotification.setChecked(viewModelMenuFragment.getUserPermissionToNotificationUseCase.execute());
        remindNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b) {
                        viewModelMenuFragment.scheduleNotification.scheduleNotification();
                    } else {
                        viewModelMenuFragment.scheduleNotification.cancelScheduleNotification();
                    }
            }
        });

        int nightModeFlags =
                requireContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        themeSwitch.setChecked(nightModeFlags == Configuration.UI_MODE_NIGHT_YES);
        themeSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b) {
                if(nightModeFlags != Configuration.UI_MODE_NIGHT_YES) {
                    viewModelMenuFragment.setNightModeAutoUseCase.execute(false);
                    viewModelMenuFragment.setNightModeUseCase.setNightMode(true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
            } else {
                if(nightModeFlags != Configuration.UI_MODE_NIGHT_NO) {
                    viewModelMenuFragment.setNightModeAutoUseCase.execute(false);
                    viewModelMenuFragment.setNightModeUseCase.setNightMode(false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
        return view;
    }
}


