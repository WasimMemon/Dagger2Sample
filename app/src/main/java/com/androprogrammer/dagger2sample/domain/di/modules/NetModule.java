package com.androprogrammer.dagger2sample.domain.di.modules;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.androprogrammer.dagger2sample.domain.network.NetworkManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class NetModule {

    private Context context;

    public NetModule(Context ctx) {
        this.context = ctx;
    }



    @Provides  // Dagger will only look for methods annotated with @Provides
    @Singleton
    NetworkManager provideNetworkManager() {
        NetworkManager networkManager = NetworkManager.getInstance(context);
        return networkManager;
    }

    @Provides  // Dagger will only look for methods annotated with @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        return gsonBuilder.create();
    }
}
