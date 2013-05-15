package com.example.googlemaptest.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Handler;

import com.example.googlemaptest.parser.InputStreamParser;
import com.example.googlemaptest.parser.InputStreamParserException;

public class NetworkRequest {

	public static final String BASE_URL = "http://dongjahellowebapp.appspot.com";
	public String urlString;
	public Object resultObject;
	public InputStreamParser parser;
	public OnDownloadCompletedListener listener;
	public static final int PROCESS_SUCCESS = 1;
	public static final int PROCESS_DOWNLOAD_FAIL = 2;
	public static final int PROCESS_PARSE_FAIL = 3;
	
	public boolean isNeedSign = false;
	
	public HttpURLConnection conn;
	
	
	public void setConnection(HttpURLConnection conn) {
		this.conn = conn;
	}
	
	boolean isCanceled = false;
	
	public interface OnDownloadCompletedListener {
		public void onDownloadCompleted(int result, NetworkRequest request);
	}
	
	public void setOnDownloadCompletedListener(OnDownloadCompletedListener listener) {
		this.listener = listener;
	}
	
	public URL getRequestUrl() {
		URL url = null;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			
		} 
		return url;
	}
	
	public Object getResult() {
		return resultObject;
	}
	
	public void setParser(InputStreamParser parser) {
		this.parser = parser;
	}
	
	public InputStreamParser getParser() {
		return parser;
	}
	
	public String getMethod() {
		return "GET";
	}
	
	public boolean setConnectionConfig(HttpURLConnection conn) {
		return true;
	}
	
	public boolean setHeader(HttpURLConnection conn) {
		return true;
	}
	
	public boolean setOutput(HttpURLConnection conn)  throws IOException {
		return true;
	}
	
	public int getConnectionTimeout() {
		return 30000;
	}
	
	public void setCancel() {
		isCanceled = true;
		try { 
			conn.disconnect();
		} catch (Exception e) {
			
		}
	}
	
	public boolean process(Handler handler,InputStream is) {
		try {
			parser.doParse(is);
			resultObject = parser.getResult();
			if (handler != null) {
				handler.post(new Runnable() {
	
					public void run() {
						if (listener != null && isCanceled == false) {
							listener.onDownloadCompleted(PROCESS_SUCCESS, NetworkRequest.this);
						}
					}
					
				});
			} else {
				if (listener != null && isCanceled == false) {
					listener.onDownloadCompleted(PROCESS_SUCCESS, this);
				}
			}
			return true;
		} catch (InputStreamParserException e) {
			e.printStackTrace();
		}
		postError(handler, PROCESS_PARSE_FAIL);
		return false;
	}
	
	public boolean postError(Handler handler,final int errorCode) {
		if (handler != null) {
			handler.post(new Runnable() {
	
				public void run() {
					if (listener != null && isCanceled == false) {
						listener.onDownloadCompleted(errorCode, NetworkRequest.this);
					}				
				}
				
			});
		} else {
			if (listener != null && isCanceled == false) {
				listener.onDownloadCompleted(errorCode, this);
			}
		}
		return true;
	}
}
