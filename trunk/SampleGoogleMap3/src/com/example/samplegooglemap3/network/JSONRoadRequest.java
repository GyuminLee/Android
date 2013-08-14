package com.example.samplegooglemap3.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;

import com.google.gson.Gson;

public class JSONRoadRequest implements Runnable {

	public static final String COORD_TYPE = "WGS84GEO";
	
	private double mStartX, mStartY, mEndX, mEndY;
	
	public static final int SEARCH_OPTIONS_TRAFFIC_AND_RECOMMAND = 0;
	public static final int SEARCH_OPTIONS_TRAFFIC_AND_SHORTEST = 2;
	public static final int SEARCH_OPTIONS_TRAFFIC_AND_BASIC = 3;
	public static final int SEARCH_OPTIONS_TRAFFIC_AND_EXPRESSWAY = 4;
	public static final int SEARCH_OPTIONS_SHORTEST = 10;

	public static final String URL_STRING = "https://apis.skplanetx.com/tmap/routes?version=1";
	
	public static final String FIELD_CONTENT_TYPE = "Content-Type";
	public static final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";
	public static final String FIELD_APPKEY = "appKey";
	public static final String APPKEY = "458a10f5-c07e-34b5-b2bd-4a891e024c2a";
	public static final String FIELD_ACCEPT = "Accept";
	public static final String ACCEPT = "application/json";
	
	
	RoadSearchResult mResult;
	
	private int searchOptions = 0;
	
	public static final String POST_DATA = "endX=%s&endY=%s&reqCoordType=%s&startX=%s&startY=%s&searchOption=%d&resCoordType=%s";	
	
	public JSONRoadRequest(double startLatitude,double startLongitude, double endLatitude, double endLongitude) {
		mStartY = startLatitude;
		mStartX = startLongitude;
		mEndY = endLatitude;
		mEndX = endLongitude;
	}
	
	public interface OnDownloadCompleteListener {
		public void onCompleted(JSONRoadRequest reqeust);
	}
	
	Handler mHandler;
	OnDownloadCompleteListener mListener;
	
	public void start(Handler handler, OnDownloadCompleteListener listener) {
		mHandler = handler;
		mListener = listener;
		new Thread(this).start();
	}
	
	public void setSearchOptions(int options) {
		searchOptions = options;
	}

	@Override
	public void run() {
		try {
			URL url = new URL(URL_STRING);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			setRequestHeader(conn);
			setOutput(conn);
			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream is = conn.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				StringBuffer sb = new StringBuffer();
				String line;
				while ((line= br.readLine())!=null) {
					sb.append(line);
					sb.append("\n\r");
				}
				JSONObject jobject = new JSONObject(sb.toString());

				mResult = new RoadSearchResult();
				
//				Gson gson = new Gson();
//				mResult = gson.fromJson(isr, RoadSearchResult.class);
				
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						if (mListener != null) {
							mListener.onCompleted(JSONRoadRequest.this);
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
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public RoadSearchResult getResult() {
		return mResult;
	}
	
	private void setRequestHeader(HttpURLConnection conn) {
		try {
			conn.setRequestMethod("POST");
			conn.setRequestProperty(FIELD_CONTENT_TYPE, CONTENT_TYPE);
			conn.setRequestProperty(FIELD_ACCEPT, ACCEPT);
			conn.setRequestProperty(FIELD_APPKEY, APPKEY);
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void setOutput(HttpURLConnection conn) {
		String message = String.format(POST_DATA, mEndX, mEndY, COORD_TYPE, mStartX, mStartY, searchOptions, COORD_TYPE);
		
		try {
			OutputStream out = conn.getOutputStream(); 
			out.write(message.getBytes("UTF-8"));
			out.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
