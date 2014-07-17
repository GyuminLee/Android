package com.example.sample4googlemap.model;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import android.content.Context;

import com.example.sample4googlemap.MyApplication;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class NetworkModel {
	private static NetworkModel instance;
	public static NetworkModel getInstnace() {
		if (instance == null) {
			instance = new NetworkModel();
		}
		return instance;
	}
	
	AsyncHttpClient client;
	Header[] headers = {new BasicHeader("Accept", "application/json"), new BasicHeader("appKey", "458a10f5-c07e-34b5-b2bd-4a891e024c2a")};
	private NetworkModel() {
		client = new AsyncHttpClient();
		client.setCookieStore(new PersistentCookieStore(MyApplication.getContext()));
		client.setTimeout(30000);
	}
	
	public interface OnResultListener<T> {
		public void onSuccess(T data);
		public void onFail(int code);
	}
	
	public RequestParams makeParams() {
		RequestParams params = new RequestParams();
		params.put("reqCoordType", "WGS84GEO");
		params.put("resCoordType", "WGS84GEO");
		params.put("version", "1");
		return params;
	}
	public static final String POI_SEARCH_URL = "https://apis.skplanetx.com/tmap/pois";
	public void getPOI(Context context,String keyword, final OnResultListener<SearchPOIInfo> listener) {
		RequestParams params = makeParams();
		params.put("searchKeyword", keyword);
		client.get(context, POI_SEARCH_URL, headers, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				POIList list = gson.fromJson(responseString, POIList.class);
				listener.onSuccess(list.searchPoiInfo);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
}
