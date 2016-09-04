package com.androprogrammer.dagger2sample.domain.network;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androprogrammer.dagger2sample.R;
import com.androprogrammer.dagger2sample.domain.listeners.RequestListener;


import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class NetworkClient extends AsyncTask<Void, Void, Void> {

	private static final String TAG = NetworkClient.class.getSimpleName();

	private RequestListener listener;

	private Context context;

	private int requestId;

	private HashMap<String, String> reqHeader;
	private HashMap<String, String> reqParams;
	private int reqMethod;
	//private MyProgressDialog progressDialog;

	private String url;
	private String tag;

	private boolean isProgressVisible = false;
	private boolean isFile = false;

	private String file_path = "", key = "";
	private byte[] fileData;


	public NetworkClient(Context context, int requestId, RequestListener listener, String url, HashMap<String, String> reqParams,
						 int reqMethod, String tag, boolean isProgressVisible) {

		this.listener = listener;
		this.requestId = requestId;
		this.reqParams = reqParams;
		this.reqMethod = reqMethod;
		this.url = url;
		this.context = context;
		this.tag = tag;
		this.isProgressVisible = isProgressVisible;
		//SharedPreferences prefManager = CryptoManager.getInstance(context).getPrefs();

		reqHeader = new HashMap<>();


	}

	public NetworkClient(Context context, int requestId, RequestListener listener, String url, HashMap<String, String> reqParams,
						 HashMap<String, String> reqHeaders,
						 int reqMethod, String tag, boolean isProgressVisible) {

		this.listener = listener;
		this.requestId = requestId;
		this.reqParams = reqParams;
		this.reqMethod = reqMethod;
		this.url = url;
		this.context = context;
		this.tag = tag;
		this.isProgressVisible = isProgressVisible;
		this.reqHeader = reqHeaders;
	}


	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (isProgressVisible) {

			showProgressDialog();
		}
	}

	@Override
	protected Void doInBackground(Void... params) {

		try {

			Log.i("Url", "=> " + url);

			if (reqMethod == Request.Method.GET) {

				// prepare string com.retailone.request
				StringRequest req = new StringRequest(Request.Method.GET, url, listenerResponse, listenerError) {
					public Map<String, String> getHeaders() throws AuthFailureError {
						return reqHeader;
					}
				};
				// add the com.retailone.request object to the queue to be executed
				NetworkManager.getInstance(context).addToRequestQueue(req, tag);

			} else if (reqMethod == Request.Method.POST) {

				// prepare string request
				StringRequest req = new StringRequest(Request.Method.POST, url, listenerResponse, listenerError) {
					@Override
					public Map<String, String> getHeaders() throws AuthFailureError {
						Log.d(tag, "headers=" + reqHeader);
						return reqHeader;
					}

					@Override
					public String getBodyContentType() {
						return "application/x-www-form-urlencoded; charset=UTF-8";
					}

					@Override
					protected Map<String, String> getParams() throws AuthFailureError {
						Log.d(tag, "data=" + reqParams);
						return reqParams;
					}

					@Override
					protected Response<String> parseNetworkResponse(NetworkResponse response) {

						if (response.statusCode == HttpURLConnection.HTTP_UNAUTHORIZED){
							dismissProgressDialog();

						}

						return super.parseNetworkResponse(response);
					}
				};

				// add the request object to the queue to be executed
				NetworkManager.getInstance(context).addToRequestQueue(req, tag);

			} /*else if (reqMethod == RequestMethod.MULTIPART) {
				if (fileData != null) {
					MultipartRequest multipartRequest = new MultipartRequest(context, url, listenerError, listenerResponse,
							key, reqParams)
					{
						@Override
						protected Map<String, String> getParams() throws AuthFailureError {
							Log.d(tag, "Params=" + reqParams);
							return reqParams;
						}

						@Override
						protected Map<String, DataPart> getByteData() {
							Map<String, DataPart> params = new HashMap<>();
							// file name could found file base or direct access from real path
							// for now just get bitmap data from ImageView
							params.put(key, new DataPart("file_avatar.jpg",
									fileData, "image/jpeg"));

							Log.d(tag, "FileParams=" + params);

							return params;
						}

					};

					VolleyManager.getInstance().addToRequestQueue(multipartRequest, tag);
				} else {
					throw new NullPointerException("File data is null");
				}
			}*/


		} catch (final Exception e) {
			e.printStackTrace();
			((AppCompatActivity) context).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					dismissProgressDialog();
					listener.onError(requestId, e.toString() + e.getMessage());
				}
			});
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
//        dismissProgressDialog();
	}

	private Response.Listener<String> listenerResponse = new Response.Listener<String>() {
		@Override
		public void onResponse(String response) {
			VolleyLog.v("Response:%n %s", response);
			Log.i("Response", "=> " + response);
			listener.onSuccess(requestId, response);
			dismissProgressDialog();
		}
	};
	private Response.ErrorListener listenerError = new Response.ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError error) {
			error.printStackTrace();
			VolleyLog.e("Error: ", error.getMessage());
			Log.i("Error", "=> " + error.toString() + " : " + error.getMessage());

			if (error instanceof AuthFailureError){
				dismissProgressDialog();


				return;
			}
			else if (error instanceof NoConnectionError)
			{
				//listener.onError(requestId, context.getResources().getString(R.string.msg_generic_server_down));
				dismissProgressDialog();

				return;
			}
			else if (error instanceof TimeoutError)
			{
				//listener.onError(requestId, context.getResources().getString(R.string.msg_generic_server_down));
				dismissProgressDialog();

				return;
			}

			listener.onError(requestId, VolleyErrorHelper.getMessage(error, context));
			dismissProgressDialog();
		}
	};

	// show progress dialog
	private void showProgressDialog() {
		//progressDialog = new MyProgressDialog(context);

		if (listener != null)
		{
			listener.onStartLoading(requestId);
		}
	}

	//  dismiss progress dialog
	private void dismissProgressDialog() {
		/*if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}*/

		if (listener != null)
		{
			listener.onStopLoading(requestId);
		}
	}

	public boolean isFile() {
		return isFile;
	}

	public void setFile(String param, String path) {
		if (path != null && param != null) {
			key = param;
			file_path = path;
			this.isFile = true;
		}
	}

	public void setFileData(String param,byte[] mData)
	{
		if (mData != null && !TextUtils.isEmpty(param))
		{
			key = param;
			fileData = mData;

			isFile = true;
		}
	}
}
