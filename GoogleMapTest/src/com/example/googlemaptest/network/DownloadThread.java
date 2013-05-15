package com.example.googlemaptest.network;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;

import com.example.googlemaptest.util.PropertyManager;

public class DownloadThread extends Thread {
	private String downloadUrl;
	private Handler mHandler;
	
	private NetworkRequest request;
	
//	public interface DownloadCompleteListener {
//		public void downloadCompleted(ArrayList<SingleNewsItem> items);
//	}
//	DownloadCompleteListener listener;
//	
//	public void setDownloadCompleteListener(DownloadCompleteListener listener) {
//		this.listener = listener;
//	}
	
	public DownloadThread(Handler mainHandler, NetworkRequest request/*String url*/) {
		mHandler = mainHandler;
		//downloadUrl = url;
		this.request = request;
	}
	
	@Override
	public void run() {
		boolean isProcessed = false;
		for (int retry = 0; retry < 3 && isProcessed == false && request.isCanceled == false ; retry++) {
			try {
				//URL url = new URL(downloadUrl);
				URL url = request.getRequestUrl();
				
				URLConnection connection;
				connection = url.openConnection();
				
				HttpURLConnection httpConnection = (HttpURLConnection)connection;
				setCookie(httpConnection);
				request.setConnectionConfig(httpConnection);
				httpConnection.setRequestMethod(request.getMethod());
				request.setHeader(httpConnection);
				request.setOutput(httpConnection);
				httpConnection.setConnectTimeout(request.getConnectionTimeout());
				if (request.isCanceled == true) {
					break;
				}
				httpConnection.connect();
				request.setConnection(httpConnection);
				
				int responseCode = httpConnection.getResponseCode();
				
				if (responseCode == HttpURLConnection.HTTP_OK) {
					saveCookie(httpConnection);
					int length = httpConnection.getContentLength();
					String transferEncoding = httpConnection.getHeaderField("Transfer-Encoding");
					InputStream in = httpConnection.getInputStream();
					request.process(mHandler, in);
				} else {
					request.resultObject = (Integer)responseCode;
					request.postError(mHandler, NetworkRequest.PROCESS_DOWNLOAD_FAIL);
				}
				httpConnection.disconnect();
				isProcessed = true;
			} catch (Exception e) {
				e.printStackTrace();
				request.resultObject = (Integer)(-1);
				request.postError(mHandler, NetworkRequest.PROCESS_DOWNLOAD_FAIL);
			}
		}
	}

	private void saveCookie(HttpURLConnection conn) {
		List<String> cookies = conn.getHeaderFields().get("Set-Cookie");
		if (cookies != null) {
			ArrayList<String> cookielist = new ArrayList<String>();
			for(String cookie : cookies) {
				cookielist.add(cookie.split(";", 2)[0]);
			}
			PropertyManager.getInstance().setCookie(cookielist);
		}
	}
	
	private void setCookie(HttpURLConnection conn) {
		List<String> cookies = PropertyManager.getInstance().getCookie();
		if (cookies != null) {
			for(String cookie: cookies) {
				conn.addRequestProperty("Cookie", cookie);
			}
		}
	}	
}
