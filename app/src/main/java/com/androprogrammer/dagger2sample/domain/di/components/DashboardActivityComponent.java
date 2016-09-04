package com.androprogrammer.dagger2sample.domain.di.components;


import com.androprogrammer.dagger2sample.domain.di.modules.AppModule;
import com.androprogrammer.dagger2sample.domain.di.modules.NetModule;
import com.androprogrammer.dagger2sample.ui.fragments.UserListFragment;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules =  {NetModule.class, AppModule.class})
public interface DashboardActivityComponent {
    void inject(UserListFragment fragment);
}