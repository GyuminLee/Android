package com.example.samplenavermovies.model;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;

public class GooglePlaceRequest extends NetworkRequest {
	
	String urlString;
	
	public GooglePlaceRequest(String keyword, double lat, double lng) {
		try {
			urlString = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+","+lng+"&radius=500&sensor=false&key=AIzaSyCe72iAyKd1mZ40jpa8v4vnFEG_Z-V6hFY&keyword="+URLEncoder.encode(keyword, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public URL getURL() {
		try {
			return new URL(urlString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	Object parse(InputStream is) {
		InputStreamReader isr = new InputStreamReader(is);
		Gson gson = new Gson();
		GooglePlaceList list = gson.fromJson(isr, GooglePlaceList.class);
		return list;
	}

}
