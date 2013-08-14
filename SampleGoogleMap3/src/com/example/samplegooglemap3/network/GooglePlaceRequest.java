package com.example.samplegooglemap3.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.os.Handler;

import com.google.gson.Gson;

public class GooglePlaceRequest implements Runnable {

	public static final String URL_STRING_FORMAT = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%s,%s&radius=%d&name=%s&sensor=false&key=AIzaSyCe72iAyKd1mZ40jpa8v4vnFEG_Z-V6hFY";
	public static final String URL_STRING_FORMAT_TYPE = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%s,%s&radius=%d&types=food&sensor=false&key=AIzaSyCe72iAyKd1mZ40jpa8v4vnFEG_Z-V6hFY";
	String urlString;
	GooglePlaceList result;
	Handler mHandler;
	public interface OnDownloadCompleteListener {
		public void onCompleted(GooglePlaceRequest request);
	}
	
	OnDownloadCompleteListener mListener;
	
	public GooglePlaceRequest(double lat, double lng, String name) {
		if(name == null || name.equals("")) {
			urlString = String.format(URL_STRING_FORMAT_TYPE, lat,lng, 5000);
		} else {
			String encodedName = null;
			try {
				encodedName = URLEncoder.encode(name, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			urlString = String.format(URL_STRING_FORMAT, lat, lng, 5000, encodedName);
		}
	}
	
	public void start(Handler handler, OnDownloadCompleteListener listener) {
		mHandler = handler;
		mListener = listener;
		new Thread(this).start();
	}
	
	public void run() {
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream is = conn.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				Gson gson = new Gson();
				result = gson.fromJson(isr, GooglePlaceList.class);
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						if (mListener!= null) {
							mListener.onCompleted(GooglePlaceRequest.this);
						}
						
					}
				});
				
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public GooglePlaceList getResult() {
		return result;
	}

}
