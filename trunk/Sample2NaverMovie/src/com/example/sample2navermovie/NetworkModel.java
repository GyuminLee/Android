package com.example.sample2navermovie;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Handler;
import android.os.Looper;

public class NetworkModel {
	
	private static NetworkModel instance;
	private Handler mainHandler;
	
	public static NetworkModel getInstance() {
		if (instance == null) {
			instance = new NetworkModel();
		}
		return instance;
	}
	
	private NetworkModel() {
		CookieHandler.setDefault(new CookieManager());
		mainHandler = new Handler(Looper.getMainLooper());
	}
	
	public interface OnNetworkResultListener {
		public void onResultSuccess(NetworkRequest request);
		public void onResultFail(NetworkRequest request, int errorCode);
	}
	
	public void getNetworkData(NetworkRequest request, OnNetworkResultListener listener) {
		MovieListDownloadTask task = new MovieListDownloadTask();
		task.setOnNetworkResultListener(listener);
		task.execute(request);
	}
	
	public void getNetworkData(NetworkRequest request) {
		getNetworkData(request, new OnNetworkResultListener() {

			@Override
			public void onResultSuccess(NetworkRequest request) {
				request.sendSuccess();
			}

			@Override
			public void onResultFail(NetworkRequest request, int errorCode) {
				request.sendError(errorCode);
			}
			
		});
	}
	
	public void getNetworkImage(ImageRequest request) {
		ImageDownloadRunnable downloader = new ImageDownloadRunnable(request, mainHandler);
		new Thread(downloader).start();
	}
	
	public class ImageDownloadRunnable implements Runnable {
		ImageRequest request;
		Handler mMainHandler;
		public ImageDownloadRunnable(ImageRequest request,Handler handler) {
			this.request = request;
			this.mMainHandler = handler;
		}
		
		@Override
		public void run() {
			HttpURLConnection conn = null;
			InputStream is = null;
			try {
				URL url = request.getURL();
				conn = (HttpURLConnection) url.openConnection();
				request.setRequestMethod(conn);
				request.setConnectionConfig(conn);
				request.setTimeout(conn);
				request.setOutput(conn);

				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					is = conn.getInputStream();
					request.process(is);
				}				
				mMainHandler.post(new Runnable() {
					
					@Override
					public void run() {
						request.sendSuccess();
					}
				});
				return;
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (conn != null) {
					conn.disconnect();
				}
			}
			mMainHandler.post(new Runnable() {
				
				@Override
				public void run() {
					request.sendError(-1);
				}
			});
		}
	}

}
