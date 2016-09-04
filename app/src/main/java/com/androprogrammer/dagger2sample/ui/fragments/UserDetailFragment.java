package com.androprogrammer.dagger2sample.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androprogrammer.dagger2sample.AppController;
import com.androprogrammer.dagger2sample.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wasim on 8/1/2016.
 */

public class UserDetailFragment extends BaseFragment {


    @BindView(R.id.layout_data)
    LinearLayout layoutData;
    @BindView(R.id.layout_progress)
    LinearLayout layoutProgress;

    private View view;

    private Unbinder viewBinder;

    private static final String TAG = "BaseFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d(TAG, "oncreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_userdetail, container, false);

        initializeView();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewBinder.unbind();
    }

    private void initializeView() {

        viewBinder = ButterKnife.bind(this, view);
    }
}
