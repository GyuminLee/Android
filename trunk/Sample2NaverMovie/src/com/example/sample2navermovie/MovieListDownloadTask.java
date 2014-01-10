package com.example.sample2navermovie;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.begentgroup.xmlparser.XMLParser;

import android.os.AsyncTask;

public class MovieListDownloadTask extends
		AsyncTask<String, Integer, NaverMovies> {

	NetworkModel.OnNetworkResultListener mListener;
	public void setOnNetworkResultListener(NetworkModel.OnNetworkResultListener listener) {
		mListener = listener;
	}
	
	@Override
	protected NaverMovies doInBackground(String... params) {
		HttpURLConnection conn = null;
		InputStream is = null;
		String keyword = params[0];
		try {
			URL url = new URL(
					"http://openapi.naver.com/search?key=55f1e342c5bce1cac340ebb6032c7d9a&display=10&start=1&target=movie&query="
							+ URLEncoder.encode(keyword, "utf8"));
			conn = (HttpURLConnection) url.openConnection();

			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				is = conn.getInputStream();
				XMLParser parser = new XMLParser();
				NaverMovies movies = parser.fromXml(is, "channel",
						NaverMovies.class);
				return movies;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(NaverMovies result) {
		if (result != null) {
			if (mListener != null) {
				mListener.onResultSuccess(result);
			}
		} else {
			if (mListener != null) {
				mListener.onResultFail(-1);
			}
		}
	}

}
