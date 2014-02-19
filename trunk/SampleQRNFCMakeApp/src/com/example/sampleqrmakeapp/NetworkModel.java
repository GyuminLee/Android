package com.example.sampleqrmakeapp;

import org.apache.http.Header;

import android.os.Handler;
import android.os.Looper;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

public class NetworkModel {

	private static final boolean IS_TEST_MODE = false;

	private static final String URL_LOGIN = "http://www.google.com";
	private static final String URL_GET_BED_NUMBER = "http://www.google.com";
	AsyncHttpClient client;
	private static NetworkModel instance;
	Handler mHandler;

	public static NetworkModel getInstance() {
		if (instance == null) {
			instance = new NetworkModel();
		}
		return instance;
	}

	private NetworkModel() {
		mHandler = new Handler(Looper.getMainLooper());
		client = new AsyncHttpClient();
		client.setCookieStore(new PersistentCookieStore(MyApplication
				.getContext()));
	}

	public interface OnResultListener<T> {
		public void onSuccess(T result);

		public void onError(int code);
	}

	public void login(String id, String passwd,
			final OnResultListener<Boolean> listener) {
		RequestParams params = new RequestParams();
		params.put("id", id);
		params.put("password", passwd);
		if (!IS_TEST_MODE) {
			client.post(URL_LOGIN, params, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					if (listener != null) {
						listener.onSuccess(true);
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					if (listener != null) {
						listener.onError(statusCode);
					}
				}
			});
		} else {
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					if (listener != null) {
						listener.onSuccess(true);
					}
				}
			}, 2000);
		}
	}

	public void getBedUrl(String bedNumber, final OnResultListener<String> listener) {
		if (!IS_TEST_MODE) {
			RequestParams params = new RequestParams();
			params.put("bedNumber", bedNumber);
			client.post(URL_GET_BED_NUMBER, params,
					new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								byte[] responseBody) {
							String url = new String(responseBody);
							if (listener != null) {
								listener.onSuccess(url);
							}
						}

						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							if (listener != null) {
								listener.onError(statusCode);
							}
						}
					});
		} else {
			final String url = "http://www.google.com/?" + bedNumber;
			mHandler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					if (listener != null) {
						listener.onSuccess(url);
					}
				}
			}, 1000);
		}
	}
}
