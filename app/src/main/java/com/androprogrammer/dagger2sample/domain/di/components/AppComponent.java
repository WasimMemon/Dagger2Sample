package com.androprogrammer.dagger2sample.domain.di.components;

import android.content.SharedPreferences;

import com.androprogrammer.dagger2sample.domain.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by wasim on 7/29/2016.
 */


@Singleton
@Component (modules = AppModule.class)
public interface AppComponent {

    SharedPreferences sharedPreferences();
}
