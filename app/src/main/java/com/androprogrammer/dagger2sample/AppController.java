package com.androprogrammer.dagger2sample;

import android.app.Application;
import android.content.Context;

import com.androprogrammer.dagger2sample.domain.di.components.DaggerDashboardActivityComponent;
import com.androprogrammer.dagger2sample.domain.di.components.DashboardActivityComponent;
import com.androprogrammer.dagger2sample.domain.di.modules.AppModule;
import com.androprogrammer.dagger2sample.domain.di.modules.NetModule;

/**
 * Created by wasim on 7/28/2016.
 */

public class AppController extends Application {

    private static Context mContext;
    private DashboardActivityComponent mNetComponent;

    @Override
    public void onCreate()
    {
        super.onCreate();

        setContext(getApplicationContext());

        // specify the full name space of the component
        // Dagger_xxxx (where xxxx = component name)

        mNetComponent = DaggerDashboardActivityComponent.builder()
                .netModule(new NetModule(mContext))
                .appModule(new AppModule(this))
                .build();

    }

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mctx) {
        mContext = mctx;
    }

    public DashboardActivityComponent getmNetComponent() {
        return mNetComponent;
    }

}
