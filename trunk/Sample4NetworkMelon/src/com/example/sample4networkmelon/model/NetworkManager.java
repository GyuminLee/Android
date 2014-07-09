package com.example.sample4networkmelon.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.begentgroup.xmlparser.XMLParser;
import com.example.sample4networkmelon.entity.Melon;
import com.example.sample4networkmelon.entity.MelonResult;
import com.example.sample4networkmelon.entity.NaverMovie;
import com.google.gson.Gson;

public class NetworkManager {

	private static NetworkManager instance;
	Handler mHandler;
	public static NetworkManager getInstance() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}
	private NetworkManager() {
		mHandler = new Handler(Looper.getMainLooper());
	}
	
	public interface OnResultListener<T> {
		public void onSuccess(T result);
		public void onFail(int code);
	}
	
	public void getNaverMovie(String keyword, OnResultListener<NaverMovie> listener) {
		new Thread(new MovieRunnable(keyword, listener)).start();
	}
	
	
	class MovieRunnable implements Runnable {
		String keyword;
		OnResultListener<NaverMovie> mListener;
		
		public MovieRunnable(String keyword, OnResultListener<NaverMovie> listener) {
			this.keyword = keyword;
			mListener = listener;
		}
		
		@Override
		public void run() {
			try {
				String encodedKeyword = URLEncoder.encode(keyword, "utf8");
				String urlString = "http://openapi.naver.com/search?key=55f1e342c5bce1cac340ebb6032c7d9a&display=10&start=1&target=movie&query=" + encodedKeyword;
				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					XMLParser parser = new XMLParser();
					final NaverMovie movie = parser.fromXml(is, "channel", NaverMovie.class);
					mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							if (mListener != null) {
								mListener.onSuccess(movie);
							}
						}
					});
					return;
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					if (mListener != null) {
						mListener.onFail(0);
					}
				}
			});
		}
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
