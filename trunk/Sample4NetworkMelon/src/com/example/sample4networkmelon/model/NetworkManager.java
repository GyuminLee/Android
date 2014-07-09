package com.example.sample4networkmelon.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;

import com.example.sample4networkmelon.entity.Melon;
import com.example.sample4networkmelon.entity.MelonResult;
import com.google.gson.Gson;

public class NetworkManager {

	private static NetworkManager instance;
	public static NetworkManager getInstance() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}
	private NetworkManager() {
		
	}
	
	public interface OnResultListener<T> {
		public void onSuccess(T result);
		public void onFail(int code);
	}
	
	public void getMelon(String url, OnResultListener<Melon> listener) {
		new MelonTask(listener).execute(url);
	}
	
	static class MelonTask extends AsyncTask<String, Integer, Melon> {
		
		OnResultListener<Melon> mListener;
		
		public MelonTask(OnResultListener<Melon> listener) {
			super();
			mListener = listener;
		}
		
		@Override
		protected Melon doInBackground(String... params) {
			String urlString = params[0];
			try {
				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("appKey", "458a10f5-c07e-34b5-b2bd-4a891e024c2a");
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					Gson gson = new Gson();
					MelonResult mr = gson.fromJson(new InputStreamReader(is), MelonResult.class);
					return mr.melon;
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Melon result) {
			super.onPostExecute(result);
			if (mListener != null) {
				if (result != null) {
					mListener.onSuccess(result);
				} else {
					mListener.onFail(0);
				}
			}
		}
	}
}
