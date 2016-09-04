package com.androprogrammer.dagger2sample.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

import com.androprogrammer.dagger2sample.R;
import com.androprogrammer.dagger2sample.ui.fragments.UserListFragment;

import butterknife.ButterKnife;

public class DashBoardActivity extends BaseActivity {

    private View viewLayout;

    private static final String TAG = "DashBoardActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSimpleToolbar();

        initializeView();

        //if (savedInstanceState == null)
        //{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new UserListFragment())
                    .commit();
        //}
    }

    private void initializeView() {
        viewLayout = LayoutInflater.from(this).inflate(R.layout.activity_dashboard, viewContainer);

        //ButterKnife.bind(DashBoardActivity.this, viewLayout);

        //((AppController) getApplication()).getAppComponents().inject(this);


        //sharedPreferences.edit().putString(TAG, "Dashboard test...").apply();

        //Utility.showToast(DashBoardActivity.this, sharedPreferences.getString(TAG, ""));

    }

    @Override
    public void onBackPressed() {


        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
        {
            getSupportFragmentManager().popBackStack();
            return;
        }

        super.onBackPressed();
    }
}
