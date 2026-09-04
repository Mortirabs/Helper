package com.example.helper.DI;

import android.view.Menu;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.helper.presentation.MainActivityViewModel;
import com.example.helper.presentation.MenuFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel.class)
    abstract ViewModel bindViewModelMainActivity(MainActivityViewModel viewModel);
    @Binds
    @IntoMap
    @ViewModelKey(MenuFragmentViewModel.class)
    abstract ViewModel bindViewModelMenuFragment(MenuFragmentViewModel viewModel);


    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory viewModelFactory);
}
