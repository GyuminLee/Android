package com.example.testnetworksample2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Handler;

public class NetworkUrlRequest implements Runnable {

	String url;
	Handler mHandler;
	String resultMessage;

	public interface OnDownloadCompleteListener {
		public void onCompleted(NetworkUrlRequest request);

		public void onError(NetworkUrlRequest request, String errorMessage);
	}

	OnDownloadCompleteListener mListener;

	public void setOnDownloadCompleteListener(
			OnDownloadCompleteListener listener) {
		mListener = listener;
	}

	public NetworkUrlRequest(String url, Handler handler) {
		this.url = url;
		mHandler = handler;
	}

	public void start() {
		new Thread(this).start();
	}

	public String getResult() {
		return resultMessage;
	}

	@Override
	public void run() {
		try {
			URL url = new URL(this.url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				StringBuilder sb = new StringBuilder();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line);
					sb.append("\n\r");
				}
				if (mHandler != null) {
					mHandler.post(new Runnable() {

						@Override
						public void run() {
							if (mListener != null) {
								mListener.onCompleted(NetworkUrlRequest.this);
							}
						}
					});
				}
				return;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (mHandler != null) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					if (mListener != null) {
						mListener.onError(NetworkUrlRequest.this, "error");
					}
				}
			});
		}
	}
}
