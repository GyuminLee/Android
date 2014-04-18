package com.example.sample3googlemap;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class NetworkModel {
	private static NetworkModel instance;
	AsyncHttpClient client;
	public static NetworkModel getInstance() {
		if (instance == null) {
			instance = new NetworkModel();
		}
		return instance;
	}
	
	private NetworkModel() {
		client = new AsyncHttpClient();
	}
	
	public interface OnNetworkResult<T> {
		public void onSuccess(T result);
		public void onFail(int code);
	}
	
	public void getCarRouting(Context context, double startLat, double startLon, double endLat, double endLon, final OnNetworkResult<CarRouteInfo> listener) {
		String url = "https://apis.skplanetx.com/tmap/routes?callback=&bizAppId=&version=1";
		Header[] headers = new Header[2];
		headers[0] = new BasicHeader("Accept", "application/json");
		headers[1] = new BasicHeader("appKey", "458a10f5-c07e-34b5-b2bd-4a891e024c2a");
		
		RequestParams params = new RequestParams();
		params.put("endX", ""+endLon);
		params.put("endY", "" + endLat);
		params.put("startX", "" + startLon);
		params.put("startY","" + startLat);
		params.put("resCoordType", "WGS84GEO");
		params.put("reqCoordType", "WGS84GEO");
		client.post(context, url, headers, params, null, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				String result = new String(responseBody);
				Gson gson = new GsonBuilder().registerTypeAdapter(Geometry.class, new GeometryDeserializer()).create();
				CarRouteInfo info = gson.fromJson(result, CarRouteInfo.class);
				listener.onSuccess(info);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				listener.onFail(statusCode);
			}
		});
	}
	
	
}
