package com.example.sample2navermovie;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.os.Handler;
import android.os.Looper;

public class NetworkModel {

	private static NetworkModel instance;
	private Handler mainHandler;

	private final static int THREAD_COUNT = 5;
	
	public static NetworkModel getInstance() {
		if (instance == null) {
			instance = new NetworkModel();
		}
		return instance;
	}

	private NetworkModel() {
		CookieHandler.setDefault(new CookieManager());
		mainHandler = new Handler(Looper.getMainLooper());
		for (int i = 0 ; i < THREAD_COUNT; i++) {
			ImageDownloadRunnable r = new ImageDownloadRunnable(mainHandler);
			new Thread(r).start();
		}
	}

	public interface OnNetworkResultListener {
		public void onResultSuccess(NetworkRequest request);

		public void onResultFail(NetworkRequest request, int errorCode);
	}

	public void getNetworkData(NetworkRequest request,
			OnNetworkResultListener listener) {
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

	ArrayList<ImageRequest> mRequestQueue = new ArrayList<ImageRequest>();

	public synchronized void enqueue(ImageRequest request) {
		mRequestQueue.add(request);
		notify();
	}

	public synchronized ImageRequest dequeue() {
		ImageRequest request = null;
		while (mRequestQueue.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		request = mRequestQueue.remove(0);
		return request;
	}

	public void getNetworkImage(ImageRequest request) {
		enqueue(request);
	}

	public class ImageDownloadRunnable implements Runnable {
		Handler mMainHandler;
		boolean isRunning = false;
		public ImageDownloadRunnable(Handler handler) {
			this.mMainHandler = handler;
			isRunning = true;
		}

		@Override
		public void run() {
			while (isRunning) {
				final ImageRequest request = dequeue();
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
					continue;
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

}
