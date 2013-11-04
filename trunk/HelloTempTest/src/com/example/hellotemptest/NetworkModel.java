package com.example.hellotemptest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
	
	public interface OnResultListener {
		public void onSuccess(Object result);
		public void onError();
	}
	private NetworkModel() {
		
	}
	
	public <T> void getNetworkData(NetworkRequest<T> request, NetworkRequest.OnResultListener<T> listener) {
		request.setOnResultListener(listener);
		getNetworkData(request);
	}
	
	public void getNetworkData(NetworkRequest request) {
		new NetworkRequestTask().execute(request);
	}
	
	static class NetworkRequestTask extends AsyncTask<NetworkRequest,Integer,NetworkRequest> {
		NetworkRequest mRequest;
		
		@Override
		protected NetworkRequest doInBackground(NetworkRequest... params) {
			NetworkRequest request = params[0];
			mRequest = request;
			URL url = request.getURL();
			
			HttpURLConnection conn;
			try {
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod(request.getRequestMethod());
				conn.setConnectTimeout(request.getConnctionTimeout());
				conn.setReadTimeout(request.getReadTimeout());
				request.setRequestProperty(conn);
				request.setOutput(conn);
				int resCode = conn.getResponseCode();
				if (resCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					request.process(is);
					return request;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(NetworkRequest result) {
			if (result != null) {
				result.sendResult();
			} else {
				mRequest.sendError(0);
			}
			super.onPostExecute(result);
		}
	}
	
	public void getNetworkData(String city, OnResultListener listener) {
		new MyNetworkTask(listener).execute(city);
	}
	
	static class MyNetworkTask extends AsyncTask<String,Integer,WeatherData> {
		OnResultListener mListener;
		
		public MyNetworkTask(OnResultListener listener) {
			super();
			mListener = listener;
		}
		
		@Override
		protected WeatherData doInBackground(String... params) {
			String city = params[0];
			try {
				URL url = new URL(
						"http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric&cnt=7&q="
								+ city);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(30000);
				conn.setReadTimeout(30000);
				int resCode = conn.getResponseCode();
				if (resCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					 XMLParser parser = new XMLParser();
					 WeatherData data = parser.fromXml(is, "weatherdata",
					 WeatherData.class);
					 return data;
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(WeatherData result) {
			if (mListener != null) {
				if (result != null) {
					mListener.onSuccess(result);
				} else {
					mListener.onError();
				}
			}
			super.onPostExecute(result);
		}
	}
}
