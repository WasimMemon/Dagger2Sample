package com.androprogrammer.dagger2sample.domain.network;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.androprogrammer.dagger2sample.R;
import com.androprogrammer.dagger2sample.domain.listeners.RequestListener;
import com.androprogrammer.dagger2sample.domain.util.ConnectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class NetworkManager implements RequestListener {

	public static final String TAG = NetworkManager.class.getSimpleName();
	public static NetworkManager instance = null;

	private Context context = null;
	private ArrayList<RequestListener> arrRequestListeners = null;

	private int requestId;
	private boolean isAvailable = false;

	// application context
	private Context mContext = null;

	// global queue for volley
	private RequestQueue mRequestQueue = null;

	// connection timeout, default 20 seconds
	private int timeout = 20 * 1000;

	//private MyProgressDialog progressDialog;


	private NetworkManager(Context context) {
		arrRequestListeners = new ArrayList<>();
		mContext = context;
		Collections.synchronizedList(arrRequestListeners);
	}

	public static NetworkManager getInstance(Context context) {

		if (instance == null)
			instance = new NetworkManager(context);

		return instance;
	}

	/**
	 * @return The Volley Request queue, the queue will be created if it is null
	 */
	public RequestQueue getRequestQueue() {

		// lazy initializeFragmentManager the com.retailone.request queue, the queue instance will be
		// created when it is accessed for the first time
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(mContext);
		}

		return mRequestQueue;
	}

	/**
	 * Adds the specified request to the global queue, if tag is specified
	 * then it is used else Default TAG is used.
	 *
	 * @param req req
	 * @param tag tag
	 */
	public <T> Request addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		req.setRetryPolicy(new DefaultRetryPolicy(timeout, 1, 1.0f));
		return getRequestQueue().add(req);
	}

	/**
	 * Cancels all pending requests by the specified TAG, it is important
	 * to specify a TAG so that the pending/ongoing requests can be cancelled.
	 *
	 * @param tag tag
	 */
	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}


	public synchronized int addRequest(final HashMap<String, String> params, final Context context, String tag,
									   int reqMethod, String url) {
		try {
			this.context = context;

			if (ConnectionUtils.isConnected(context)) {

				url = RequestBuilder.SERVER_URL_API;

				requestId = UniqueNumberUtils.getInstance().getUniqueId();

				NetworkClient networkClient = new NetworkClient(context, requestId, this,
						url, params, reqMethod, tag, isAvailable);
				networkClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

			} else {
				onError(requestId, context.getResources().getString(R.string.msg_noInternet));
			}

		} catch (Exception e) {
			onError(requestId, e.toString() + e.getMessage());
		}

		return requestId;
	}
//
//    public int getRequestId() {
//        return requestId;
//    }

	public void isProgressVisible(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public synchronized void addListener(RequestListener listener) {
		try {
			if (listener != null && !arrRequestListeners.contains(listener)) {
				arrRequestListeners.add(listener);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void onSuccess(int id, String response) {
		if (arrRequestListeners != null && arrRequestListeners.size() > 0) {
			for (RequestListener listener : arrRequestListeners) {
				if (listener != null) listener.onSuccess(id, response);
			}
		}
	}

	@Override
	public void onError(final int id, final String message) {
		try {
			if (Looper.myLooper() == null)
				Looper.prepare();

			if (arrRequestListeners != null && arrRequestListeners.size() > 0) {
				for (final RequestListener listener : arrRequestListeners) {
					if (listener != null) {
						listener.onError(id, message);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onStartLoading(int id) {
		if (arrRequestListeners != null && arrRequestListeners.size() > 0) {
			for (RequestListener listener : arrRequestListeners) {
				if (listener != null) listener.onStartLoading(id);
			}
		}
	}

	@Override
	public void onStopLoading(int id) {

		if (arrRequestListeners != null && arrRequestListeners.size() > 0) {
			for (RequestListener listener : arrRequestListeners) {
				if (listener != null) listener.onStopLoading(id);
			}
		}

	}

	public synchronized void removeListeners(RequestListener listener) {
		try {
			if (listener != null && !arrRequestListeners.contains(listener)) {
				arrRequestListeners.remove(listener);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeRequest(Object tag) {
		try {
			cancelPendingRequests(tag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getListenerSize() {
		try {
			return arrRequestListeners.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/*Response.Listener<String> listenerResponse = new Response.Listener<String>() {
		@Override
		public void onResponse(String response) {
			VolleyLog.v("Response:%n %s", response);
			Log.i("Response", "=> " + response);
			dismissProgressDialog();
			onSuccess(requestId, response);
		}
	};
	Response.ErrorListener listenerError = new Response.ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError error) {
			VolleyLog.e("Error: ", error.getMessage());
			Log.i("Error", "=> " + error.toString() + " : " + error.getMessage());
			dismissProgressDialog();
			onError(requestId, error.toString() + " : " + error.getMessage());
		}
	};

	// show progress dialog
	public synchronized void showProgressDialog() {
		if (progressDialog != null && progressDialog.isShowing()) return;
		progressDialog = new MyProgressDialog(context);
	}

	//  dismiss progress dialog
	private void dismissProgressDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}*/

	/*public synchronized int addMultipartRequest(final HashMap<String, String> params,
												Context context, String tag, RequestMethod reqMethod, String url, String fileParam, byte[] fileData) {
		try {
			this.context = context;

			if (ConnectivityTools.isNetworkAvailable(context)) {

				//url = ConstantData.SERVER_URL + url;
				requestId = UniqueNumberUtils.getInstance().getUniqueId();

				isProgressVisible(true);

				NetworkClient networkClient = new NetworkClient(context, requestId, this, url, params, reqMethod, tag, isAvailable);
				networkClient.setFileData(fileParam, fileData);
				networkClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			} else {
				onError(requestId, context.getString(R.string.msg_no_internet));
			}

		} catch (Exception e) {
			onError(requestId, e.toString() + e.getMessage());
		}

		return requestId;
	}*/

}
