package com.androprogrammer.dagger2sample.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.androprogrammer.dagger2sample.AppController;
import com.androprogrammer.dagger2sample.R;
import com.androprogrammer.dagger2sample.domain.adapters.DashBoardListAdapter;
import com.androprogrammer.dagger2sample.domain.listeners.RequestListener;
import com.androprogrammer.dagger2sample.domain.listeners.RowItemElementClickListener;
import com.androprogrammer.dagger2sample.domain.network.NetworkManager;
import com.androprogrammer.dagger2sample.domain.network.RequestBuilder;
import com.androprogrammer.dagger2sample.domain.util.Utility;
import com.androprogrammer.dagger2sample.models.UserDataResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wasim on 8/1/2016.
 */

public class UserListFragment extends BaseFragment implements RequestListener, RowItemElementClickListener {

	@BindView(R.id.recycler_users)
	RecyclerView recyclerUsers;
	@BindView(R.id.tv_noData)
	TextView tvNoData;
	@BindView(R.id.layout_data)
	LinearLayout layoutData;
	@BindView(R.id.layout_progress)
	LinearLayout layoutProgress;

	private View view;

	@Inject
	SharedPreferences sharedPreferences;

	@Inject
	NetworkManager networkManager;

	@Inject
	Gson gsonParser;

	private List<UserDataResponse> mUserData;
	private DashBoardListAdapter mAdapter;

	private int reqId = -1;

	private static final String TAG = "UserListFragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Log.d(TAG, "oncreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.fragment_userlist, container, false);

		initializeView();

		return view;

	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		userList();
	}

	@Override
	public void onStop() {
		networkManager.removeListeners(this);

		super.onStop();
	}

	private void initializeView() {

		ButterKnife.bind(this, view);

		((AppController) getActivity().getApplication()).getmNetComponent().inject(this);
	}

	private void userList() {

		networkManager.addListener(this);
		networkManager.isProgressVisible(true);

		reqId = networkManager.addRequest(RequestBuilder.getRequestParameter(null), getActivity(),
				TAG, Request.Method.GET, RequestBuilder.SERVER_URL_API);
	}

	@Override
	public void onSuccess(int id, String response) {

		try {
			if (!TextUtils.isEmpty(response)) {

				if (id == reqId) {

					Log.d(TAG, response);

					Type listType = new TypeToken<ArrayList<UserDataResponse>>() {
					}.getType();
					mUserData = gsonParser.fromJson(response, listType);

					if (mUserData != null) {

						mAdapter = new DashBoardListAdapter(getActivity(), mUserData);

						mAdapter.setClickListener(UserListFragment.this);
						mAdapter.setAnimateItems(true);

						recyclerUsers.setAdapter(mAdapter);

						mAdapter.setAnimateItems(false);
					} else {
						recyclerUsers.setVisibility(View.GONE);
						tvNoData.setVisibility(View.VISIBLE);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onError(int id, String message) {
		Utility.showToast(getContext(), message);

		//Log.d(TAG, "onError: " + message);

	}

	@Override
	public void onStartLoading(int id) {
		layoutData.setVisibility(View.GONE);
		layoutProgress.setVisibility(View.VISIBLE);
	}

	@Override
	public void onStopLoading(int id) {
		layoutProgress.setVisibility(View.GONE);
		layoutData.setVisibility(View.VISIBLE);

	}

	@Override
	public void onLayoutClick(View v, int position) {
		switch (v.getId()) {
			case R.id.row_mainLayout:

				Utility.showToast(getActivity(), "" + position);

				if (mAdapter != null) {

					getActivity().getSupportFragmentManager().beginTransaction()
							.replace(R.id.fragmentContainer, new UserDetailFragment())
							.addToBackStack("list")
							.commit();

				}

				recyclerUsers.setAdapter(null);

				break;
		}
	}
}
