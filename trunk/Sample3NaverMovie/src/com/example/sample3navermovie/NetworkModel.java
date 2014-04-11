package com.example.sample3navermovie;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.os.AsyncTask;

import com.begentgroup.xmlparser.XMLParser;

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
	
	public void getNaverMovie(String keyword, int display, int start, OnNetworkResultListener<NaverMovies> listener) {
		new NaverMovieTask(keyword, display, start, listener).execute();
	}
	
	class NaverMovieTask extends AsyncTask<Void, Integer, NaverMovies> {
		String keyword;
		int display;
		int start;
		
		OnNetworkResultListener<NaverMovies> mListener;
		
		public NaverMovieTask(String keyword,int display, int start, OnNetworkResultListener<NaverMovies> listener) {
			super();
			this.keyword = keyword;
			this.display = display;
			this.start = start;
			mListener = listener;
		}
		@Override
		protected NaverMovies doInBackground(Void... params) {
			try {
				String urlString = "http://openapi.naver.com/search?key=55f1e342c5bce1cac340ebb6032c7d9a&query="+URLEncoder.encode(keyword, "utf8")+"&display="+display+"&start="+start+"&target=movie";
				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setConnectTimeout(30000);
				conn.setReadTimeout(30000);
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					XMLParser parser = new XMLParser();
					NaverMovies movies = parser.fromXml(is, "channel", NaverMovies.class);
					return movies;
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
			return null;
		}
		@Override
		protected void onPostExecute(NaverMovies result) {
			super.onPostExecute(result);
			if (result != null && mListener != null) {
				mListener.onResult(result);
			}
		}
	}
}
