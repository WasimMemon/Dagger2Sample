package com.androprogrammer.dagger2sample.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by wasim on 8/1/2016.
 */

public class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d(TAG, "oncreate");
    }

    public boolean onBackPressed() {
        return false;
    }
}
