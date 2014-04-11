package com.example.sample3melonchart;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;

import com.google.gson.Gson;

public class NetworkModel {

	private static NetworkModel instance;
	public static NetworkModel getInstance() {
		if (instance == null) {
			instance = new NetworkModel();
		}
		return instance;
	}
	private NetworkModel() {
		
	}
	
	public interface OnNetworkResultListener<T> {
		public void onResult(T result);
	}
	public void getMelon(int count, int page, OnNetworkResultListener<Melon> listener) {
		new MelonTask(listener).execute((Integer)count,(Integer)page);
	}
	
	class MelonTask extends AsyncTask<Integer, Integer, Melon> {
		OnNetworkResultListener<Melon> mListener;
		public MelonTask(OnNetworkResultListener<Melon> listener) {
			super();
			mListener = listener;
		}
		
		@Override
		protected Melon doInBackground(Integer... params) {
			int count = params[0];
			int page = params[1];
			String urlString = "http://apis.skplanetx.com/melon/charts/realtime?count="+count+"&page="+page+"&version=1";
			try {
				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setRequestProperty("appKey", "458a10f5-c07e-34b5-b2bd-4a891e024c2a");
				conn.setRequestProperty("Accept", "application/json");
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					Gson gson = new Gson();
					MelonResult mr = gson.fromJson(isr, MelonResult.class);
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
			if (result != null) {
				mListener.onResult(result);
			}
		}
	}
}
