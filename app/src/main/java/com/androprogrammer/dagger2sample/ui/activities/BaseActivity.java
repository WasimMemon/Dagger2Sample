package com.androprogrammer.dagger2sample.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.androprogrammer.dagger2sample.R;

public class BaseActivity extends AppCompatActivity {

    public FrameLayout viewContainer;
    public CoordinatorLayout layoutContainer;
    public AppBarLayout toolbarContainer;
    public android.support.v7.widget.Toolbar toolbar;
    public View view_simpleToolbar;
    public TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);

        viewContainer = (FrameLayout) findViewById(R.id.container);
        layoutContainer = (CoordinatorLayout) findViewById(R.id.base_coordinatorLayout);
        toolbarContainer = (AppBarLayout) findViewById(R.id.base_appBar);
    }


    public void setSimpleToolbar() {
        view_simpleToolbar = LayoutInflater.from(this).inflate(R.layout.simple_toolbar, toolbarContainer);
        toolbar = (android.support.v7.widget.Toolbar) view_simpleToolbar.findViewById(R.id.main_toolbar);
        toolbarTitle = (TextView) view_simpleToolbar.findViewById(R.id.main_toolbar_title);

        //Set the custom toolbar
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            //toolbar.setLogo(R.mipmap.ic_launcher);
        }
    }

    public void setToolbarTittle(String header) {
        if (toolbarTitle != null)
            toolbarTitle.setText(header);
    }

    public void setToolbarElevation(float value) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (toolbar != null)
                toolbar.setElevation(value);
        }
    }
}
