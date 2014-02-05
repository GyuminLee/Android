package com.example.samplenetworktest;

import java.io.IOException;
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class NetworkModel {
	AsyncHttpClient client;
	private static NetworkModel instance;
	public static NetworkModel getInstance() {
		if (instance == null) {
			instance = new NetworkModel();
		}
		return instance;
	}
	private NetworkModel() {
		client = new AsyncHttpClient();
		
	}
	
	public interface OnResultListener<T> {
		public void onSuccess(T result);
		public void onError(int code);
	}
	
	public void getMyDataList(String name, final OnResultListener<MyList> listener) {
		RequestParams params = new RequestParams();
		params.put("name", name);
		client.post("http://....", params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				String text = new String(responseBody);
				Gson gson = new Gson();
				MyList list = gson.fromJson(text, MyList.class);
				listener.onSuccess(list);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				listener.onError(0);
			}
		});
	}
}
