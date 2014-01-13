package com.example.sample2navermovie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;

public class MelonRequest extends NetworkRequest<Places> {

	@Override
	public URL getURL() throws MalformedURLException,
			UnsupportedEncodingException {
		return new URL(
				"https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&types=food&name=harbour&sensor=false&key=AIzaSyA06E0xNLoiOG4pevKrsnVNjz2Uoy9ITvM");
	}

	@Override
	public void process(InputStream is) {
		Gson gson = new Gson();
		result = gson.fromJson(new InputStreamReader(is), Places.class);
	}

}
